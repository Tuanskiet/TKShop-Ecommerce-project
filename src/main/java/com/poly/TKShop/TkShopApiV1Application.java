package com.poly.TKShop;


import com.poly.TKShop.entity.Role;
import com.poly.TKShop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TkShopApiV1Application{
	public static void main(String[] args) {
		SpringApplication.run(TkShopApiV1Application.class, args);
	}

}
