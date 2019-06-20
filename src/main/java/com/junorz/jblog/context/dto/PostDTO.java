package com.junorz.jblog.context.dto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.junorz.jblog.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDTO {

	private long id;
	private String title;
	private String content;
	private String author;
	private List<CommentDTO> commentList;
	// For home page display
	private long commentsCount;
	private ZonedDateTime createDateTime;
	private ZonedDateTime modifyDateTime;

	public static PostDTO of(Post post, boolean isPostView) {
	    if(isPostView) {
	        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getAuthor().getName(),
	                Optional.ofNullable(post.getCommentList()).map(list -> list.stream().map(c -> CommentDTO.of(c)).collect(Collectors.toList())).orElse(new ArrayList<CommentDTO>()),
	                post.getCommentsCount(), post.getCreateDateTime(), post.getModifyDateTime());
	    }
	    return new PostDTO(post.getId(), post.getTitle(), post.getContent(), "", new ArrayList<CommentDTO>(), post.getCommentsCount(), post.getCreateDateTime(), post.getModifyDateTime()); 
	            
	}

}
