package com.admin.koperasi.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataNasabah {

    private Integer no;
    private String nip;
    private String namaNasabah;
    private String email;
    private String noHp;
    private Long jumlahSimpanan;
    private Long jumlahPinjaman;
}

