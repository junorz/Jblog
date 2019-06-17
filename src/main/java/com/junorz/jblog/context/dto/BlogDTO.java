package com.junorz.jblog.context.dto;

import java.time.ZonedDateTime;

import com.junorz.jblog.domain.Blog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogDTO {

    private String blogName;
    private String subtitle;
    private String adminName;
    private boolean isPrivate;
    private boolean isCommentable;
    private ZonedDateTime since;
    private long postsCount;
    private long commentsCount;
    private boolean isInitialized;

    public static BlogDTO of(Blog blog) {
        if(blog == null) {
            return new BlogDTO("JBlog", "A subtitle", null, false, false, null, 0, 0, false);
        }
        return new BlogDTO(blog.getName(), blog.getSubtitle(), blog.getAdmin().getName(), blog.isPrivate(),
                blog.isCommentable(), blog.getCreateDateTime(), 0, 0, true);
    }

}
