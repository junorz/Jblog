package com.junorz.jblog.context.orm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DefaultRepository implements Repository {
    
    @PersistenceContext(unitName = "jblog")
    private EntityManager em;
    
    public EntityManager em() {
        return em;
    }
    
}
