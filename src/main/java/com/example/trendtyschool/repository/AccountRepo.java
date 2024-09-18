package com.example.trendtyschool.repository;

import com.example.trendtyschool.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    Account findByUserName(String userName);
    Account findById(int id);

}
