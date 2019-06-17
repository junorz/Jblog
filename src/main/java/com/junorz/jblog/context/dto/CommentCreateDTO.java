package com.junorz.jblog.context.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CommentCreateDTO {
    
    @NotEmpty(message = "{COMMENT_AUTHOR_CANNOT_BE_NULL}")
    private String author;
    
    @NotEmpty(message = "{COMMENT_EMAIL_CANNOT_BE_NULL}")
    @Email(message = "{COMMENT_EMAIL_FORMAT_ERROR}")
    private String email;
    
    @NotEmpty(message = "{COMMENT_CANNOT_BE_NULL}")
    private String comment;
    
    @NotEmpty(message = "{SYSTEM_INVALID_OPERATION}")
    private String postId;
    
}
