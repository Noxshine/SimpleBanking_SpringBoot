package com.example.project2.Controller;

import com.example.project2.Entity.Account;
import com.example.project2.Entity.TransactionLog;
import com.example.project2.Form.AddMoneyForm;
import com.example.project2.Form.ChangePassForm;
import com.example.project2.Form.SendMoneyForm;
import com.example.project2.Form.SetLimitForm;
import com.example.project2.JWT.CustomUserDetails;
import com.example.project2.Repository.AccRepo;
import com.example.project2.Repository.Transaction_Repo;
import com.example.project2.Service.AccService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private CustomUserDetails customUserDetails ;
    @Autowired
    private AccService accService;

    @Autowired
    private AccRepo accRepo;

    @Autowired
    private Transaction_Repo tranRepo;

    @GetMapping("/welcome")
    public String welcome(){
        System.out.println("hello world");
        return "public content";
    }
    @GetMapping("/")
    @ResponseBody
    public String test() {
        customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = "hello " + customUserDetails.getUsername();
        return name;
    }



    @PostMapping("/addMoney")
    public String addMoney(@Validated @RequestBody AddMoneyForm addMoneyForm){
        customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accService.addMoney(customUserDetails.getAcc(),addMoneyForm.getMoney());
        return "Add money successfully";
    }

    @PostMapping("/withdrawMoney")
    public String withdrawMoney(@Validated @RequestBody AddMoneyForm wdrMoneyForm ){
        customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account acc = customUserDetails.getAcc();
        Long nowMoney = acc.getMoney();
        Long money = wdrMoneyForm.getMoney();
        if(money <= nowMoney - 1000) {
            accService.subMoney(acc, wdrMoneyForm.getMoney());
            return "Withdraw money successfully";
        } else {
            return "Withdraw money unsuccessfully";
        }
    }

    @PostMapping("/sendMoney")
    public String sendMoney(@Validated @RequestBody SendMoneyForm sendMoneyForm){
        customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account acc1 = customUserDetails.getAcc();
        Long acc1_now_money = acc1.getMoney();

        Account acc2 = accRepo.findByAcc_num(sendMoneyForm.getDes_acc_num());

        Long money = sendMoneyForm.getSendMoney();
        String content = sendMoneyForm.getContent();

        if(money <= acc1_now_money - 1000){
            accService.sendMoney(acc1,acc2,money);
            String trancode = String.valueOf(acc1.getAcc_num())+String.valueOf(acc2.getAcc_num())+ accService.getTime();
            TransactionLog tranlog = new TransactionLog(1,trancode,money,acc1.getAcc_num(),
                    acc1.getBank(), acc2.getAcc_num(),acc2.getBank(),content,accService.getTime());
            tranRepo.save(tranlog);
            return "send money successfully";
        } else {
            return "send money unsuccessfully";
        }

    }

    @PostMapping("/setLimit")
    public void setLimit(SetLimitForm setLimitForm){
        customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @PostMapping("/changePass")
    public void changePass(ChangePassForm changePassForm){
        customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String pass = changePassForm.getNewPass();
        String confirm_pass = changePassForm.getConfirm_pass();
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/test")
    public String userAccess() {
        return "User content";
    }

}
