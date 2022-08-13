package com.blog.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.repositories.UserRepo;

@Service
public class CustomUserDeatilsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("this is username " +username);
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("username", username,0));
		return user;
	}

}
