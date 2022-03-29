package com.admin.koperasi.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataKoperasi {

    private String namaKoperasi;
    private Long jumlahSimpanan;
    private Long trSimpanan;
    private Long jumlahPinjaman;
    private Long trPinjaman;
    private Long jumlahNasabah;
}
