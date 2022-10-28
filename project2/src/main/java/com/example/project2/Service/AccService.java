package com.example.project2.Service;

import com.example.project2.Entity.Account;
import com.example.project2.Entity.TransactionLog;
import com.example.project2.Repository.AccRepo;
import com.example.project2.Repository.Transaction_Repo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// min : 1000
@Service
@Slf4j
public class AccService {
    @Autowired
    private AccRepo accRepo;
    @Autowired
    private Transaction_Repo tran_repo;

    public List<Account> GetALLAccount(){
        return accRepo.findAll();
    }

    public void addMoney(Account acc,Long money){
        acc.setMoney(acc.getMoney()+money);
        accRepo.save(acc);
        log.info("add money successfully");
    }
    public void subMoney(Account acc, Long money){
        acc.setMoney(acc.getMoney()-money);
        accRepo.save(acc);
        log.info("sub money successfully");
    }
    public void sendMoney(Account acc1,Account acc2, Long money){
        acc1.setMoney(acc1.getMoney()-money);
        accRepo.save(acc1);
        acc2.setMoney(acc2.getMoney()+money);
        accRepo.save(acc2);
        log.info("send money successfully");
    }

    public void changePassword(Account acc,String newPass){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        acc.setPassword(encoder.encode(newPass));
        accRepo.save(acc);
        log.info("change password successfully");
    }

    public String getTime(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

}
