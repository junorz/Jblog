package com.junorz.jblog.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junorz.jblog.context.bean.AppInfo.BlogInfo;
import com.junorz.jblog.context.dto.BlogCreateDTO;
import com.junorz.jblog.context.utils.AppInfoUtil;
import com.junorz.jblog.context.utils.ControllerUtil;
import com.junorz.jblog.service.BlogService;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("")
    public ResponseEntity<BlogInfo> info() {
        return ControllerUtil.ok(AppInfoUtil.getBlogInfo());
    }

    @PostMapping("/create")
    public ResponseEntity<BlogInfo> create(@Valid @RequestBody BlogCreateDTO dto) {
        blogService.create(dto);
        return ControllerUtil.ok(AppInfoUtil.getBlogInfo());
    }
    
    @GetMapping("/auth")
    public ResponseEntity<Boolean> isLoggedIn() {
        boolean isAnonymous = true;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            isAnonymous = false;
        }
        return ControllerUtil.ok(isAnonymous ? false : true);
    }

}
