package com.admin.koperasi.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaksiBatal {

    private Integer id;
    private Integer idTransaksi;
    private Date tanggal;
    private Time waktu;
    private String deskripsi;
}
