package com.junorz.jblog.context.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.junorz.jblog.context.consts.Authority;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.orm.TxTemplate;
import com.junorz.jblog.domain.Blog;
import com.junorz.jblog.domain.User;

@Component
public class DataFixture {

    private final Repository rep;
    private final PasswordEncoder passwordEncoder;
    private final PlatformTransactionManager txm;

    @Value("${jblog.datafixture}")
    private boolean dataFixture;

    @Autowired
    public DataFixture(Repository rep, PasswordEncoder passwordEncoder, PlatformTransactionManager txm) {
        this.rep = rep;
        this.passwordEncoder = passwordEncoder;
        this.txm = txm;
    }

    @PostConstruct
    public void init() {
        if (dataFixture) {
            User user = new User();
            user.setName("admin");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setAuthority(Authority.ROLE_ADMIN);
            user.setAvaliable(true);

            Blog blog = new Blog();
            blog.setAdmin(user);
            blog.setName("Test");
            blog.setSubtitle("A subtitle");
            blog.setCommentable(true);
            blog.setPrivate(false);

            TxTemplate.of(txm).tx(() -> rep.em().persist(blog));

        }
    }

}
