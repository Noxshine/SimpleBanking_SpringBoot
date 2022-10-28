package com.example.project2.Repository;

import com.example.project2.Entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Transaction_Repo extends JpaRepository<TransactionLog,Long> {


}
