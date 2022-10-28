package com.example.project2.JWT;

import com.example.project2.Service.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// ktra authorizing string, nếu hợp lệ đi đến controller
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenAuthService tokenAuthService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            System.out.println("do jwt auth filter");
            String jwt = getJwtFromRequest(request);
            if(jwt != null && tokenAuthService.validateToken(jwt)){
                String username = tokenAuthService.getUsernameFromJWT(jwt);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("ok1");
            } else {
                log.debug("No valid JWT token");
            }

            log.info("do filterchain");
            filterChain.doFilter(request,response);

        } catch (Exception e){
            log.error("Could not set user authentication in security context", e);
        }
        log.info("ok2");
    }

    private String getJwtFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.split(" ")[1].trim();
        }
        return null;
    }

}
