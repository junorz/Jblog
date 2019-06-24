package com.junorz.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.junorz.jblog.context.dto.PostCreateDTO;
import com.junorz.jblog.context.dto.PostDTO;
import com.junorz.jblog.context.utils.ControllerUtil;
import com.junorz.jblog.service.PostService;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @GetMapping("")
    public ResponseEntity<List<PostDTO>> findAll(@RequestParam Map<String, String> params) {
        // Return all posts when paging parameters not specified.
        if (Strings.isNullOrEmpty(params.get("pageNum")) || Strings.isNullOrEmpty(params.get("limit")) || Strings.isNullOrEmpty(params.get("isPostView"))) {
            List<PostDTO> postDTOList = postService.findAll().stream()
                    .map(p -> PostDTO.of(p, true))
                    .collect(Collectors.toList());
            return ControllerUtil.ok(postDTOList);
        }
        // The page number
        int pageNum = Integer.parseInt(params.get("pageNum"));
        // Items count per page
        int limit = Integer.parseInt(params.get("limit"));
        boolean isPostView = Boolean.parseBoolean(params.get("isPostView"));
        List<PostDTO> postDTOList = postService.paging(pageNum, limit).stream()
                .map(p -> PostDTO.of(p, isPostView))
                .collect(Collectors.toList());
        return ControllerUtil.ok(postDTOList);
    }
    
    @PostMapping("/create")
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostCreateDTO dto) {
        return ControllerUtil.ok(PostDTO.of(postService.create(dto), false));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable("id") String id) {
        long postId = Long.parseLong(id);
        return ControllerUtil.ok(Optional.ofNullable(postService.findById(postId)).map(post -> PostDTO.of(post, true)).orElse(null));
    }
    
    @PostMapping("/{id}/delete")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        long postId = Long.parseLong(id);
        return ControllerUtil.ok(postService.delete(postId));
    }
    
    @PostMapping("/{id}/update")
    public ResponseEntity<Boolean> update(@PathVariable("id") String id, @Valid @RequestBody PostCreateDTO dto) {
        long postId = Long.parseLong(id);
        return ControllerUtil.ok(postService.update(postId, dto));
    }
    
}
