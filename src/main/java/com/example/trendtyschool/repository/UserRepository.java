package com.example.trendtyschool.repository;

import com.example.trendtyschool.dto.StatusObjectResponse;
import com.example.trendtyschool.dto.TokenResponse;
import com.example.trendtyschool.model.Account;
import com.example.trendtyschool.model.Role;
import com.example.trendtyschool.model.Token;
//import com.example.trendtyschool.service.JwtService;
import com.example.trendtyschool.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JwtService jwtService;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    public StatusObjectResponse callLoginProcedure(String userName, String passWord, HttpServletRequest httpRequest) {
        String sql = "EXEC proc_login_account @userName = :userName, @passWord = :passWord";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userName", userName)
                .addValue("passWord", passWord);

        StatusObjectResponse response = new StatusObjectResponse();

        List<Object> results = namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<Object>() {

            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    if (columnCount == 1 && metaData.getColumnName(1).equalsIgnoreCase("ErrorMessage")) {
                        // Nếu có lỗi, set thông tin lỗi và boolean là false
                        response.setBoolean(false);
                        response.setErrorMessage(rs.getString("ErrorMessage"));
                        System.out.println(rs.getString("ErrorMessage"));
                        return null;
                    } else {
                        // Nếu đăng nhập thành công, set boolean là true và trả về tài khoản
                        Account account = new Account();
                        account.setId(rs.getInt("id"));  // Đảm bảo 'id' là tên cột đúng
                        account.setUserName(rs.getString("userName"));
                        Role role = new Role();
                        role.setNameRole(rs.getString("nameRole")); // Đảm bảo 'nameRole' là tên cột đúng
                        account.setRole(role);
                        // Thiết lập các trường khác...

                        response.setBoolean(true);
                        response.setErrorMessage("No Message");
                        String accessToken = jwtService.generateToken(account);
                        String refreshToken = jwtService.generateRefreshToken(account);

                        response.setToken(TokenResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .build());

                        // Lưu token và các thông tin bổ sung
                        String browserName = httpRequest.getHeader("User-Agent");
                        String hostName = httpRequest.getRemoteHost();
                        String ipAddress = httpRequest.getRemoteAddr();
                        Object o = createToken(
                                Token.builder()
                                        .idAccount(account.getId())
                                        .accessToken(accessToken)
                                        .refeshToken(refreshToken)
                                        .browserName(browserName)
                                        .hostName(hostName)
                                        .expriseAccess(jwtService.expiryTime())
                                        .ipAddress(ipAddress)
                                        .createTime(new Date())
                                        .build()
                        );

                        return account;
                    }
                } catch (SQLException e) {
                    // Xử lý lỗi SQL và thiết lập thông báo lỗi
                    response.setBoolean(false);
                    response.setErrorMessage(e.getMessage());
                    return null;
                }
            }


        });

        return response;
    }

    public Object createToken(Token token) {

        String sql = "{call create_token(:tokenData)}";

        // Định nghĩa kiểu dữ liệu bảng
        String tableTypeSql = "DECLARE @tokenData TokenType; "
                + "INSERT INTO @tokenData (accessToken, refeshToken, idAccount, expriseAccess, ipAddress, hostName, browserName, createTime, course) "
                + "VALUES (:accessToken, :refreshToken, :idAccount, :expriseAccess, :ipAddress, :hostName, :browserName, :createTime, :course); "
                + "EXEC create_token @tokenData = @tokenData;";

        // Tạo đối tượng MapSqlParameterSource để truyền tham số
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("accessToken", token.getAccessToken())
                .addValue("refreshToken", token.getRefeshToken())
                .addValue("idAccount", token.getIdAccount())
                .addValue("expriseAccess", token.getExpriseAccess())
                .addValue("ipAddress", token.getIpAddress())
                .addValue("hostName", token.getHostName())
                .addValue("browserName", token.getBrowserName())
                .addValue("createTime", token.getCreateTime())
                .addValue("course", token.getCourse());

//                .addValue("accessToken", "aaa")
//                .addValue("refreshToken", "ádasd")
//                .addValue("idAccount", 2)
//                .addValue("expriseAccess", "2024-09-16")
//                .addValue("ipAddress", "sds")
//                .addValue("hostName", "sds")
//                .addValue("browserName", "sda")
//                .addValue("createTime", "2024-09-16")
//                .addValue("course", "á");

        List<Object> results = namedParameterJdbcTemplate.query(tableTypeSql, parameters, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    if (rs.getMetaData().getColumnCount() == 1 && rs.getMetaData().getColumnName(1).equalsIgnoreCase("ErrorMessage")) {
                        // Nếu có lỗi, xử lý lỗi
                        System.out.println(rs.getString("ErrorMessage"));
                        return null;
                    } else {
                        // Xử lý kết quả thành công (nếu có)
                        // Ví dụ: Ánh xạ kết quả vào một đối tượng
                        Object result = new Object();
                        // Ánh xạ các trường từ ResultSet sang đối tượng result...
                        return result;
                    }
                } catch (SQLException e) {
                    // Bắt lỗi SQL và xử lý
                    return null;
                }
            }
        });


        // Thực thi stored procedure
//        namedParameterJdbcTemplate.update(tableTypeSql, parameters);
        return null;

    }
}