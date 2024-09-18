package com.example.trendtyschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TrendtySchoolApplication {

    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String rawPassword = "adminntu";
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        System.out.println("Encoded Password: " + encodedPassword);
        SpringApplication.run(TrendtySchoolApplication.class, args);
    }

}
