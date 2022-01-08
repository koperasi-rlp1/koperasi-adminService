package com.admin.koperasi.service.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupUser {

    private String id;
    private String idUser;
    private Integer idRole;
    private String userName;
    private String roleName;
}
