package com.admin.koperasi.service.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdmin {

    private String id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String userName;
    private String userPassword;
}

