package com.poly.TKShop;


import com.poly.TKShop.converter.UserConvert;
import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.entity.Role;
import com.poly.TKShop.entity.User;
import com.poly.TKShop.repository.RoleRepository;
import com.poly.TKShop.service.RoleService;
import com.poly.TKShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TkShopApiV1Application{
	public static void main(String[] args) {
		SpringApplication.run(TkShopApiV1Application.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner runner(UserService userService, RoleService roleService){
		return args -> {
			userService.createNewUser(new UserDto("tk", "123", "tuankiet@ff", true));
			roleService.newRole(new Role(null, "ROLE_ADMIN"));
//			roleService.newRole(new Role(null, "ROLE_USER"));
//			roleService.addRoleByUser("tk", "ROLE_ADMIN");
//			roleService.addRoleByUser("tk", "ROLE_USER");
		};
	}
}
