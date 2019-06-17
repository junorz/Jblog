package com.junorz.jblog.context.exception;

import com.junorz.jblog.context.utils.MsgUtil;

public class InvalidOperationException extends RuntimeException {
    
    private static final long serialVersionUID = 1020193979884304971L;

    public InvalidOperationException(String msg) {
        super(MsgUtil.message(msg));
    }
    
}
