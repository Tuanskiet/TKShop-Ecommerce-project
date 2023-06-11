package com.poly.TKShop;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class encodePass {
    public static void main(String[] args) {
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        System.out.println(b.encode("222222"));
    }
}
