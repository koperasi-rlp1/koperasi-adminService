package com.admin.koperasi.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaksi {

    private Integer id;
    private Integer idApproval;
    private String jenisTransaksi;
    private Date tanggal;
    private Time waktu;
    private Long nominalTransaksi;
    private String idNasabah;
    private String buktiPembayaran;
    private String deskripsi;
    private String statusApproval;
}
