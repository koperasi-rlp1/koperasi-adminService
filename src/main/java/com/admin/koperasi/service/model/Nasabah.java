package com.admin.koperasi.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nasabah {

    private String nip;
    private String idBackup;
    private String namaNasabah;
    private String jabatan;
    private String email;
    private String noHp;
    private String username;
    private String password;
    private String unitOperasional;
    private String statusKeanggotaan;
    private Integer idStatusKeanggotaan;
    private Timestamp createdDate;
    private String fileBuktiPembayaran;
}