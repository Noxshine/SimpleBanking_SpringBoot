package com.example.project2.WebSecurityConfig;

import com.example.project2.JWT.JwtAuthFilter;
import com.example.project2.JWT.JwtLoginFilter;
import com.example.project2.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }
    @Bean
    public AuthEntryPoint authEntryPoint() {
        return new AuthEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().authenticationEntryPoint(authEntryPoint());
        http.authorizeRequests()
                // Need Authentication
                .antMatchers("/api/auth/**").permitAll() // cho phep tat ca mn truy cap dia chi nay
                .antMatchers("/api/admin/**").hasAuthority("ADMIN")
                .antMatchers("/api/checker/**").hasAuthority("CHECKER")
                .antMatchers("/api/user/**").hasAuthority("USER")

                .anyRequest().authenticated().and().csrf().disable() ; //

                // filter 1
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtLoginFilter("/login",authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class);
                // filter 2
    }
}
