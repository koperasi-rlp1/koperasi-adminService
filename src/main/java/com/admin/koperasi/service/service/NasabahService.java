package com.admin.koperasi.service.service;

import com.admin.koperasi.service.dao.NasabahDAO;
import com.admin.koperasi.service.dto.NasabahDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class NasabahService {

    @Autowired
    private NasabahDAO dao;

    public String konfimasiPembayaran(NasabahDTO.KonfirmasiPendaftaran value) throws DataAccessException {
        value.setIdStatusKeanggotaan(4);
        dao.update(value);
        return "Berhasil Konfirmasi";
    }
}
