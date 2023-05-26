package com.poly.TKShop.database;

import com.poly.TKShop.entity.Role;
import com.poly.TKShop.entity.User;
import com.poly.TKShop.repository.RoleRepository;
import com.poly.TKShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class database implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user =  new User().builder()
                .username("tk")
                .password(passwordEncoder.encode("123"))
                .enabled(true)
                .build();

        Role role = new Role().builder()
                .name("ROLE_USER")
                .build();
        userRepository.save(user);
        roleRepository.save(role);
//        userRoleRepository.save(new UserRole().builder()
//                .user(user)
//                .role(role)
//                .build());
    }
}
