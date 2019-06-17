package com.junorz.jblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.junorz.jblog.context.dto.BlogCreateDTO;
import com.junorz.jblog.context.dto.BlogDTO;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.orm.TxTemplate;
import com.junorz.jblog.domain.Blog;
import com.junorz.jblog.domain.Comment;
import com.junorz.jblog.domain.Post;

@Service
public class BlogService {
    
    private final Repository rep;
    private final PlatformTransactionManager txm;
    
    public BlogService(Repository rep, PlatformTransactionManager txm) {
        this.rep = rep;
        this.txm = txm;
    }
    
    public BlogDTO info() {
        BlogDTO blogDTO = BlogDTO.of(TxTemplate.of(txm).tx(() -> Blog.info(rep)));
        blogDTO.setPostsCount(Post.count(rep));
        blogDTO.setCommentsCount(Comment.count(rep));
        return blogDTO;
    }
    
    public Blog create(BlogCreateDTO dto) {
        return TxTemplate.of(txm).tx(() -> Blog.create(dto, rep));
    }
    
    

}
