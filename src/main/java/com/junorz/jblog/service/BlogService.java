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

    public void create(BlogCreateDTO dto) {
        TxTemplate.of(txm).tx(() -> {
            Blog.create(dto, rep);
            AppInfoUtil.updateBlogInfo();
        });
    }
    
    public void update(BlogCreateDTO dto) {
        TxTemplate.of(txm).tx(() -> {
            Blog.update(dto, rep);
            AppInfoUtil.updateBlogInfo();
        });
    }

}
