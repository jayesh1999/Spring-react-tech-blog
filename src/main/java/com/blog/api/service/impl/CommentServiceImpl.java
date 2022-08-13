package com.blog.api.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Comment;
import com.blog.api.entities.Post;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CommentDto;
import com.blog.api.repositories.CommentRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
	//	System.out.println(commentDto.getContent());
	Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "post_Id", postId));
	Comment comment  = this.modelMapper.map(commentDto,Comment.class);	
	//System.out.println(comment.getContent());
	comment.setPost(post);
	
	Comment savedComment =this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "comment_Id", commentId));
		this.commentRepo.delete(com);
		
	}

}
