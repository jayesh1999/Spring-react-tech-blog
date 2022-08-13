package com.blog.api.service;

import java.util.List;

import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;

public interface PostService {

	//create
	public PostDto createPost(PostDto postDto, Integer userId, Integer catagoryId);
	
	//update
	public PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	public void deletePost(Integer postId);
	
	//get single post
	public PostDto getPostById(Integer postId);
	
	//get all posts
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy,String sortDir);
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all post by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//search post
	List<PostDto> searchPosts(String keyword);
}
