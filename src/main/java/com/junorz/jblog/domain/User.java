package com.junorz.jblog.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.junorz.jblog.context.consts.Authority;
import com.junorz.jblog.context.orm.Repository;

import lombok.Data;

@Entity
@Data
@Table(name = "user", indexes = { @Index(name = "user_name", columnList = "name") })
public class User {

    @Id
    @GenericGenerator(name = "userIdGen", strategy = "native")
    @GeneratedValue(generator = "userIdGen")
    private long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private Authority authority;

    @Column
    private boolean isAvaliable = true;
    
    
    public static User findByName(String username, Repository rep) {
        List<User> userList = rep.em().createQuery("SELECT u FROM User u WHERE u.name = ?1", User.class)
                .setParameter(1, username)
                .getResultList();
        return userList.isEmpty() ? null : userList.get(0);
    }

}
