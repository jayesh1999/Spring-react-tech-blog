package com.blog.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.api.config.AppConstants;
import com.blog.api.entities.Role;
import com.blog.api.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo repoRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("application started");

		try {
			Role role = new Role();

			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role, role1);

			List<Role> result = this.repoRepo.saveAll(roles);
			result.forEach((r) -> {
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
