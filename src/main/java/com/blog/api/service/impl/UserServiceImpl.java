package com.blog.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.api.config.AppConstants;
import com.blog.api.entities.Role;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.UserDto;
import com.blog.api.repositories.RoleRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());

		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);

		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

		UserDto userDto = this.userToDto(user);

		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub

		List<User> users = this.userRepo.findAll();

		List<UserDto> userDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

		this.userRepo.delete(user);

	}

	// convert dto to user
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());

		return user;
	}

	// convert user to dto
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//			
//			userDto.setId(user.getId());
//			userDto.setAbout(user.getAbout());
//			userDto.setEmail(user.getEmail());
//			userDto.setName(user.getName());
//			userDto.setPassword(user.getPassword());

		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

		// encoded password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRole().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
