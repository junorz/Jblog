package com.junorz.jblog.context.orm;

import javax.persistence.EntityManager;

public interface Repository {
    
    public EntityManager em();
    
}
