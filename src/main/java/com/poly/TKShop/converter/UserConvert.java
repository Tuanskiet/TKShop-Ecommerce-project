package com.poly.TKShop.converter;

import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.entity.User;

public class UserConvert {
    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setEnabled(user.isEnabled());
        return userDto;
    }
    public static User toUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getPassword());
        user.setEnabled(userDto.isEnabled());
        return user;
    }

}
