package com.junorz.jblog.context.exception;

import com.junorz.jblog.context.Messages;
import com.junorz.jblog.context.utils.MsgUtil;

public class ValidationException extends RuntimeException {
    
    private static final long serialVersionUID = 4485850020443561150L;

    public ValidationException(String code) {
        super(MsgUtil.message(code));
    }
    
    public ValidationException(Warns warns) {
        super(MsgUtil.message(warns.head().map(w -> w.getMsgCode()).orElse(Messages.SYSTEM_ERROR_OCCURRED)));
    }
    
}
