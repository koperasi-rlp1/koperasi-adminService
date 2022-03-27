package com.admin.koperasi.service.dao;

import com.admin.koperasi.service.dto.NasabahDTO;
import com.admin.koperasi.service.dto.PinjamanDTO;
import com.admin.koperasi.service.model.Pinjaman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class PinjamanDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int saveTerima(PinjamanDTO.PinjamanTerima value) throws SQLException {
        String baseQuery = "insert into \"TN_PINJAMAN\" (\"ID_NASABAH\", \"ID_APPROVAL\", \"ID_TRANSAKSI\", \"TOTAL_PINJAMAN\", \"SISA_PINJAMAN\", \n" +
                "\"BULAN_BAYAR\", \"SISA_BULAN_BAYAR\", \"TANGGAL_APPROVAL\",\"ADMIN_APPROVE\", \"TUJUAN_PINJAM\") values(:idNasabah, :idApproval, :idTransaksi,\n" +
                ":totalPinjaman, :sisaPinjaman, :bulanBayar, :sisaBulanBayar, :tanggalApproval, :adminApprove, :tujuanPinjam)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idNasabah", value.getIdNasabah());
        parameterSource.addValue("idApproval", value.getIdApproval());
        parameterSource.addValue("idTransaksi", value.getIdTransaksi());
        parameterSource.addValue("totalPinjaman", value.getTotalPinjaman());
        parameterSource.addValue("sisaPinjaman", value.getSisaPinjaman());
        parameterSource.addValue("bulanBayar", value.getBulanBayar());
        parameterSource.addValue("sisaBulanBayar", value.getSisaBulanBayar());
        parameterSource.addValue("tanggalApproval", value.getTanggalApprove());
        parameterSource.addValue("adminApprove", value.getAdminApprove());
        parameterSource.addValue("tujuanPinjam", value.getTujuanPinjam());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int saveTolak(PinjamanDTO.PinjamanTolak value) throws SQLException {
        String baseQuery = "insert into \"TN_PINJAMAN_TOLAK\" (\"ID_NASABAH\",\"NOMINAL_TRANSAKSI\", \"BULAN_BAYAR\", \"TUJUAN_PINJAMAN\", \"ADMIN_TOLAK\",\n" +
                "\"TANGGAL_TOLAK\", \"ALASAN_TOLAK\", \"ID_TRANSAKSI\") values(:idNasabah, :nominalPinjaman, :bulanBayar, :tujuanPinjan, \n" +
                ":adminTolak, :tanggalTolak, :alasanTolak, :idTransaksi)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idNasabah", value.getIdNasabah());
        parameterSource.addValue("nominalPinjaman", value.getNominalTransaksi());
        parameterSource.addValue("bulanBayar", value.getBulanBayar());
        parameterSource.addValue("tujuanPinjan", value.getTujuanPinjam());
        parameterSource.addValue("adminTolak", value.getAdminTolak());
        parameterSource.addValue("tanggalTolak", value.getTanggalTolak());
        parameterSource.addValue("alasanTolak", value.getAlasanTolak());
        parameterSource.addValue("idTransaksi", value.getIdTransaksi());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int savePinjamTransaksi(PinjamanDTO.PinjamanParameter value) throws SQLException{
        String baseQuery = "insert into \"TN_PINJAMAN_TRANSAKSI\" (\"ID_NASABAH\", \"NOMINAL_TRANSAKSI\", \"NO_PINJAMAN\", \"STATUS_TRANSAKSI\", \n" +
                "\"BUKTI_PEMBAYARAN\", \"TANGGAL_TRANSAKSI\", \"DESKRIPSI\") values(:idNasabah, :nominalTransaksi, :noPinjaman, \n" +
                ":statusTransaksi, :buktiPembayaran, :tanggalTransaksi, :deskripsi)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idNasabah", value.getIdNasabah());
        parameterSource.addValue("nominalTransaksi", value.getNominalPinjaman());
        parameterSource.addValue("noPinjaman", value.getNoPinjaman());
        parameterSource.addValue("statusTransaksi", value.getStatusTransaksi());
        parameterSource.addValue("buktiPembayaran", value.getBuktiPembayaran());
        parameterSource.addValue("tanggalTransaksi", value.getTanggalTransaksi());
        parameterSource.addValue("deskripsi", value.getAlasanTolak());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public void updatePinjamanTransaksi(Pinjaman value) throws DataAccessException {
        String baseQuery = "UPDATE \"TN_PINJAMAN_TRANSAKSI\" SET ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        StringBuilder buildInQuery;

        buildInQuery = new StringBuilder(baseQuery);

        buildInQuery.append(" \"ID_NASABAH\" = :idNasabah");
        parameterSource.addValue("idNasabah", value.getIdNasabah());

        if(value.getNominalPinjaman() != null){
            buildInQuery.append(", \"NOMINAL_TRANSAKSI\" = :nominalTransaksi");
            parameterSource.addValue("nominalTransaksi", value.getNominalPinjaman());
        }

        if(value.getNoPinjaman() != null){
            buildInQuery.append(", \"NO_PINJAMAN\" = :noPinjaman");
            parameterSource.addValue("noPinjaman", value.getNoPinjaman());
        }

        if(value.getStatusTransaksi() != null){
            buildInQuery.append(", \"STATUS_TRANSAKSI\" = :statusTransaksi");
            parameterSource.addValue("statusTransaksi", value.getStatusTransaksi());
        }

        if(value.getBuktiPembayaran() != null){
            buildInQuery.append(", \"BUKTI_PEMBAYARAN\" = :buktiPembayaran");
            parameterSource.addValue("buktiPembayaran", value.getBuktiPembayaran());
        }

        if(value.getTanggalTransaksi() != null){
            buildInQuery.append(", \"TANGGAL_TRANSAKSI\" = :tanggalTransaksi");
            parameterSource.addValue("tanggalTransaksi", value.getTanggalTransaksi());
        }

        if(value.getDeskripsi() != null){
            buildInQuery.append(", \"DESKRIPSI\" = :deskripsi");
            parameterSource.addValue("deskripsi", value.getDeskripsi());
        }

        buildInQuery.append(" WHERE \"ID\" = :id");
        parameterSource.addValue("id", value.getId());

        jdbcTemplate.update(buildInQuery.toString(),parameterSource);
    }

    public void potonganBayar(PinjamanDTO.PinjamanTerima value) throws DataAccessException {
        String baseQuery = "UPDATE \"TN_PINJAMAN\" SET ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        StringBuilder buildInQuery;

        buildInQuery = new StringBuilder(baseQuery);

        if(value.getSisaPinjaman() != null){
            buildInQuery.append(" \"SISA_PINJAMAN\" = :sisaPinjaman");
            parameterSource.addValue("sisaPinjaman", value.getSisaPinjaman());
        }

        if(value.getSisaBulanBayar() != null){
            buildInQuery.append(", \"SISA_BULAN_BAYAR\" = :sisaBulanBayar");
            parameterSource.addValue("sisaBulanBayar", value.getSisaBulanBayar());
        }

        buildInQuery.append(" WHERE \"ID\" = :id");
        parameterSource.addValue("id", value.getId());

        jdbcTemplate.update(buildInQuery.toString(),parameterSource);
    }

    public Pinjaman findLastTransaksi() throws EmptyResultDataAccessException {
        String baseQuery = "select \"ID\" as id from \"TN_PINJAMAN_TRANSAKSI\" order by \"ID\" desc limit 1";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return jdbcTemplate.queryForObject(baseQuery, parameterSource, new BeanPropertyRowMapper<>(Pinjaman.class));
    }

    public void deleteApproval(Long id){
        String baseQuery = "delete from \"TN_PINJAMAN_APPROVAL\" where \"ID\" = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        jdbcTemplate.update(baseQuery, parameterSource);
    }




}