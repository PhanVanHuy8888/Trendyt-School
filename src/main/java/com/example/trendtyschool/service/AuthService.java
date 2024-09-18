package com.example.trendtyschool.service;

import com.example.trendtyschool.dto.LoginRequest;
import com.example.trendtyschool.dto.TokenResponse;
import com.example.trendtyschool.model.Token;
import com.example.trendtyschool.repository.AccountRepo;
import com.example.trendtyschool.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepo accountRepo;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    public TokenResponse authenticate(LoginRequest request, HttpServletRequest httpRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var account = accountRepo.findByUserName(request.getUsername());
        String accessToken = jwtService.generateToken(account);

        String refreshToken = jwtService.generateRefreshToken(account);

        String browserName = httpRequest.getHeader("User-Agent");
        String hostName = httpRequest.getRemoteHost();
        String ipAdress = httpRequest.getRemoteAddr();

        userRepository.createToken(
                Token.builder()
                        .idAccount(account.getId())
                        .accessToken(accessToken)
                        .refeshToken(refreshToken)
                        .browserName(browserName)
                        .hostName(hostName)
                        .expriseAccess(jwtService.expiryTime())
                        .ipAddress(ipAdress)
                        .build()

        );

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
