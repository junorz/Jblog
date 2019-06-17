package com.junorz.jblog.context.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A container that includes warning information when doing validation.
 */
public class Warns {
    
    List<Warn> warnList = new ArrayList<>();
    
    public static Warns init() {
        return new Warns();
    }
    
    public Optional<Warn> head() {
        return warnList.isEmpty() ? Optional.empty() : Optional.of(warnList.get(0));
    }
    
    public void add(String msgCode) {
        warnList.add(new Warn(msgCode));
    }
    
    public boolean hasWarns() {
        return warnList.isEmpty() ? false : true;
    }
    
    @AllArgsConstructor
    @Getter
    public static class Warn {
        private String msgCode;
    }
    
}
