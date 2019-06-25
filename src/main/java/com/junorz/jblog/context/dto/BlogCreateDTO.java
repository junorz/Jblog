package com.junorz.jblog.context.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BlogCreateDTO {
    @NotEmpty(message = "{BLOG_NAME_CANNOT_BE_NULL}")
    private String blogName;
    @NotEmpty(message = "{BLOG_SUBTITLE_CANNOT_BE_NULL}")
    private String subtitle;
    @NotEmpty(message = "{BLOG_ADMIN_CANNOT_BE_NULL}")
    private String adminName;
    private String password;
    private String confirm;
    @NotNull(message = "{BLOG_PRIVATE_NOT_NULL}")
    private boolean isPrivate;
    private String viewPassword;
    @NotNull(message = "{BLOG_COMMENTABLE_NOT_NULL}")
    private boolean isCommentable;
}
