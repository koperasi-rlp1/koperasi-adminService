package com.admin.koperasi.service.dao;

import com.admin.koperasi.service.dto.NasabahDTO;
import com.admin.koperasi.service.dto.PinjamanDTO;
import com.admin.koperasi.service.model.Pinjaman;
import com.admin.koperasi.service.model.TransaksiApproval;
import com.admin.koperasi.service.model.datatables.DataTableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PinjamanDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int saveTerima(PinjamanDTO.PinjamanTerima value) throws SQLException {
        String baseQuery = "insert into \"TN_PINJAMAN\" (\"ID_NASABAH\", \"ID_APPROVAL\", \"ID_TRANSAKSI\", \"TOTAL_PINJAMAN\", \"SISA_PINJAMAN\", \n" +
                "\"BULAN_BAYAR\", \"SISA_BULAN_BAYAR\",\"ADMIN_APPROVE\", \"TUJUAN_PINJAM\") values(:idNasabah, :idApproval, :idTransaksi,\n" +
                ":totalPinjaman, :sisaPinjaman, :bulanBayar, :sisaBulanBayar, :adminApprove, :tujuanPinjam)";

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
                "\"ALASAN_TOLAK\", \"ID_TRANSAKSI\") values(:idNasabah, :nominalPinjaman, :bulanBayar, :tujuanPinjan, \n" +
                ":adminTolak, :alasanTolak, :idTransaksi)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idNasabah", value.getIdNasabah());
        parameterSource.addValue("nominalPinjaman", value.getNominalTransaksi());
        parameterSource.addValue("bulanBayar", value.getBulanBayar());
        parameterSource.addValue("tujuanPinjan", value.getTujuanPinjam());
        parameterSource.addValue("adminTolak", value.getAdminTolak());
        parameterSource.addValue("alasanTolak", value.getAlasanTolak());
        parameterSource.addValue("idTransaksi", value.getIdTransaksi());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int savePinjamTransaksi(PinjamanDTO.PinjamanParameter value) throws SQLException{
        String baseQuery = "insert into \"TN_PINJAMAN_TRANSAKSI\" (\"ID_NASABAH\", \"NOMINAL_TRANSAKSI\", \"NO_PINJAMAN\", \"STATUS_TRANSAKSI\", \n" +
                "\"BUKTI_PEMBAYARAN\", \"DESKRIPSI\") values(:idNasabah, :nominalTransaksi, :noPinjaman, \n" +
                ":statusTransaksi, :buktiPembayaran, :deskripsi)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idNasabah", value.getIdNasabah());
        parameterSource.addValue("nominalTransaksi", value.getNominalTransaksi());
        parameterSource.addValue("noPinjaman", value.getNoPinjaman());
        parameterSource.addValue("statusTransaksi", value.getStatusTransaksi());
        parameterSource.addValue("buktiPembayaran", value.getBuktiPembayaran());
        parameterSource.addValue("deskripsi", value.getAlasanTolak());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public void updatePinjamanTransaksi(Pinjaman value) throws DataAccessException {
        String baseQuery = "UPDATE \"TN_PINJAMAN_TRANSAKSI\" SET ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        StringBuilder buildInQuery;

        buildInQuery = new StringBuilder(baseQuery);

        if(value.getIdNasabah() != null){
            buildInQuery.append(" \"ID_NASABAH\" = :idNasabah");
            parameterSource.addValue("idNasabah", value.getIdNasabah());
        }

        if(value.getNominalPinjaman() != null){
            buildInQuery.append(", \"NOMINAL_TRANSAKSI\" = :nominalTransaksi");
            parameterSource.addValue("nominalTransaksi", value.getNominalPinjaman());
        }

        if(value.getNoPinjaman() != null){
            buildInQuery.append(", \"NO_PINJAMAN\" = :noPinjaman");
            parameterSource.addValue("noPinjaman", value.getNoPinjaman());
        }

        if(value.getStatusTransaksi() != null){
            buildInQuery.append(" \"STATUS_TRANSAKSI\" = :statusTransaksi");
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


    public Long datatablesApprovalCount(DataTableRequest<PinjamanDTO.PinjamanApproval> request){
        String baseQuery = "select count(*) as row_count " +
                "from \"TN_TRANSAKSI_APPROVAL\" tta join \"NASABAH\" n\n" +
                "on tta.\"ID_NASABAH\" = n.\"ID_BACKUP\" \n" +
                "where 1 = 1 ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder(baseQuery);

//        query.append(" and \"ID_NASABAH\" = :idNasabah ");
//        parameterSource.addValue("idNasabah", request.getExtraParam().getIdNasabah());

//        query.append(" and \"JENIS_TRANSAKSI\" = :jenisTransaksi ");
//        parameterSource.addValue("jenisTransaksi", request.getExtraParam().getJenisTransaksi());

        query.append(" order by :sortCol asc");
        parameterSource.addValue("sortCol", request.getSortCol()+1);
//        parameterSource.addValue("sortDir", request.getSortDir());

        query.append(" limit :limit offset :offset");
        parameterSource.addValue("limit", request.getLength());
        parameterSource.addValue("offset", request.getStart());

        return this.jdbcTemplate.queryForObject(
                query.toString(),parameterSource,(resultSet, i) -> resultSet.getLong("row_count")
        );
    }

    public List<PinjamanDTO.PinjamanApproval> datatablesApproval(DataTableRequest<PinjamanDTO.PinjamanApproval> request){
        String baseQuery = "select row_number() over (order by tpa.\"ID\") as no,\n" +
                "tpa.\"ID\" as id,\n" +
                "tpa.\"ID_NASABAH\" as \"ID_NASABAH\",\n" +
                "tpa.\"NOMINAL_PINJAMAN\" as nominalTransaksi,\n" +
                "tpa.\"TANGGAL\" as tanggal,\n" +
                "tpa.\"WAKTU\" as waktu,\n" +
                "tpa.\"BULAN_BAYAR\" as bulanBayar,\n" +
                "tpa.\"TUJUAN_PEMINJAMAN\" as tujuanPinjam,\n" +
                "n.\"NIP\" as nip,\n" +
                "n.\"NAMA_NASABAH\" as namaNasabah\n" +
                "from \"TN_PINJAMAN_APPROVAL\" tpa join \"NASABAH\" n on tpa.\"ID_NASABAH\" = n.\"ID_BACKUP\" \n" +
                "where 1 = 1 ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder(baseQuery);

//        query.append("and \"ID_NASABAH\" = :idNasabah ");
//        parameterSource.addValue("idNasabah", request.getExtraParam().getIdNasabah());


//        query.append(" and \"JENIS_TRANSAKSI\" = :jenisTransaksi ");
//        parameterSource.addValue("jenisTransaksi", request.getExtraParam().getJenisTransaksi());

        query.append(" order by :sortCol asc");
        parameterSource.addValue("sortCol", request.getSortCol()+1);
//        parameterSource.addValue("sortDir", request.getSortDir());

        query.append(" limit :limit offset :offset");
        parameterSource.addValue("limit", request.getLength());
        parameterSource.addValue("offset", request.getStart());

        return jdbcTemplate.query(query.toString(), parameterSource, new BeanPropertyRowMapper<>(PinjamanDTO.PinjamanApproval.class));
    }


    public Long datatablesConfirnCount(DataTableRequest<PinjamanDTO.PinjamanParameter> request){
        String baseQuery = "select count(*) as row_count " +
                "from \"TN_PINJAMAN_TRANSAKSI\" tpt join \"TN_PINJAMAN\" tp on tpt.\"ID\" = tp.\"ID_TRANSAKSI\"\n" +
                "join \"NASABAH\" n on tpt.\"ID_NASABAH\" = n.\"ID_BACKUP\"\n" +
                "where 1 = 1 and tpt.\"STATUS_TRANSAKSI\" = 'approve' ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder(baseQuery);

//        query.append(" and \"ID_NASABAH\" = :idNasabah ");
//        parameterSource.addValue("idNasabah", request.getExtraParam().getIdNasabah());

//        query.append(" and \"JENIS_TRANSAKSI\" = :jenisTransaksi ");
//        parameterSource.addValue("jenisTransaksi", request.getExtraParam().getJenisTransaksi());

        query.append(" order by :sortCol asc");
        parameterSource.addValue("sortCol", request.getSortCol()+1);
//        parameterSource.addValue("sortDir", request.getSortDir());

        query.append(" limit :limit offset :offset");
        parameterSource.addValue("limit", request.getLength());
        parameterSource.addValue("offset", request.getStart());

        return this.jdbcTemplate.queryForObject(
                query.toString(),parameterSource,(resultSet, i) -> resultSet.getLong("row_count")
        );
    }

    public List<PinjamanDTO.PinjamanParameter> datatablesConfirm(DataTableRequest<PinjamanDTO.PinjamanParameter> request){
        String baseQuery = "select row_number() over (order by tpt.\"ID\") as no,\n" +
                "tpt.\"ID\" as id,\n" +
                "tp.\"TOTAL_PINJAMAN\" as nominalTransaksi,\n" +
                "tp.\"ID\" as noPinjaman,\n" +
                "tpt.\"ID_NASABAH\" as idNasabah,\n" +
                "tp.\"BULAN_BAYAR\" as bulanBayar,\n" +
                "tp.\"ADMIN_APPROVE\" as adminApprove,\n" +
                "tp.\"TANGGAL_APPROVAL\" as tanggalApprove,\n" +
                "tp.\"TUJUAN_PINJAM\" as tujuanPinjam,\n" +
                "n.\"NIP\" as nip,\n" +
                "n.\"NAMA_NASABAH\" as namaNasabah\n" +
                "from \"TN_PINJAMAN_TRANSAKSI\" tpt join \"TN_PINJAMAN\" tp on tpt.\"ID\" = tp.\"ID_TRANSAKSI\"\n" +
                "join \"NASABAH\" n on tpt.\"ID_NASABAH\" = n.\"ID_BACKUP\"\n" +
                "where 1 = 1 and tpt.\"STATUS_TRANSAKSI\" = 'approve' ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder(baseQuery);

//        query.append("and \"ID_NASABAH\" = :idNasabah ");
//        parameterSource.addValue("idNasabah", request.getExtraParam().getIdNasabah());


//        query.append(" and \"JENIS_TRANSAKSI\" = :jenisTransaksi ");
//        parameterSource.addValue("jenisTransaksi", request.getExtraParam().getJenisTransaksi());

        query.append(" order by :sortCol asc");
        parameterSource.addValue("sortCol", request.getSortCol()+1);
//        parameterSource.addValue("sortDir", request.getSortDir());

        query.append(" limit :limit offset :offset");
        parameterSource.addValue("limit", request.getLength());
        parameterSource.addValue("offset", request.getStart());

        return jdbcTemplate.query(query.toString(), parameterSource, new BeanPropertyRowMapper<>(PinjamanDTO.PinjamanParameter.class));
    }


    public Optional<PinjamanDTO.PinjamanApproval> getDataTransaksiApproval(Long idApproval) throws EmptyResultDataAccessException {
        String baseQuery = "select row_number() over (order by tpa.\"ID\") as no,\n" +
                "tpa.\"ID\" as id,\n" +
                "tpa.\"ID_NASABAH\" as \"ID_NASABAH\",\n" +
                "tpa.\"NOMINAL_PINJAMAN\" as nominalTransaksi,\n" +
                "tpa.\"TANGGAL\" as tanggal,\n" +
                "tpa.\"WAKTU\" as waktu,\n" +
                "tpa.\"BULAN_BAYAR\" as bulanBayar,\n" +
                "tpa.\"TUJUAN_PEMINJAMAN\" as tujuanPinjam,\n" +
                "n.\"NIP\" as nip,\n" +
                "n.\"NAMA_NASABAH\" as namaNasabah\n" +
                "from \"TN_PINJAMAN_APPROVAL\" tpa join \"NASABAH\" n on tpa.\"ID_NASABAH\" = n.\"ID_BACKUP\" \n" +
                "where tpa.\"ID\" = :id ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", idApproval);

        PinjamanDTO.PinjamanApproval data = jdbcTemplate.queryForObject(baseQuery, parameterSource, new BeanPropertyRowMapper<>(PinjamanDTO.PinjamanApproval.class));

        return Optional.of(data);
    }

    public Optional<Pinjaman> getDataTransaksi(Long idApproval) throws EmptyResultDataAccessException {
        String baseQuery = "select tpt.\"ID\" as id,\n" +
                "tpt.\"NOMINAL_TRANSAKSI\" as nominalPinjaman,\n" +
                "n.\"NAMA_NASABAH\" as namaNasabah\n" +
                "from \"TN_PINJAMAN_TRANSAKSI\" tpt join \"NASABAH\" n on tpt.\"ID_NASABAH\" = n.\"ID_BACKUP\" where tpt.\"ID\" = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", idApproval);

        Pinjaman data = jdbcTemplate.queryForObject(baseQuery, parameterSource, new BeanPropertyRowMapper<>(Pinjaman.class));

        return Optional.of(data);
    }

}
