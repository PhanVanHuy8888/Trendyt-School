package com.example.trendtyschool.repository;

import com.example.trendtyschool.dto.StatusObjectResponse;
import com.example.trendtyschool.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public StatusObjectResponse callLoginProcedure(String userName, String passWord) {
        String sql = "EXEC proc_login_account @userName = :userName, @passWord = :passWord";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userName", userName)
                .addValue("passWord", passWord);

        StatusObjectResponse response = new StatusObjectResponse();

        List<Object> results = namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<Object>() {

            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    if (rs.getMetaData().getColumnCount() == 1 && rs.getMetaData().getColumnName(1).equalsIgnoreCase("ErrorMessage")) {
                        // Nếu có lỗi, set thông tin lỗi và boolean là false
                        response.setBoolean(false);
                        response.setErrorMessage(rs.getString("ErrorMessage"));
                        return null;
                    } else {
                        // Nếu đăng nhập thành công, set boolean là true và trả về tài khoản
                        Account account = new Account();
                        account.setUserName(rs.getString("userName"));
                        // Ánh xạ các trường khác...
                        response.setBoolean(true);
                        response.setErrorMessage("Not Message");
                        return account;
                    }
                } catch (SQLException e) {
                    // Bắt lỗi SQL và set thông báo lỗi
                    response.setBoolean(false);
                    response.setErrorMessage(e.getMessage());
                    return null;
                }

            }

        });

        return response;
    }
}
