package com.singh.rdst;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.singh.rdst.config.AppConstants;
import com.singh.rdst.entity.Role;
import com.singh.rdst.repository.RoleRepo;

@SpringBootApplication
public class BlogingAppApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogingAppApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.encoder.encode("Rohit@123"));
		
		try {
				Role roleAdmin = new Role();
			
				roleAdmin.setRoleId(AppConstants.ROLE_ADMIN);
				roleAdmin.setRoleName("ROLE_ADMIN");
				
				Role roleUser = new Role();
				roleUser.setRoleId(AppConstants.ROLE_USER);
				roleUser.setRoleName("ROLE_USER");
				
				List<Role> userRoles = List.of(roleAdmin,roleUser);
				
				List<Role> list = this.roleRepo.saveAll(userRoles);
				list.forEach(role -> System.out.println(role));
				
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
