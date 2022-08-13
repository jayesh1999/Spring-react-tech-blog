package com.blog.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.service.PostService;


@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId ) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","userId",userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category Id", categoryId));
		
		
		Post post = this.modelMapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		post.setAddedDate(LocalDateTime.now());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);	
		return this.modelMapper.map(updatedPost,PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
	Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post_Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy, String sortDir) {
		
		Sort sort  = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort); 
		
		Page<Post> pagePosts = this.postRepo.findAll(p);
		
		List<Post> allPosts =pagePosts.getContent(); 
		
		List<PostDto> postDtos  = allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat =  this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category" , "category_id", categoryId));
			List<Post> posts  = this.postRepo.findByCategory(cat);
			for (Post postDto : posts) {
				System.out.println(postDto.getContent());
			}
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "user_Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		for (Post postDto : posts) {
			System.out.println(postDto.getContent());
		}
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post>posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> searchPosts = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return searchPosts;
	}

}
