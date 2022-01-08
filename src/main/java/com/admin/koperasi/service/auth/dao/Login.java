package com.admin.koperasi.service.auth.dao;

import com.admin.koperasi.service.auth.model.InfoLogin;
import com.admin.koperasi.service.auth.model.StatusLogin;
import com.admin.koperasi.service.auth.model.UserAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class Login {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate template;

    public Optional<UserAdmin> getUserAdminById(String username) {
        String SQL = "SELECT ID, USERNAME, PASSWORD, NAME FROM TA_USER WHERE USERNAME = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL, (rs, rownum) -> {
                UserAdmin kab = new UserAdmin();
                kab.setUserName(rs.getString("USERNAME"));
                kab.setFullName(rs.getString("NAME"));
                kab.setUserPassword(rs.getString("PASSWORD"));
                kab.setId(rs.getString("ID"));
                return kab;
            }, username));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<String> getRolesByUserName(String username){
        String query = "SELECT ROLE_NAME FROM TA_ROLE TR JOIN TA_USER_ROLE TUR ON (TR.ID = TUR.ROLES_ID)" +
                " JOIN TA_USER TU ON (TU.ID = TUR.USER_ID) WHERE TU.USERNAME = ?";

        Object param[] = {username};

        List<String> prop = jdbcTemplate.query(query, (rs, rownum) ->{
            return rs.getString("ROLE_NAME");
        }, param);

        return prop;
    }

    public StatusLogin cekLoginValid(InfoLogin user){
        String baseQuery = "SELECT TU.USERNAME FROM TA_LOGIN_INFO TLI INNER " +
                "JOIN TA_USER TU ON TU.ID = TLI.USER_ID where TLI.TOKEN_KEY = ?";
        StatusLogin slogin = new StatusLogin();
        try{
            boolean isValid = false;
            Optional<InfoLogin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                InfoLogin use = new InfoLogin();
                use.setUserName(rs.getString("USERNAME"));
                return use;
            },user.getTokenKey()));
            if (hasil.isPresent()){
                if(hasil.get().getUserName() != null){
                    List<String> rolesName = getRolesByUserName(hasil.get().getUserName());
                    slogin.setIsValid(true);
                    slogin.setRoles(rolesName);
                    slogin.setTokenKey(user.getTokenKey());
                }else{
                    slogin.setIsValid(false);
                }
            }
        }catch (Exception e){
            slogin.setIsValid(false);
            e.printStackTrace();
        }
        return slogin;
    }

    public void insertInfoLogin(Map<String, Object> param){
        String SQL = "INSERT INTO TA_LOGIN_INFO (USER_ID, TOKEN_KEY) VALUES (?,?)";
        Object parameter[] = {param.get("userid"), param.get("token")};
        jdbcTemplate.update(SQL, parameter);
    }

    public void logout(String token) throws DataAccessException {
        String baseQuery = "DELETE FROM TA_LOGIN_INFO WHERE TOKEN_KEY = :tokenkey";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tokenkey", token);

        template.update(baseQuery,parameterSource);
    }

    public void clearLoginStory(String userid) throws DataAccessException {
        String baseQuery = "DELETE FROM TA_LOGIN_INFO WHERE USER_ID = :userid";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userid", userid);

        template.update(baseQuery,parameterSource);
    }


}


