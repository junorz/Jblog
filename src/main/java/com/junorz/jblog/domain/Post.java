package com.junorz.jblog.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.Strings;
import com.junorz.jblog.context.Messages;
import com.junorz.jblog.context.dto.PostCreateDTO;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.utils.AppInfoUtil;
import com.junorz.jblog.context.utils.Validator;

import lombok.Data;

@Entity
@Data
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 3278633880276004294L;
    
    @Id
    @GenericGenerator(name = "postIdGen", strategy="native")
    @GeneratedValue(generator = "postIdGen")
    private long id;
    
    @Column
    private String title;
    
    @Column
    private String content;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Comment> commentList;
    
    @Column
    private ZonedDateTime createDateTime;
    
    @Column
    private ZonedDateTime modifyDateTime;
    
    @Column
    private long commentsCount = 0;
    
    public static List<Post> findAll(Repository rep) {
        return rep.em().createQuery("SELECT p FROM Post p ORDER BY p.createDateTime DESC", Post.class).getResultList();
    }
    
    public static List<Post> paging(int pageNum, int limit, Repository rep) {
        return rep.em().createQuery("SELECT p FROM Post p ORDER BY p.createDateTime DESC", Post.class)
                .setFirstResult((pageNum - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }
    
    public static long count(Repository rep) {
        List<Long> count = rep.em().createQuery("SELECT count(p) FROM Post p", Long.class).getResultList();
        return count.isEmpty() ? 0 : count.get(0);
    }
    
    public static Post findById(long id, Repository rep) {
        Post post = rep.em().find(Post.class, id);
        if(!AppInfoUtil.getBlogInfo().isCommentable()) {
            post.setCommentList(new ArrayList<Comment>());
        }
        return post; 
    }
    
    public static Post create(PostCreateDTO dto, Repository rep) {
        Validator.validate(v -> {
            v.check(!Strings.isNullOrEmpty(dto.getTitle()), Messages.POST_TITLE_CANNOT_BE_NULL);
            v.check(!Strings.isNullOrEmpty(dto.getContent()), Messages.POST_CONTENT_CANNOT_BE_NULL);
        });
        
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(Blog.info(rep).getAdmin());
        post.setCreateDateTime(ZonedDateTime.now());
        
        rep.em().persist(post);
        return post;
    }
    
}
