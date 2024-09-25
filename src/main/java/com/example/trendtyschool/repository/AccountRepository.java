package com.example.trendtyschool.repository;

import com.example.trendtyschool.model.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUserName(String userName);
    Optional<Account> findById(int id);

}
