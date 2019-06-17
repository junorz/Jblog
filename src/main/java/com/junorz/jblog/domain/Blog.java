package com.junorz.jblog.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.junorz.jblog.context.Messages;
import com.junorz.jblog.context.consts.Authority;
import com.junorz.jblog.context.dto.BlogCreateDTO;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.utils.Validator;

import lombok.Data;

@Entity
@Data
public class Blog implements Serializable {

    private static final long serialVersionUID = -9219549591453118055L;

    @Id
    private String name;

    @Column
    private String subtitle;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "admin_user_id", referencedColumnName = "id")
    private User admin;
    
    @Column
    private boolean isPrivate = false;
    
    @Column
    private String viewPassword;
    
    @Column
    private boolean isCommentable = true;
    
    @Column
    private ZonedDateTime createDateTime = ZonedDateTime.now();
    

    public static Blog info(Repository rep) {
        List<Blog> blogList = rep.em().createQuery("SELECT b FROM Blog b", Blog.class).getResultList();
        return blogList.isEmpty() ? null : blogList.get(0);
    }
    
    public static Blog create(BlogCreateDTO dto, Repository rep) {
        Validator.validate(v -> {
            v.check(info(rep) == null, Messages.BLOG_HAS_BEEN_INITIALIZED);
            v.check(dto.getPassword().equals(dto.getConfirm()), Messages.BLOG_PASSWORD_NOT_MATCH);
        });
        
        PasswordEncoder pe = new BCryptPasswordEncoder();
        
        User user = new User();
        user.setName(dto.getAdminName());
        user.setPassword(pe.encode(dto.getPassword()));
        user.setAvaliable(true);
        user.setAuthority(Authority.ROLE_SUPERADMIN);
        
        Blog blog = new Blog();
        blog.setName(dto.getBlogName());
        blog.setSubtitle(dto.getSubtitle());
        blog.setAdmin(user);
        blog.setViewPassword(pe.encode(dto.getViewPassword()));
        blog.setPrivate(dto.isPrivate());
        blog.setCommentable(dto.isCommentable());
        
        rep.em().persist(blog);
        
        return blog;
    }

}
