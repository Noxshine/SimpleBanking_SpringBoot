package com.example.project2.Repository;

import com.example.project2.Entity.Account;
import com.example.project2.Entity.ERole;
import com.example.project2.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(ERole role);
}
