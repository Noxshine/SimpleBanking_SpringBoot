package com.example.project2.Service;

import com.example.project2.Entity.Account;
import com.example.project2.JWT.CustomUserDetails;
import com.example.project2.Repository.AccRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private AccRepo accRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = this.accRepo.findByUsername(username);
        if(acc == null){
            throw new UsernameNotFoundException(username);
        } return new CustomUserDetails(acc);
    }
}
