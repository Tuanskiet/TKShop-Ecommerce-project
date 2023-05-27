package com.poly.TKShop.service.impl;

import com.poly.TKShop.config.MyUserDetails;
import com.poly.TKShop.converter.UserConvert;
import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.entity.Role;
import com.poly.TKShop.entity.User;
import com.poly.TKShop.exception.UserException;
import com.poly.TKShop.repository.UserRepository;
import com.poly.TKShop.service.RoleService;
import com.poly.TKShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.findByUsername(username);
        if(!findUser.isPresent()){
            throw new UsernameNotFoundException("Username not found!");
        }
        return new MyUserDetails(findUser.get());
    }

    @Override
    public UserDto createNewUser(UserDto userDto) {
        Optional<User> findUserByUsername = userRepository.findByUsername(userDto.getUsername());
        if(findUserByUsername.isPresent()){
            System.out.println("user taken");
            throw new UserException("Username already taken!");
        }

        Optional<User> findUserByEmail = userRepository.findByEmail(userDto.getEmail());
        if(findUserByEmail.isPresent()){
            System.out.println("email taken");
            throw new  UserException("Email already taken!");
        }

        User newUser = UserConvert.toUser(userDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Optional<Role> role_user =  roleService.findByName("ROLE_USER");
        if(role_user.isPresent()){
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role_user.get());
            newUser.setRoles(roleSet);
        }else{
            throw new UserException("Role is not set!");
        }
        userRepository.save(newUser);
        return userDto;
    }

    @Override
    public UserDto updateUser(int id, UserDto newUser) {
        Optional<User> userUpdated = userRepository.findById(id);
        if(userUpdated.isPresent()){
            userUpdated.get().setUsername(newUser.getUsername());
            userUpdated.get().setPassword(passwordEncoder.encode(newUser.getPassword()));
            userUpdated.get().setEmail(newUser.getEmail());
            userUpdated.get().setEnabled(newUser.isEnabled());
        }else{
            throw new UserException("User not found with id : " +  id);
        }
        userRepository.save(userUpdated.get());
        return newUser;
    }
//DataIntegrityViolationException

}
