package com.junorz.jblog.context.dto;

import java.time.ZonedDateTime;

import com.junorz.jblog.domain.Blog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Deprecated
public class BlogDTO {

    private String blogName;
    private String subtitle;
    private boolean isPrivate;
    private boolean isCommentable;
    private ZonedDateTime since;
    private boolean isInitialized;

    public static BlogDTO of(Blog blog) {
        if (blog == null) {
            return new BlogDTO("JBlog", "A subtitle", false, false, null, false);
        }
        return new BlogDTO(blog.getName(), blog.getSubtitle(), blog.isPrivate(),
                blog.isCommentable(), blog.getCreateDateTime(), true);
    }

}
