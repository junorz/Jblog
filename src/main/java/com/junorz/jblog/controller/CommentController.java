package com.junorz.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.junorz.jblog.context.dto.CommentCreateDTO;
import com.junorz.jblog.context.dto.CommentDTO;
import com.junorz.jblog.context.utils.ControllerUtil;
import com.junorz.jblog.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    
    private final CommentService commentService;
    
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @GetMapping("")
    public ResponseEntity<List<CommentDTO>> paging(@RequestParam Map<String, String> params) {
        if(Strings.isNullOrEmpty(params.get("pageNum")) || Strings.isNullOrEmpty(params.get("limit"))) {
            return null;
        }
        int pageNum = Integer.parseInt(params.get("pageNum"));
        int limit = Integer.parseInt(params.get("limit"));
        return ControllerUtil.ok(commentService.paging(pageNum, limit).stream().map(CommentDTO::of).collect(Collectors.toList()));
    }
    
    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentCreateDTO dto) {
        return ControllerUtil.ok(CommentDTO.of(commentService.create(dto)));
    }
    
}
