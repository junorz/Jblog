package com.junorz.jblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.junorz.jblog.context.dto.BlogCreateDTO;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.orm.TxTemplate;
import com.junorz.jblog.context.utils.AppInfoUtil;
import com.junorz.jblog.domain.Blog;

@Service
public class BlogService {

    private final Repository rep;
    private final PlatformTransactionManager txm;

    public BlogService(Repository rep, PlatformTransactionManager txm) {
        this.rep = rep;
        this.txm = txm;
    }

    public Blog info() {
        return TxTemplate.of(txm).tx(() -> Blog.info(rep));
    }

    public Blog create(BlogCreateDTO dto) {
        return TxTemplate.of(txm).tx(() -> {
            Blog blog = Blog.create(dto, rep);
            AppInfoUtil.updateBlogInfo();
            return blog;
        });
    }

}
