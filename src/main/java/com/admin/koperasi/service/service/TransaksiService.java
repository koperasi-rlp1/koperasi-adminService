package com.admin.koperasi.service.service;

import com.admin.koperasi.service.dao.TransaksiDAO;
import com.admin.koperasi.service.model.SimpananSukaRela;
import com.admin.koperasi.service.model.SimpananWajib;
import com.admin.koperasi.service.model.Transaksi;
import com.admin.koperasi.service.model.TransaksiBatal;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiDAO dao;

    public Transaksi transactional(Transaksi transaksi, String statusApproval){
        transaksi.setStatusApproval(statusApproval);
        dao.saveTransaksi(transaksi);
        Optional<Transaksi> transaksiOptional = dao.findIdTransaksiLast();
        if(Objects.equal(statusApproval,"1")){
            if(Objects.equal(transaksi.getJenisTransaksi(), "SIMPANAN WAJIB" )){
                SimpananWajib simpananWajib = new SimpananWajib();
                simpananWajib.setIdTransaksi(transaksiOptional.get().getId());
                simpananWajib.setIdNasabah(transaksi.getIdNasabah());
                simpananWajib.setNominalTransaksi(transaksi.getNominalTransaksi());
                dao.saveSimpananWajib(simpananWajib);
                dao.deleteApproval(transaksi.getIdApproval());
            }
            if (Objects.equal(transaksi.getJenisTransaksi(), "SIMPANAN SUKA RELA" )){
                SimpananSukaRela simpananSukaRela = new SimpananSukaRela();
                simpananSukaRela.setIdTransaksi(transaksiOptional.get().getId());
                simpananSukaRela.setIdNasabah(transaksi.getIdNasabah());
                simpananSukaRela.setNominalTransaksi(transaksi.getNominalTransaksi());
                dao.saveSimpananSukaRela(simpananSukaRela);
                dao.deleteApproval(transaksi.getIdApproval());
            }
        }
        if(Objects.equal(statusApproval,"2")){
            TransaksiBatal transaksiBatal = new TransaksiBatal();
            transaksiBatal.setIdTransaksi(transaksiOptional.get().getId());
            transaksiBatal.setDeskripsi(transaksi.getDeskripsi());
            dao.saveTransaksiBatal(transaksiBatal);
            dao.deleteApproval(transaksi.getIdApproval());
        }

        return transaksi;
    }
}
