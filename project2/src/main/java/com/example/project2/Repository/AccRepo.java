package com.example.project2.Repository;

import com.example.project2.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccRepo extends JpaRepository<Account,Long> {
    Account findByUsername(String username);
    @Query(value ="SELECT p.* from user_acc p where p.acc_num = ?1",nativeQuery = true)
    Account findByAcc_num(Long acc_num);

}
