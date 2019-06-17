package com.junorz.jblog.context.utils;

import java.util.function.Consumer;

import com.junorz.jblog.context.exception.ValidationException;
import com.junorz.jblog.context.exception.Warns;

public class Validator {
    
    private final Warns warns = Warns.init();
    
    public static void validate(Consumer<Validator> proc) {
        Validator validator = new Validator();
        proc.accept(validator);
        validator.verify();
    }
    
    public void check(boolean condition, String msgCode) {
        if (!condition) {
            warns.add(msgCode);
        }
    }
    
    public void verify() {
        if (warns.hasWarns()) {
            throw new ValidationException(warns);
        }
    }
    
}
