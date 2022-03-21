package com.admin.koperasi.service.dao;

import com.admin.koperasi.service.model.SimpananSukaRela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SimpananSukaRelaDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SimpananSukaRela save(SimpananSukaRela value)throws DataAccessException{
        String baseQuery= "insert into \"TN_SIPM_SUKARELA\"(\"ID_TRANSAKSI\",\"ID_NASABAH\", \"NOMINAL_TRANSAKSI\")\n" +
                "values(:idTransaksi, :idNasabah, :nominalTransaksi)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idNasabah", value.getIdNasabah());
        parameterSource.addValue("idTransaksi", value.getIdTransaksi());
        parameterSource.addValue("nominalTransaksi", value.getNominalTransaksi());

        namedParameterJdbcTemplate.update(baseQuery, parameterSource);

        return value;
    }
}
