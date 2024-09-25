package com.example.trendtyschool.controller;

import com.example.trendtyschool.dto.request.KhoaRequest;
import com.example.trendtyschool.helper.StringUrlApi;
import com.example.trendtyschool.repository.procedures.KhoaRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(StringUrlApi.API_VERSION)
@RequiredArgsConstructor
public class KhoaController {

    private final KhoaRepository khoaRepository;


    @PostMapping("/khoa")
    public ResponseEntity<String> createKhoa(@RequestBody KhoaRequest khoaRequest, HttpServletRequest request) {
        try {

            return ResponseEntity.ok(khoaRepository.createKhoa(khoaRequest, request));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(khoaRepository.createKhoa(khoaRequest, request));
        }
    }

    @PutMapping("/khoa/{id}")
    public ResponseEntity<String> updateKhoa(@RequestBody KhoaRequest khoaRequest, @PathVariable int id) {
        try {

            return ResponseEntity.ok(khoaRepository.updateKhoa(khoaRequest, id));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(khoaRepository.updateKhoa(khoaRequest, id));
        }
    }
}
