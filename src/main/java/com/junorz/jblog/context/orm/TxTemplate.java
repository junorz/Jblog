package com.junorz.jblog.context.orm;

import java.util.function.Supplier;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Handle transaction
 */
public class TxTemplate {
    
    private PlatformTransactionManager txm;
    
    public TxTemplate(PlatformTransactionManager txm) {
        this.txm = txm;
    }
    
    public static TxTemplate of(PlatformTransactionManager txm) {
        return new TxTemplate(txm);
    }
    
    public <T> T tx(Supplier<T> supplier) {
        TransactionTemplate txTemplate = new TransactionTemplate(txm);
        return txTemplate.execute(status -> {
            T result = null;
            try {
                result = supplier.get();
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
            return result;
        });
    }
    
    public void tx(Runnable runnable) {
        TransactionTemplate txTemplate = new TransactionTemplate(txm);
        txTemplate.execute(status -> {
            try {
                runnable.run();
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
            return null;
        });
    }
    
}
