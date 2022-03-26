package com.admin.koperasi.service.dao;

import com.admin.koperasi.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    public TransaksiBatal saveTransaksiBatal(TransaksiBatal transaksiBatal) throws DataAccessException{
        String baseQuery= "insert into \"TN_TRANSAKSI_BATAL\"(\"ID_TRANSAKSI\", \"DESKRIPSI\")\n" +
                "values(:idTransaksi, :deskripsi)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idTransaksi", transaksiBatal.getIdTransaksi());
        parameterSource.addValue("idNasabah", transaksiBatal.getDeskripsi());

        namedParameterJdbcTemplate.update(baseQuery, parameterSource);

        return transaksiBatal;
    }

    public SimpananSukaRela saveSimpananSukaRela(SimpananSukaRela simpananSukaRela) throws DataAccessException{
        String baseQuery= "insert into \"TN_SIMP_SUKARELA\"(\"ID_TRANSAKSI\", \"ID_NASABAH\", \"NOMINAL_TRANSAKSI\")\n" +
                "values(:idTransaksi, :idNasabah, :nominalTransaksi)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idTransaksi", simpananSukaRela.getIdTransaksi());
        parameterSource.addValue("idNasabah", simpananSukaRela.getIdNasabah());
        parameterSource.addValue("nominalTransaksi", simpananSukaRela.getNominalTransaksi());

        namedParameterJdbcTemplate.update(baseQuery, parameterSource);

        return simpananSukaRela;
    }

    public SimpananWajib saveSimpananWajib(SimpananWajib simpananWajib) throws DataAccessException{
        String baseQuery= "insert into \"TN_SIMP_WAJIB\"(\"ID_TRANSAKSI\", \"ID_NASABAH\", \"NOMINAL_TRANSAKSI\")\n" +
                "values(:idTransaksi, :idNasabah, :nominalTransaksi)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idTransaksi", simpananWajib.getIdTransaksi());
        parameterSource.addValue("idNasabah", simpananWajib.getIdNasabah());
        parameterSource.addValue("nominalTransaksi", simpananWajib.getNominalTransaksi());

        namedParameterJdbcTemplate.update(baseQuery, parameterSource);

        return simpananWajib;
    }

    public Optional<Transaksi> findIdTransaksiLast() throws EmptyResultDataAccessException {
        String baseQuery = "select \"ID\" as idtransaksi\n" +
                "from \"TN_TRANSAKSI\" order by \"ID\" desc limit 1";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        Transaksi data =  namedParameterJdbcTemplate.queryForObject(baseQuery, parameterSource, new RowMapper<Transaksi>() {
            @Override
            public Transaksi mapRow(ResultSet resultSet, int i) throws SQLException {
                Transaksi data = new Transaksi();
                data.setId(resultSet.getInt("idtransaksi"));
                return data;
            }
        });

        return Optional.of(data);
    }

    public void deleteApproval(Integer id){
        String baseQuery = "DELETE FROM \"TN_TRANSAKSI_APPROVAL\" WHERE \"ID\" = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        namedParameterJdbcTemplate.update(baseQuery, parameterSource);
    }


}
