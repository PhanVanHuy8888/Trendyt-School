package com.example.trendtyschool.repository.procedures;

import com.example.trendtyschool.dto.request.KhoaRequest;
import com.example.trendtyschool.model.Entity.Account;
import com.example.trendtyschool.model.Entity.Khoa;
import com.example.trendtyschool.repository.AccountRepository;
import com.example.trendtyschool.repository.KhoaRepo;
import com.example.trendtyschool.service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class KhoaRepository {

    private final AccountRepository accountRepository;
    private final JdbcTemplate jdbcTemplate;

    private final JwtService jwtService;
    private final KhoaRepo khoaRepo;


    public String createKhoa(KhoaRequest khoaRequest, HttpServletRequest request) {
        final String authorization = request.getHeader("Authorization");

        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("Thiếu hoặc không hợp lệ tiêu đề Authorization");
        }

        final String token = authorization.substring("Bearer".length()).trim();

        int accountId;
        try {
            accountId = jwtService.extractClaim(token, claims -> claims.get("accountId", Integer.class));
        } catch (Exception e) {
            throw new RuntimeException("Token không hợp lệ hoặc đã hết hạn");
        }

        // Lấy Account từ database
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
        int idSchool = account.getSchool().getId();

        // Gọi stored procedure
        String sql = "EXEC proc_create_khoa @nameKhoa = ?, @maKhoa = ?, @idSchool = ?, @idAccount = ?";
        String resultMessage = jdbcTemplate.queryForObject(
                sql,
                new Object[]{khoaRequest.getNameKhoa(), khoaRequest.getMaKhoa(), idSchool, accountId},
                String.class
        );

        return resultMessage;
    }

    public String updateKhoa(KhoaRequest khoaRequest, int id){
        String sql = "EXEC proc_update_khoa @id = ?, @nameKhoa = ?, @maKhoa = ?";

        // Gọi stored procedure và nhận thông báo
        String resultMessage = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id, khoaRequest.getNameKhoa(), khoaRequest.getMaKhoa()},
                String.class
        );
        String result;
        return resultMessage;
    }

}
