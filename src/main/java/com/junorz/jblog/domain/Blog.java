package com.junorz.jblog.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.base.Strings;
import com.junorz.jblog.context.Messages;
import com.junorz.jblog.context.consts.Authority;
import com.junorz.jblog.context.dto.BlogCreateDTO;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.utils.Validator;

import lombok.Data;

@Entity
@Data
@Table(name = "blog", indexes = { @Index(columnList = "name") })
public class Blog implements Serializable {

    private static final long serialVersionUID = -9219549591453118055L;

    @Id
    @GenericGenerator(name = "blogIdGen", strategy = "native")
    @GeneratedValue(generator = "blogIdGen")
    private long id;

    @Column
    private String name;

    @Column
    private String subtitle;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    public static void create(BlogCreateDTO dto, Repository rep) {
        Validator.validate(v -> {
            v.check(!Strings.isNullOrEmpty(dto.getPassword()), Messages.BLOG_PASSWORD_CANNOT_BE_NULL);
            v.check(Strings.isNullOrEmpty(dto.getConfirm()), Messages.BLOG_PASSWORD_CANNOT_BE_NULL);
            v.check(info(rep) == null, Messages.BLOG_HAS_BEEN_INITIALIZED);
            v.check(dto.getPassword().equals(dto.getConfirm()), Messages.BLOG_PASSWORD_NOT_MATCH);
        });

        PasswordEncoder pe = new BCryptPasswordEncoder();

        User user = new User();
        user.setName(dto.getAdminName());
        user.setPassword(pe.encode(dto.getPassword()));
        user.setAvaliable(true);
        user.setAuthority(Authority.ROLE_SUPER_ADMIN);

        Blog blog = new Blog();
        blog.setName(dto.getBlogName());
        blog.setSubtitle(dto.getSubtitle());
        blog.setAdmin(user);
        blog.setViewPassword(pe.encode(dto.getViewPassword()));
        blog.setPrivate(dto.isPrivate());
        blog.setCommentable(dto.isCommentable());

        rep.em().persist(blog);
    }

    public static void update(BlogCreateDTO dto, Repository rep) {
        Blog blog = info(rep);
        User admin = blog.getAdmin();
        PasswordEncoder pe = new BCryptPasswordEncoder();

        // If new administrator password specified
        if (!Strings.isNullOrEmpty(dto.getPassword())) {
            Validator.validate(
                    v -> v.check(dto.getPassword().equals(dto.getConfirm()), Messages.BLOG_PASSWORD_NOT_MATCH));
            admin.setPassword(pe.encode(dto.getPassword()));
        }

        // If view password specified
        if (!Strings.isNullOrEmpty(dto.getViewPassword()) && dto.isPrivate()) {
            blog.setViewPassword(pe.encode(dto.getViewPassword()));
        }

        blog.setName(dto.getBlogName());
        blog.setSubtitle(dto.getSubtitle());
        admin.setName(dto.getAdminName());
        blog.setPrivate(dto.isPrivate());
        blog.setCommentable(dto.isCommentable());

        rep.em().merge(blog);

    }

}
