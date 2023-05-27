package com.poly.TKShop.service.impl;

import com.poly.TKShop.config.MyUserDetails;
import com.poly.TKShop.converter.UserConvert;
import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.entity.Role;
import com.poly.TKShop.entity.User;
import com.poly.TKShop.exception.UserNotFoundException;
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
        User newUser = UserConvert.toUser(userDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Optional<Role> role_user =  roleService.findByName("ROLE_USER");
        if(role_user.isPresent()){
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role_user.get());
            newUser.setRoles(roleSet);
        }else{
            throw new UserNotFoundException("Role is not set!");
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
            throw new UserNotFoundException("User not found with id : " +  id);
        }
        userRepository.save(userUpdated.get());
        return newUser;
    }
//DataIntegrityViolationException

}
