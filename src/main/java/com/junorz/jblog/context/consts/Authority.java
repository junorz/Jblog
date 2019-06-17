package com.junorz.jblog.context.consts;

public enum Authority {
    ROLE_USER("USER"), 
    ROLE_ADMIN("ADMIN"),
    ROLE_SUPERADMIN("SUPER_ADMIN");
    
    private String roleName;
    
    private Authority(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleName() {
        return this.roleName;
    }
}
