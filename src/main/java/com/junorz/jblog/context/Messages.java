package com.junorz.jblog.context;

public interface Messages {

    // Blog
    public static String BLOG_HAS_BEEN_INITIALIZED = "BLOG_HAS_BEEN_INITIALIZED";
    public static String BLOG_PASSWORD_NOT_MATCH = "BLOG_PASSWORD_NOT_MATCH";

    // User
    public static String USER_NOT_FOUND = "USER_NOT_FOUND";
    
    // Post
    public static String POST_TITLE_CANNOT_BE_NULL = "POST_TITLE_CANNOT_BE_NULL";
    public static String POST_CONTENT_CANNOT_BE_NULL = "POST_CONTENT_CANNOT_BE_NULL";
    public static String BLOG_PASSWORD_CANNOT_BE_NULL = "BLOG_PASSWORD_CANNOT_BE_NULL";
    
    // Comment
    public static String COMMENT_IS_NOT_ALLOWED = "COMMENT_IS_NOT_ALLOWED";

    // System
    public static String SYSTEM_ERROR_OCCURRED = "SYSTEM_ERROR_OCCURRED";
    public static String SYSTEM_INVALID_OPERATION = "SYSTEM_INVALID_OPERATION";
    public static String SYSTEM_RESOURCE_NOT_FOUND = "SYSTEM_RESOURCE_NOT_FOUND";
    public static String SYSTEM_AUTH_FAILED = "SYSTEM_AUTH_FAILED";
}
