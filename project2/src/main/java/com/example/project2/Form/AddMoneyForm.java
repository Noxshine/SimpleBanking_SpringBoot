package com.example.project2.Form;

import lombok.Data;

@Data
public class AddMoneyForm {
    private Long money;
    public Long getMoneyAdd() {
        return money;
    }
}
