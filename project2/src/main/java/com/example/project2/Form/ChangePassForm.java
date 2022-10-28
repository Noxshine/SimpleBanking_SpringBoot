package com.example.project2.Form;

public class ChangePassForm {
    private String newPass;

    private String confirm_pass;

    public ChangePassForm(){};

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirm_pass() {
        return confirm_pass;
    }

    public void setConfirm_pass(String confirm_pass) {
        this.confirm_pass = confirm_pass;
    }
}
