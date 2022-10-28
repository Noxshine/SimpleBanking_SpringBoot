package com.example.project2.JWT;

import com.example.project2.Entity.Account;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.example.project2.Controller.AuthController.decodeValue;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    public JwtLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.printf("JWTLoginFilter.attemptAuthentication: username/password= %s,%s", username, password);
        System.out.println();

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        System.out.println("JWTLoginFilter.successfulAuthentication:");

        // Write Authorization to Headers of Response.
        TokenAuthService.addAuthentication(response, authResult.getName());

        String authorizationString = response.getHeader("Authorization");

        System.out.println("Authorization String=" + authorizationString);
    }
    public boolean checkPasswordFrontEnd(HttpServletRequest request, Account acc)
            throws IOException, ServletException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (bCryptPasswordEncoder.matches(decodeValue(request.getAttribute("password").toString()),
                acc.getPassword())) {
            return true;
        }
        return false;
    }
}
