package com.junorz.jblog.context.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PostCreateDTO {
    
    @NotEmpty(message = "{POST_TITLE_CANNOT_BE_NULL}")
    private String title;
    
    @NotEmpty(message = "{POST_CONTENT_CANNOT_BE_NULL}")
    private String content;
    
}
