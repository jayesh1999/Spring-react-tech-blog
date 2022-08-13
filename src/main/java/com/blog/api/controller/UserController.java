package com.blog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.UserDto;
import com.blog.api.service.UserService;

@RestController
@EnableWebMvc
@RequestMapping("/api/users")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	//Create the user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserDto  = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED); 
	}
	
	//update single user
	@PutMapping("{userId}")
	public  ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
		UserDto updateUser = this.userService.updateUser(userDto, uid);
		
		return ResponseEntity.ok(updateUser);
	}
	
	
	
	//get the all userF
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
	    return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//get single user
	@GetMapping("/{userId}")
	public  ResponseEntity<UserDto> singleUser(@PathVariable("userId") Integer uid) {
			return ResponseEntity.ok(this.userService.getUserById(uid));
	}
	
	//delete single user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deletesigleUser(@PathVariable("userId")Integer uid) {
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User is deleted successfully",true),HttpStatus.OK);
	}
	
}
