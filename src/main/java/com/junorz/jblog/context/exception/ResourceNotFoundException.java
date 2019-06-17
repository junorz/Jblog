package com.junorz.jblog.context.exception;

import com.junorz.jblog.context.utils.MsgUtil;

public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = -8230389177087405365L;

    public ResourceNotFoundException(String msg) {
        super(MsgUtil.message(msg));
    }
    
}
