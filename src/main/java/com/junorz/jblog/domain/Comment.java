package com.junorz.jblog.domain;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.junorz.jblog.context.Messages;
import com.junorz.jblog.context.dto.CommentCreateDTO;
import com.junorz.jblog.context.exception.ResourceNotFoundException;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.utils.Validator;

import lombok.Data;

@Entity
@Data
@Table(name = "comment")
public class Comment {
    
    @Id
    @GenericGenerator(name = "commentIdGen", strategy = "native")
    @GeneratedValue(generator = "commentIdGen")
    private long id;
    
    @Column
    private String comment;
    
    @Column
    private String author;
    
    @Column
    private String email;
    
    @Column
    private ZonedDateTime createDateTime; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
    
    public static List<Comment> paging(int pageNum, int limit, Repository rep) {
        return rep.em().createQuery("SELECT c FROM Comment c", Comment.class)
                .setFirstResult((pageNum - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }
    
    public static long count(Repository rep) {
        List<Long> count = rep.em().createQuery("SELECT count(c) FROM Comment c", Long.class).getResultList();
        return count.isEmpty() ? 0 : count.get(0);
    }
    
    public static Comment create(CommentCreateDTO dto, Repository rep) {
        Validator.validate(v -> {
            v.check(Blog.info(rep).isCommentable(), Messages.COMMENT_IS_NOT_ALLOWED);
        });
        
        Post post = Optional.of(Post.findById(Long.parseLong(dto.getPostId()), rep))
                .orElseThrow(() -> new ResourceNotFoundException(Messages.SYSTEM_RESOURCE_NOT_FOUND));
        
        Comment comment = new Comment();
        comment.setComment(dto.getComment());
        comment.setAuthor(dto.getAuthor());
        comment.setEmail(dto.getEmail());
        comment.setCreateDateTime(ZonedDateTime.now());
        comment.setPost(post);
        
        rep.em().persist(comment);
        
        return comment;
    }
    
}
