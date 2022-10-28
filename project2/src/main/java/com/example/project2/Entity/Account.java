package com.example.project2.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user_acc")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private Long acc_num;
    private String username;
    private String password;
    private Long money= 0L;
    private String Bank;
    private Long send_limit;
    private Long total_limit_perday;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> role = new HashSet<>();

    public Account(){}
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(Long acc_num) {
        this.acc_num = acc_num;
    }

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

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Set<Role> getRoles() {
        return role;
    }

    public void setRoles(Set<Role> roles) {
        this.role = roles;
    }

    public Long getSend_limit() {
        return send_limit;
    }

    public void setSend_limit(Long send_limit) {
        this.send_limit = send_limit;
    }

    public Long getTotal_limit_perday() {
        return total_limit_perday;
    }

    public void setTotal_limit_perday(Long total_limit_perday) {
        this.total_limit_perday = total_limit_perday;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }
}
