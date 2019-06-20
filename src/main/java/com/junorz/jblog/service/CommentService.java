package com.junorz.jblog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.junorz.jblog.context.dto.CommentCreateDTO;
import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.orm.TxTemplate;
import com.junorz.jblog.context.utils.AppInfoUtil;
import com.junorz.jblog.domain.Comment;

@Service
public class CommentService {

    private final Repository rep;
    private final PlatformTransactionManager txm;

    public CommentService(Repository rep, PlatformTransactionManager txm) {
        this.rep = rep;
        this.txm = txm;
    }

    public List<Comment> paging(int pageNum, int limit) {
        return TxTemplate.of(txm).tx(() -> Comment.paging(pageNum, limit, rep));
    }

    public Comment create(CommentCreateDTO dto) {
        return TxTemplate.of(txm).tx(() -> {
            Comment comment = Comment.create(dto, rep);
            AppInfoUtil.increaseBlogCommentsCount();
            return comment;
        });
    }

}
