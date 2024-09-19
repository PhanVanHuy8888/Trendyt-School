package com.example.trendtyschool.controller;

import com.example.trendtyschool.dto.reponse.StatusObjectResponse;
import com.example.trendtyschool.dto.reponse.TokenResponse;
import com.example.trendtyschool.dto.request.LoginRequest;
import com.example.trendtyschool.helper.StringUrlApi;
import com.example.trendtyschool.model.ClassModel.TokenAddHeaderModel;
import com.example.trendtyschool.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.trendtyschool.helper.StringConfiguration.stringKeyAccessToken;
import static com.example.trendtyschool.repository.TokenHeaderReponse.AddTokenHeaderClient;

@RestController
@RequestMapping(StringUrlApi.API_LOGIN)
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @PostMapping()
    public ResponseEntity<StatusObjectResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {
        StatusObjectResponse loginResponse = userRepository.callLoginProcedure(request.getUsername(), request.getPassword(), httpRequest);

        if (loginResponse.isBoolean()) {
            TokenResponse tokenResponse = (TokenResponse) loginResponse.getObject();
            TokenAddHeaderModel tokenAddHeaderModel = new TokenAddHeaderModel(){
                {
                    setKey(stringKeyAccessToken);
                    setValue(tokenResponse.getAccessToken());
                    setExpireDay(60); // 60 ngày
                    setHttpServletResponse(response);
                }
            };
            AddTokenHeaderClient(tokenAddHeaderModel); // Add token vào header
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }

    @GetMapping()
    public String logo() {
        return "logo";
    }

}
