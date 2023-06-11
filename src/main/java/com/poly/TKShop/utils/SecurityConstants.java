package com.poly.TKShop.utils;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {
    public static final String LOGIN_URL = "/login";
    public static final String MY_SECRET_KEY = "gdsfhkjldjsjflksjkf";
    public static final int TIME_EXPIRED_ACCESS_TOKEN = 1000*60*5;
    public static final int TIME_EXPIRED_REFRESH_TOKEN = 1000*60*24;
    @Value("${my_config.domain}")
    public static final String MY_DOMAIN = "";

}
