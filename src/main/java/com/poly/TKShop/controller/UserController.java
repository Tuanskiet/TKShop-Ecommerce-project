package com.poly.TKShop.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class UserController {
    @GetMapping("/")
    public String testOAuth2(){
        return "/main";
    }


    @GetMapping("/login")
    public String loginForm(){
        return "/login";
    }

        @GetMapping("/admin/index")
    public String adminMainPage(){
        return "/admin/index";
    }

    @GetMapping("/admin/test")
    public String adminTest(){
        return "/admin/test";
    }

    @GetMapping("/success")
    public String userMainPage(){
        return "/success";
    }


}
