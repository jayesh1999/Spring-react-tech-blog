package com.blog.api.security;

public class JwtAuthRequest {
	
	public String username;
	
	public String password;

	public JwtAuthRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtAuthRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "JwtAuthRequest [username=" + username + ", password=" + password + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
