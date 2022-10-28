package com.example.project2.Form;

import lombok.Data;

@Data
public class SetLimitForm {
    private long send_limit;
    private long total_limit_perday;

    public SetLimitForm(){};

    public long getSend_limit() {
        return send_limit;
    }

    public void setSend_limit(long send_limit) {
        this.send_limit = send_limit;
    }

    public long getTotal_limit_perday() {
        return total_limit_perday;
    }

    public void setTotal_limit_perday(long total_limit_perday) {
        this.total_limit_perday = total_limit_perday;
    }
}
