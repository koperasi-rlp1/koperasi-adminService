package com.admin.koperasi.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pinjaman {

    private Long id;
    private String idNasabah;
    private String namaNasabah;
    private Long nominalPinjaman;
    private Long noPinjaman;
    private String statusTransaksi;
    private String buktiPembayaran;
    private Date tanggalTransaksi;
    private String deskripsi;
}
