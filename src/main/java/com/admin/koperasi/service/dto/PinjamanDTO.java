package com.admin.koperasi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

public class PinjamanDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PinjamanApproval{
        private Long id;
        private String idNasabah;
        private Long nominalTransaksi;
        private Date tanggal;
        private Time waktu;
        private Integer bulanBayar;
        private String tujuanPinjam;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PinjamanTolak{
        private Long id;
        private String idNasabah;
        private Long nominalTransaksi;
        private Integer bulanBayar;
        private String tujuanPinjam;
        private String adminTolak;
        private Date tanggalTolak;
        private String alasanTolak;
        private Long idTransaksi;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PinjamanTerima{
        private Long id;
        private String idNasabah;
        private Long totalPinjaman;
        private Long sisaPinjaman;
        private Integer bulanBayar;
        private Integer sisaBulanBayar;
        private Long idApproval;
        private Date tanggalApprove;
        private String adminApprove;
        private Long idTransaksi;
        private String tujuanPinjam;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PinjamanParameter{
        private Long id;
        private String idNasabah;
        private Long nominalPinjaman;
        private Integer bulanBayar;
        private Long noPinjaman;
        private String statusTransaksi;
        private String buktiPembayaran;
        private Date tanggalTransaksi;
        private String adminAction;
        private String alasanTolak;
        private String deskripsi;
        private Long idApproval;
        private String tujuanPinjam;
    }

}
