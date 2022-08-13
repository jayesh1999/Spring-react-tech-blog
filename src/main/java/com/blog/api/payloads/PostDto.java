package com.blog.api.payloads;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.blog.api.entities.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PostDto {
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private LocalDateTime  addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();

	
	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public LocalDateTime  getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDateTime  addedDate) {
		this.addedDate = addedDate;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
