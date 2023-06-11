package com.poly.TKShop.config;




import com.poly.TKShop.filter.CustomAuthenticationFilter;
import com.poly.TKShop.filter.CustomAuthorizationFilter;
import com.poly.TKShop.hander.CustomAccessDeniedHandler;
import com.poly.TKShop.hander.CustomHttp403ForbiddenEntryPoint;
import com.poly.TKShop.service.CustomOAuth2UserService;
import com.poly.TKShop.service.UserService;
import com.poly.TKShop.utils.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomHttp403ForbiddenEntryPoint customHttp403ForbiddenEntryPoint;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("oauth2/**", "/swagger-ui/**").permitAll()
//                    .antMatchers("/api/v1/**").permitAll()
                    .antMatchers(SecurityConstants.LOGIN_URL).permitAll()
                    .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN") //role admin
                    .anyRequest().authenticated() //cac req còn lại phải auth
//                    .and().formLogin()
//                    .loginPage("/login").usernameParameter("username").passwordParameter("password") // xử dụng trang login custom
//                    .loginProcessingUrl("/login") //xử lí khi đăng nhập thành cộng
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
                .and().addFilter(new CustomAuthenticationFilter(this.authenticationManagerBean()))
        ;
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler).and()
                .exceptionHandling().authenticationEntryPoint(customHttp403ForbiddenEntryPoint);
        http.headers().frameOptions().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
