package com.example.trendtyschool.service;

import com.example.trendtyschool.model.Entity.Account;
import com.example.trendtyschool.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepo;

    // load user từ db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByUserName(username);
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + account.getRole().getNameRole())
        );


        return new User(
                account.getUsername(),
                account.getPassword(),
                authorities);
    }

}
