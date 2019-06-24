package com.junorz.jblog.context.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junorz.jblog.context.bean.AppInfo;
import com.junorz.jblog.context.bean.AppInfo.BlogInfo;

@Component
public class AppInfoUtil {
    
    public static AppInfo appInfo;
    
    @Autowired
    public void setAppInfo(AppInfo appInfo) {
        AppInfoUtil.appInfo = appInfo;
    }
    
    public static BlogInfo getBlogInfo() {
        return appInfo.getBlogInfo();
    }
    
    public static void setBlogInfo(BlogInfo blogInfo) {
        appInfo.setBlogInfo(blogInfo);
    }
    
    public static void updateBlogInfo() {
        appInfo.updateBlogInfo();
    }
    
    public static void increaseBlogPostsCount() {
        synchronized (appInfo) {
            appInfo.getBlogInfo().setPostsCount(appInfo.getBlogInfo().getPostsCount() + 1);
        }
    }
    
    public static void decreaseBlogPostsCount() {
        synchronized (appInfo) {
            appInfo.getBlogInfo().setPostsCount(appInfo.getBlogInfo().getPostsCount() - 1);
        }
    }
    
    public static void increaseBlogCommentsCount() {
        synchronized (appInfo) {
            appInfo.getBlogInfo().setCommentsCount(appInfo.getBlogInfo().getCommentsCount() + 1);
        }
    }
    
    public static void decreaseBlogCommentsCount() {
        synchronized (appInfo) {
            appInfo.getBlogInfo().setCommentsCount(appInfo.getBlogInfo().getCommentsCount() - 1);
        }
    }
    
    // The functions below update posts/comments count by searching in database
    public static void updateBlogPostsAndCommentsCount() {
        appInfo.updateBlogPostsAndCommentsCount();
    }

}
