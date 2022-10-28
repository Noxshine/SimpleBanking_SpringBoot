package com.example.project2.Response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String username;
    private String type = "Bearer";
    private String token;
    private List<String> roles;

    public JwtResponse(String username, String token, List<String> roles) {
        this.username = username;
        this.token = token;
        this.roles = roles;
    }

    public JwtResponse() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
