package com.poly.TKShop.service;

import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService extends UserDetailsService {
    UserDto createNewUser(UserDto userDto);

    UserDto updateUser(int id, UserDto userDto);

    void deleteUser(int id);

    List<UserDto> getAllUsers();
}
