package com.poly.TKShop.controller;


import com.poly.TKShop.config.MyUserDetails;
import com.poly.TKShop.entity.User;
import com.poly.TKShop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


//@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String testOAuth2(){
        return "/main";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "/login";
    }


    // verify token with email
    @GetMapping("/verify-token")
    public String verifyEmail(@RequestParam(name="token") String token, Model model){
        boolean validToken = userService.verifyEmail(token);
        if(!validToken){
            model.addAttribute("valid-token", false);
            return "/verify-token";
        }
        return "/account/new-password";
    }

    //check new pass id valid
    @PostMapping("/check-new-password")
    public String checkNewPass(Model model, HttpServletRequest request){
        String new_password = request.getParameter("new_password");
        System.out.println(new_password);
        String re_new_password = request.getParameter("re_new_password");
        String email = (String) request.getSession().getAttribute("email");
        boolean checkUpdate = userService.updateNewPassword(email, new_password);
        if(!checkUpdate){
            model.addAttribute("new_password", new_password);
            model.addAttribute("re_new_password", re_new_password);
        }
        return "/login";
    }

}
