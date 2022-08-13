package com.blog.api.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min = 4, message="Username must be min of 4 characters" )
	private String name;
	
	@Email(message  = "Email address is not valid!!")
	@NotEmpty(message = "Email is require!!")
	private String email;
	
	@NotEmpty(message = "Password is required !!")
	@Size(min = 3, max = 10, message = "password must be min of 3 character and max of 10 characters")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> role = new HashSet<>();
	
	public UserDto(int id, String name, String email, String password, String about) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
	}
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + "]";
	}
	public Set<RoleDto> getRole() {
		return role;
	}
	public void setRole(Set<RoleDto> role) {
		this.role = role;
	}
	public UserDto(int id, @NotEmpty @Size(min = 4, message = "Username must be min of 4 characters") String name,
			@Email(message = "Email address is not valid!!") @NotEmpty(message = "Email is require!!") String email,
			@NotEmpty(message = "Password is required !!") @Size(min = 3, max = 10, message = "password must be min of 3 character and max of 10 characters") String password,
			@NotEmpty String about, Set<RoleDto> role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.role = role;
	}
	
	

}
