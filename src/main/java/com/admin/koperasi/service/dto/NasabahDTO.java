package com.admin.koperasi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

public class NasabahDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KonfirmasiPendaftaran {

        private Integer nip;
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
}
