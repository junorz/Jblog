package com.junorz.jblog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.junorz.jblog.context.dto.PostCreateDTO;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.orm.TxTemplate;
import com.junorz.jblog.context.utils.AppInfoUtil;
import com.junorz.jblog.domain.Post;

@Service
public class PostService {

    private final Repository rep;
    private final PlatformTransactionManager txm;

    public PostService(Repository rep, PlatformTransactionManager txm) {
        super();
        this.rep = rep;
        this.txm = txm;
    }

    public List<Post> findAll() {
        return TxTemplate.of(txm).tx(() -> Post.findAll(rep));
    }

    public List<Post> paging(int pageNum, int limit) {
        return TxTemplate.of(txm).tx(() -> Post.paging(pageNum, limit, rep));
    }

    public Post findById(long id) {
        return TxTemplate.of(txm).tx(() -> Post.findById(id, rep));
    }

    public Post create(PostCreateDTO dto) {
        return TxTemplate.of(txm).tx(() -> {
            Post post = Post.create(dto, rep);
            AppInfoUtil.increaseBlogPostsCount();
            return post;
        });
    }

}
