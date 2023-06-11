package com.poly.TKShop.service.impl;

import com.poly.TKShop.config.MyUserDetails;
import com.poly.TKShop.converter.UserConvert;
import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.entity.Role;
import com.poly.TKShop.entity.User;
import com.poly.TKShop.exception.UserException;
import com.poly.TKShop.repository.RoleRepository;
import com.poly.TKShop.repository.UserRepository;
import com.poly.TKShop.service.RoleService;
import com.poly.TKShop.service.UserService;
import com.poly.TKShop.utils.SendEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private  SendEmail sendEmail;


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
           log.info("user is taken");
            throw new UserException("Username already taken!");
        }

        Optional<User> findUserByEmail = userRepository.findByEmail(userDto.getEmail());
        if(findUserByEmail.isPresent()){
            log.info("email taken");
            throw new  UserException("Email already taken!");
        }

        User newUser = UserConvert.toUser(userDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Optional<Role> role_user =  roleService.findByName("ROLE_USER");
        Set<Role> roleSet = new HashSet<>();
        if(!role_user.isPresent()){
            role_user = Optional.of(roleRepository.save(new Role(null,"ROLE_USER")));
        }
        roleSet.add(role_user.get());
        newUser.setRoles(roleSet);
        userRepository.save(newUser);
        return userDto;
    }
    @Override
    public UserDto updateUser(int id, UserDto newUser) {
        Optional<User> userUpdated = userRepository.findById(id);
        if(userUpdated.isPresent()){
            BeanUtils.copyProperties(newUser, userUpdated);
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

    @Override
    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserException("User not found with id : " +  id);
        }
        userRepository.deleteById(id);
    }
    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = userRepository.findAll().stream()
                .map(user -> UserConvert.toUserDto(user))
                .collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public boolean resetPassword(String email) {
        System.out.println("rm : "+email);
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            String token = UUID.randomUUID().toString();
//            ConfirmationToken confirmationToken = new ConfirmationToken(
//                    token,
//                    LocalDateTime.now(),
//                    LocalDateTime.now().plusMinutes(10),
//                    user.get()
//            );
//            confirmationTokenRepository.save(confirmationToken);
            try {
                System.out.println("email to : " + email);
                sendEmail.resetPasswordWithToken(email, token);
            } catch (MessagingException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else{
            throw new UserException("Email not available!");
        }
        return true;
    }

    @Override
    public boolean verifyEmail(String token) {
        boolean resultValue = false;
//        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
//        if(confirmationToken.isPresent()){
//            LocalDateTime timeExpired = confirmationToken.get().getExpiredAt();
//            if(timeExpired.isBefore(LocalDateTime.now())){
//                System.out.println("token is expired !");
//            }else{
//                resultValue = true;
//            }
//        }
        return resultValue;
    }

    @Override
    public boolean updateNewPassword(String email, String newPassword) {
        String newPasswordEncode = passwordEncoder.encode(newPassword);
        userRepository.updateNewPassword(email, newPasswordEncode);
        return false;
    }
}
