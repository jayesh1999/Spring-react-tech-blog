package com.blog.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.entities.User;
import com.blog.api.exceptions.ApiException;
import com.blog.api.payloads.UserDto;
import com.blog.api.security.JwtAuthRequest;
import com.blog.api.security.JwtAuthResponse;
import com.blog.api.security.JwtTokenHelper;
import com.blog.api.service.UserService;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		System.out.println("hello authentication");
		System.out.println(request.getPassword());
		System.out.println(request.getUsername());
		this.authenticate(request.getUsername(),request.getPassword());
		System.out.println("hello");
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		System.out.println(userDetails.getUsername());
		System.out.println(userDetails.getPassword());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus
				.OK);

	}
	
	private void authenticate(String username, String password) throws Exception {

		System.out.println(username + "password" +password);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			System.out.println(authenticationToken);

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new  ApiException("Invalid username or password !!");
			
		}

	}
	
	//register new user
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto user){
		UserDto regestiredUser = this.userService.registerNewUser(user);
		return new ResponseEntity<UserDto>(regestiredUser,HttpStatus.CREATED);
	}
	
	
}
