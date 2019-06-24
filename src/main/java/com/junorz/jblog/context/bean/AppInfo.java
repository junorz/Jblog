package com.junorz.jblog.context.bean;

import java.time.ZonedDateTime;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.junorz.jblog.context.orm.Repository;
import com.junorz.jblog.domain.Blog;
import com.junorz.jblog.domain.Comment;
import com.junorz.jblog.domain.Post;

import lombok.Data;

/**
 * The application scoped data.<br>
 * Do not access this component directly, use AppInfoUtil instead.
 */
public class AppInfo {

    private BlogInfo blogInfo = new BlogInfo();
    private final Repository rep;

    @Autowired
    public AppInfo(Repository rep) {
        this.rep = rep;
    }

    @PostConstruct
    public void init() {
        // Initialize blog info
        this.blogInfo.setInitialized(false);
        Optional.ofNullable(Blog.info(rep)).ifPresent(blog -> {
            this.blogInfo.setBlogName(blog.getName());
            this.blogInfo.setSubtitle(blog.getSubtitle());
            this.blogInfo.setAdminName(blog.getAdmin().getName());
            this.blogInfo.setPrivate(blog.isPrivate());
            this.blogInfo.setCommentable(blog.isCommentable());
            this.blogInfo.setSince(blog.getCreateDateTime());
            this.blogInfo.setPostsCount(Post.count(rep));
            this.blogInfo.setCommentsCount(Comment.count(rep));
            this.blogInfo.setInitialized(true);
        });
    }

    public void setBlogInfo(BlogInfo blogInfo) {
        synchronized (this.blogInfo) {
            this.blogInfo = blogInfo;
        }
    }

    public BlogInfo getBlogInfo() {
        synchronized (blogInfo) {
            return blogInfo;
        }
    }
    
    public void updateBlogInfo() {
        synchronized (blogInfo) {
            init();
        }
    }
    
    public void updateBlogPostsAndCommentsCount() {
        synchronized (blogInfo) {
            this.blogInfo.setPostsCount(Post.count(rep));
            this.blogInfo.setCommentsCount(Comment.count(rep));
        }
    }

    @Data
    public static class BlogInfo {
        private String blogName;
        private String subtitle;
        private String adminName;
        private boolean isPrivate;
        private boolean isCommentable;
        private ZonedDateTime since;
        private long postsCount;
        private long commentsCount;
        private boolean isInitialized;
    }

}
