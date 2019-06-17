package com.junorz.jblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.context.orm.TxTemplate;
import com.junorz.jblog.domain.User;

@Service
public class UserService {

    private final PlatformTransactionManager txm;
    private final Repository rep;

    public UserService(PlatformTransactionManager txm, Repository rep) {
        super();
        this.txm = txm;
        this.rep = rep;
    }

    public User findByName(String username) {
        return TxTemplate.of(txm).tx(() -> User.findByName(username, rep));
    }

}
