package com.admin.koperasi.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaksiApproval {
    private Integer nip;
    private Integer no;
    private Integer id;
    private Integer idApproval;
    private String jenisTransaksi;
    private String tanggal;
    private Time waktu;
    private Long nominalTransaksi;
    private String idNasabah;
    private String buktiPembayaran;
    private String deskripsi;
    private String namaNasabah;
}
