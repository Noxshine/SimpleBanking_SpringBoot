package com.example.project2.Controller;

import com.example.project2.Entity.Account;
import com.example.project2.Entity.ERole;
import com.example.project2.Entity.Role;
import com.example.project2.JWT.CustomUserDetails;
import com.example.project2.JWT.TokenAuthService;
import com.example.project2.Repository.AccRepo;
import com.example.project2.Repository.RoleRepo;
import com.example.project2.Request.LoginRequest;
import com.example.project2.Request.SignupRequest;
import com.example.project2.Response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AccRepo accRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenAuthService tokenAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody LoginRequest loginReq){

        JwtResponse jwtRes = new JwtResponse();
        String username = loginReq.getUsername();
        String password = loginReq.getPassword();

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password));

        CustomUserDetails userDt = (CustomUserDetails) auth.getPrincipal();
        try{
            Account acc = accRepo.findByUsername(username);
            if(acc==null)
                throw new Exception("Username does not exist");

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(decodeValue(password), acc.getPassword()))
                throw new Exception("Invalid password");

            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = tokenAuthService.generateToken(auth);

            List<String> roles = userDt.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            jwtRes.setUsername(username);
            jwtRes.setToken(jwt);
            jwtRes.setRoles(roles);

        } catch (Exception e){
            System.out.println(e);
        }
        // nếu có username trong db trả về jwt response
        return ResponseEntity.ok(jwtRes);
    }
    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupReq){
        Account tmp = accRepo.findByUsername(signupReq.getUsername());
        if(tmp!=null){
            return ResponseEntity
                    .badRequest()
                    .body("Username is already taken");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Account acc = new Account(signupReq.getUsername(),
                encoder.encode(signupReq.getPassword()));

        int min = 10000000, max = 99999999;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        acc.setAcc_num((long) random_int);

        List<String> strRoles = signupReq.getRole();
        Set<Role> role = new HashSet<>();

        if(strRoles == null){
            Role userRole = roleRepo.findByName(ERole.USER);
            role.add(userRole);
            System.out.println("No roles found");
        } else {
            strRoles.forEach(rol ->{
                        switch (rol){
                            case "admin":
                                Role adminRole = roleRepo.findByName(ERole.ADMIN);
                                role.add(adminRole);
                            case "user":
                                Role userRole = roleRepo.findByName(ERole.USER);
                                role.add(userRole);
                            case "checker" :
                                Role checkerRole = roleRepo.findByName(ERole.CHECKER);
                                role.add(checkerRole);
                        }
                    }
            );
        }
        acc.setRoles(role);
        acc.setMoney(0L);
        acc.setBank("TNHH1mìnhtao");
        acc.setSend_limit(0L);
        acc.setTotal_limit_perday(0L);
        accRepo.save(acc);
        return ResponseEntity.ok("User registered successfully");
    }
}
