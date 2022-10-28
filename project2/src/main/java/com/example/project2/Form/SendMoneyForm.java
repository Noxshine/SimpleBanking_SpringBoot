package com.example.project2.Form;

import lombok.Data;

@Data
public class SendMoneyForm {
    private Long des_acc_num;
    private Long sendMoney;
    private String content;

    public SendMoneyForm(){};

    public Long getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(Long sendMoney) {
        this.sendMoney = sendMoney;
    }
}
