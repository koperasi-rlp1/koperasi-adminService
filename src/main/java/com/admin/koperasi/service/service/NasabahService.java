package com.admin.koperasi.service.service;

import com.admin.koperasi.service.dao.NasabahDAO;
import com.admin.koperasi.service.dto.NasabahDTO;
import com.admin.koperasi.service.model.Nasabah;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NasabahService {

    @Autowired
    private NasabahDAO dao;

    public String konfimasiPembayaran(NasabahDTO.KonfirmasiPendaftaran value) throws DataAccessException {
        value.setIdStatusKeanggotaan(4);
        dao.update(value);
        return "Berhasil Konfirmasi";
    }

    public List<Nasabah> findNasabahByStatusKeanggotaan(Integer statusKeanggotaan) throws EmptyResultDataAccessException {
        return dao.findNasabahByStatusKeanggotaan(statusKeanggotaan);
    }

    public Optional<Nasabah> findByIdBackup(String idBakcup) throws EmptyResultDataAccessException {
        return dao.findNasabahByIdBackup(idBakcup);
    }
}
