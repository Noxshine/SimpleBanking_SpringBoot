package com.example.project2.Request;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private List<String> role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRoles(List<String> role) {
        this.role = role;
    }
}
