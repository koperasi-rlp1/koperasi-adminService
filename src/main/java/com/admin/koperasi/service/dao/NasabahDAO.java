package com.admin.koperasi.service.dao;

import com.admin.koperasi.service.dto.NasabahDTO;
import com.admin.koperasi.service.model.Nasabah;
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

@Repository
public class NasabahDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Nasabah> findNasabahByStatusKeanggotaan(Integer statusKeanggotaan) throws EmptyResultDataAccessException {
        String baseQuery = "SELECT * FROM NASABAH WHERE STATUS_KEANGGOTAAN = :statusKeanggotaan";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("statusKeanggotaan", statusKeanggotaan);

        return namedParameterJdbcTemplate.query(baseQuery, parameterSource, new RowMapper<Nasabah>() {
            @Override
            public Nasabah mapRow(ResultSet resultSet, int i) throws SQLException {
                Nasabah data = new Nasabah();
                data.setNip(resultSet.getInt("NIP"));
                data.setNamaNasabah(resultSet.getString("NAMA_NASABAH"));
                data.setNoHp(resultSet.getString("NO_HP"));
                data.setEmail(resultSet.getString("EMAIL"));
                data.setJabatan(resultSet.getString("JABATAN"));
                data.setUnitOperasional(resultSet.getString("UNIT_OPERASIONAL"));
                data.setFileBuktiPembayaran(resultSet.getString("BUKTI_PEMBAYARAN"));
                return data;
            }
        });
    }

    public void update(NasabahDTO.KonfirmasiPendaftaran value) throws DataAccessException {
        String baseQuery = "UPDATE NASABAH SET ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        StringBuilder buildInQuery;

        buildInQuery = new StringBuilder(baseQuery);
        buildInQuery.append(" NIP = :nip");
        parameterSource.addValue("nip", value.getNip());

        if(value.getNamaNasabah() != null){
            buildInQuery.append(", NAMA_NASABAH = :namaNasabah");
            parameterSource.addValue("namaNasabah", value.getNamaNasabah());
        }

        if(value.getEmail() != null){
            buildInQuery.append(", EMAIL = :email");
            parameterSource.addValue("email", value.getEmail());
        }

        if(value.getNoHp() != null){
            buildInQuery.append(", NO_HP = :noHp");
            parameterSource.addValue("noHp", value.getNoHp());
        }

        if(value.getJabatan() != null){
            buildInQuery.append(", JABATAN = :jabatan");
            parameterSource.addValue("jabatan", value.getJabatan());
        }

        if(value.getUnitOperasional() != null){
            buildInQuery.append(", UNIT_OPERASIONAL = :unitOperasional");
            parameterSource.addValue("unitOperasional", value.getUnitOperasional());
        }

        if(value.getUsername() != null){
            buildInQuery.append(", USERNAME = :username");
            parameterSource.addValue("username", value.getUsername());
        }

        if(value.getPassword() != null){
            buildInQuery.append(", PASSWORD = :password");
            parameterSource.addValue("password", value.getPassword());
        }

        if(value.getIdBackup() != null){
            buildInQuery.append(", ID_BACKUP = :idBakcup");
            parameterSource.addValue("idBackup", value.getIdBackup());
        }

        if(value.getIdStatusKeanggotaan() != null){
            buildInQuery.append(", ID_STATUS_KEANGGOTAAN = :idKeanggotaan");
            parameterSource.addValue("idKeanggotaan", value.getIdStatusKeanggotaan());
        }

        if(value.getFileBuktiPembayaran() != null){
            buildInQuery.append(", BUKTI_PEMBAYARAN = :buktiPembayaran");
            parameterSource.addValue("buktiPembayaran", value.getFileBuktiPembayaran());
        }

        if(value.getCreatedDate() != null){
            buildInQuery.append(", CREATED_DATE = :createdDate");
            parameterSource.addValue("createdDate", value.getCreatedDate());
        }

        buildInQuery.append(" WHERE NIP = :nipInduk");
        parameterSource.addValue("nipInduk", value.getNip());

        namedParameterJdbcTemplate.update(buildInQuery.toString(),parameterSource);
    }
}
