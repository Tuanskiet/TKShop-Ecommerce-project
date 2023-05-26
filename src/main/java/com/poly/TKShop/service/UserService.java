package com.poly.TKShop.service;

import com.poly.TKShop.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;



public interface UserService extends UserDetailsService {
    UserDto createNewUser(UserDto userDto);
}
