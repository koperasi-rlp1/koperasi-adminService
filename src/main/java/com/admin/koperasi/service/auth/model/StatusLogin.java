package com.admin.koperasi.service.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusLogin {

    private String userName;
    private String fullName;
    private String tokenKey;
    private Boolean isValid;
    private List<String> roles;

}