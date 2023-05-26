package com.poly.TKShop.config;




import com.poly.TKShop.hander.EmployeeAuthenticationSuccessHandler;
import com.poly.TKShop.service.CustomOAuth2UserService;
import com.poly.TKShop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private EmployeeAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService()).passwordEncoder(this.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("oauth2/**", "/**").permitAll()
                    .antMatchers("/api/v1/hi", "/h2-console/**", "/login").permitAll()
                    .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN") //role admin
                    .anyRequest().authenticated() //cac req còn lại phải auth
                .and().formLogin()
                    .loginPage("/login").usernameParameter("username").passwordParameter("password") // xử dụng trang login custom
                    .loginProcessingUrl("/login")
                    .successHandler(successHandler) //xử lí khi đăng nhập thành cộng
                .and().oauth2Login().loginPage("/login")
                .userInfoEndpoint().userService(this.customOAuth2UserService).and()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID", "remember-me")
                    .permitAll()
                .and().rememberMe()
                    .key(Arrays.toString(KeyGenerators.secureRandom().generateKey())) // gia tri mac dinh la uniqueAndSecret
//                .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(3600)
//                .and().exceptionHandling().accessDeniedPage("/403")

        ;

        http.headers().frameOptions().disable();
    }




}
