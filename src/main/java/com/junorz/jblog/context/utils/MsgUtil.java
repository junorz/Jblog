package com.junorz.jblog.context.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * Get message from messages properties file.
 */
@Component
public class MsgUtil implements MessageSourceAware {
    
    public static MessageSourceAccessor messageSourceAccessor;
    
    @Override
    public void setMessageSource(MessageSource messageSource) {
        MsgUtil.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }
    
    public static String message(String code) {
        return messageSourceAccessor.getMessage(code);
    }
    
    
}
