package com.junorz.jblog.context.dto;

import java.time.ZonedDateTime;

import com.junorz.jblog.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {

    private long id;
    private String comment;
    private String author;
    private String email;
    private ZonedDateTime createDateTime;
    private long postId;

    public static CommentDTO of(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getComment(), comment.getAuthor(),
                comment.getEmail(), comment.getCreateDateTime(), comment.getPost().getId());
    }
}
