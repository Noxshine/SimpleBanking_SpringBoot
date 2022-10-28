package com.example.project2.Entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table (name = "tran_log")
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private int status;
    private String tran_code;// ma giao dich
    private Long money;

    private Long send_num;
    private String send_bank;

    private Long receive_num;
    private String receive_bank;

    private String content;
    private String time;

    public TransactionLog(){};

    public TransactionLog(int status, String tran_code, Long money, Long send_num, String send_bank,
                          Long receive_num, String receive_bank, String content, String time) {
        this.status = status;
        this.tran_code = tran_code;
        this.money = money;
        this.send_num = send_num;
        this.send_bank = send_bank;
        this.receive_num = receive_num;
        this.receive_bank = receive_bank;
        this.content = content;
        this.time = time;
    }
}
