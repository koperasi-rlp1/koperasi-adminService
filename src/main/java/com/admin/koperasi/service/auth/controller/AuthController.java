package com.admin.koperasi.service.auth.controller;

import com.admin.koperasi.service.auth.dao.Login;
import com.admin.koperasi.service.auth.model.InfoLogin;
import com.admin.koperasi.service.auth.model.StatusLogin;
import com.admin.koperasi.service.auth.model.UserAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private Login login;


    @PostMapping("/login")
    public ResponseEntity<StatusLogin> login(@RequestBody InfoLogin infoLogin) throws Exception {
        StatusLogin statusLogin = new StatusLogin();
        if (infoLogin != null) {
            String username = infoLogin.getUserName();
            Optional<UserAdmin> useradmindb = login.getUserAdminById(username);
            if (useradmindb.isPresent() && Objects.equals(username, useradmindb.get().getUserName())) {
                String password = infoLogin.getUserPassword();
                if (Objects.equals(password, useradmindb.get().getUserPassword())) {
                    login.clearLoginStory(infoLogin.getUserName());
                    String token = UUID.randomUUID().toString();
                    statusLogin.setIsValid(true);
                    statusLogin.setTokenKey(token);
                    statusLogin.setFullName(useradmindb.get().getFullName());
                    List<String> roles = login.getRolesByUserName(infoLogin.getUserName());
                    statusLogin.setRoles(roles);
                    Map<String, Object> paramlogin = new HashMap<>();
                    paramlogin.put("userid", useradmindb.get().getId());
                    paramlogin.put("token", token);
                    login.insertInfoLogin(paramlogin);
                } else {
                    statusLogin.setIsValid(false);
                    statusLogin.setTokenKey(null);
                }
            }
        } else {
            statusLogin.setIsValid(false);
            statusLogin.setTokenKey(null);
        }
        return ResponseEntity.ok().body(statusLogin);
    }

    @PostMapping(path = "/checking")
    public ResponseEntity<StatusLogin> cekLoginValid(@RequestBody InfoLogin infoLogin) {
        return ResponseEntity.ok().body(login.cekLoginValid(infoLogin));
    }

    @DeleteMapping(path = "/logout/{token}")
    public ResponseEntity<?> logout(@PathVariable("token") String token) {
        try {
            login.logout(token);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}