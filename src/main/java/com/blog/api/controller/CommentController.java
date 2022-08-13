package com.blog.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.CommentDto;
import com.blog.api.service.CommentService;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments/")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId")Integer postId){
		System.out.println(commentDto.getContent());
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted successfully !!",true), HttpStatus.OK);
	}
	
	
	

}
