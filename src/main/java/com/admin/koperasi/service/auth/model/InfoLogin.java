package com.admin.koperasi.service.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoLogin {

    private String userName;
    private String userPassword;
    private String tokenKey;

}
