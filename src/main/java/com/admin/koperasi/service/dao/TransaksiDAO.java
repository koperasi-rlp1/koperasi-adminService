package com.admin.koperasi.service.dao;

import com.admin.koperasi.service.model.Transaksi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransaksiDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Transaksi saveTransaksi(Transaksi transaksi) throws DataAccessException{
        String baseQuery= "insert into \"TN_TRANSAKSI\"(\"ID_APPROVAL\", \"ID_NASABAH\", \"NOMINAL_TRANSAKSI\", \"JENIS_TRANSAKSI\", \"BUKTI_PEMBAYARAN\", \"DESKRIPSI\",\"STATUS_APPROVAL\")\n" +
                "values(:idApproval, :idNasabah, :nominalTransaksi, :jenisTransaksi, :buktiPembayaran, :deskripsi, :statusApproval)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idApproval", transaksi.getIdApproval());
        parameterSource.addValue("idNasabah", transaksi.getIdNasabah());
        parameterSource.addValue("nominalTransaksi", transaksi.getNominalTransaksi());
        parameterSource.addValue("jenisTransaksi", transaksi.getJenisTransaksi());
        parameterSource.addValue("buktiPembayaran", transaksi.getBuktiPembayaran());
        parameterSource.addValue("deskripsi", transaksi.getDeskripsi());
        parameterSource.addValue("statusApproval", transaksi.getStatusApproval());

        namedParameterJdbcTemplate.update(baseQuery, parameterSource);

        return transaksi;
    }

}
