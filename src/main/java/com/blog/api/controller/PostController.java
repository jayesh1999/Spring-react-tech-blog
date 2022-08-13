package com.blog.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.config.AppConstants;
import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.service.FileService;
import com.blog.api.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
//	create
	
	@PostMapping("/userid/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId")Integer userId, @PathVariable("categoryId") Integer categoryId){
		
	PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		
		
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
//	get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer userId){
		List<PostDto> posts  = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}

//	get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId){
		List<PostDto> posts  = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
// get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue=AppConstants.SORT_BY, required = false)String sortBy,@RequestParam(value= "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir ){
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
// get Single post
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePosts(@PathVariable("postId")Integer postId){
		PostDto Post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(Post,HttpStatus.OK);
	}
	
// delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable	("postId") Integer postId){
		this.postService.deletePost(postId);
		return new ApiResponse("Post Deleted Successfully", true);
	}
	
// update post by post Id
	@PutMapping("/posts/{postId}")
	public  ResponseEntity<PostDto> updatePost(@RequestBody PostDto postdto, @PathVariable	("postId") Integer postId){
		PostDto updatedPost = this.postService.updatePost(postdto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
// search the post
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> SearchPostByTitle(@PathVariable("keyword")String keyword){
		List<PostDto> result = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
// post Image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable("postId") Integer postId) throws IOException{
		String fileName = this.fileService.uploadImage(path, image);
	PostDto postDto = 	this.postService.getPostById(postId);
	postDto.setImageName(fileName);
	PostDto updatedPost = this.postService.updatePost(postDto, postId);
	return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	
	}
	
// method to get file
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
}

