package com.admin.koperasi.service.dao;

import com.admin.koperasi.service.model.Nasabah;
import com.admin.koperasi.service.model.TransaksiApproval;
import com.admin.koperasi.service.model.datatables.DataTableRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TransaksiApprovalDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Long datatablesApprovalCount(DataTableRequest<TransaksiApproval> request){
        String baseQuery = "select count(*) as row_count " +
                "from \"TN_TRANSAKSI_APPROVAL\" tta join \"NASABAH\" n\n" +
                "on tta.\"ID_NASABAH\" = n.\"ID_BACKUP\" \n" +
                "where 1 = 1 ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder(baseQuery);

//        query.append(" and \"ID_NASABAH\" = :idNasabah ");
//        parameterSource.addValue("idNasabah", request.getExtraParam().getIdNasabah());

        query.append(" and \"JENIS_TRANSAKSI\" = :jenisTransaksi ");
        parameterSource.addValue("jenisTransaksi", request.getExtraParam().getJenisTransaksi());

        query.append(" order by :sortCol asc");
        parameterSource.addValue("sortCol", request.getSortCol()+1);
//        parameterSource.addValue("sortDir", request.getSortDir());

        query.append(" limit :limit offset :offset");
        parameterSource.addValue("limit", request.getLength());
        parameterSource.addValue("offset", request.getStart());

        return this.namedParameterJdbcTemplate.queryForObject(
                query.toString(),parameterSource,(resultSet, i) -> resultSet.getLong("row_count")
        );
    }

    public List<TransaksiApproval> datatablesApproval(DataTableRequest<TransaksiApproval> request){
        String baseQuery = "select row_number() over (order by tta.\"ID\") as no,\n" +
                "tta.\"ID\" as idApproval,\n" +
                "tta.\"ID_NASABAH\" as idNasabah,\n" +
                "tta.\"NOMINAL_TRANSAKSI\" as nominalTransaksi,\n" +
                "to_char(tta.\"TANGGAL\", 'dd/mm/yyyy') as tanggal,\n" +
                "tta.\"DESKRIPSI\" as deskripsi,\n" +
                "tta.\"BUKTI_TRANSAKSI\" as buktiPembayaran,\n" +
                "tta.\"JENIS_TRANSAKSI\" as jenisTransaksi,\n" +
                "n.\"NIP\" as nip,\n" +
                "n.\"NAMA_NASABAH\" as namaNasabah\n" +
                "from \"TN_TRANSAKSI_APPROVAL\" tta join \"NASABAH\" n\n" +
                "on tta.\"ID_NASABAH\" = n.\"ID_BACKUP\" \n" +
                "where 1 = 1 ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder(baseQuery);

//        query.append("and \"ID_NASABAH\" = :idNasabah ");
//        parameterSource.addValue("idNasabah", request.getExtraParam().getIdNasabah());


        query.append(" and \"JENIS_TRANSAKSI\" = :jenisTransaksi ");
        parameterSource.addValue("jenisTransaksi", request.getExtraParam().getJenisTransaksi());

        query.append(" order by :sortCol asc");
        parameterSource.addValue("sortCol", request.getSortCol()+1);
//        parameterSource.addValue("sortDir", request.getSortDir());

        query.append(" limit :limit offset :offset");
        parameterSource.addValue("limit", request.getLength());
        parameterSource.addValue("offset", request.getStart());

        return namedParameterJdbcTemplate.query(query.toString(), parameterSource, new BeanPropertyRowMapper<>(TransaksiApproval.class));
    }


    public Optional<TransaksiApproval> getDataTransaksiApproval(Integer idApproval) throws EmptyResultDataAccessException {
        String baseQuery = "select tta.\"ID\" as idApproval,\n" +
                "tta.\"NOMINAL_TRANSAKSI\" as nominalTransaksi,\n" +
                "to_char(tta.\"TANGGAL\", 'dd/mm/yyyy') as tanggal,\n" +
                "tta.\"BUKTI_TRANSAKSI\" as buktiPembayaran,\n" +
                "tta.\"JENIS_TRANSAKSI\" as jenisTransaksi,\n" +
                "tta.\"DESKRIPSI\" as deskripsi,\n" +
                "tta.\"ID_NASABAH\" as idNasabah,\n" +
                "n.\"NIP\" as nip,\n" +
                "n.\"NAMA_NASABAH\" as namaNasabah\n" +
                "from \"TN_TRANSAKSI_APPROVAL\" tta join \"NASABAH\" n\n" +
                "on tta.\"ID_NASABAH\" = n.\"ID_BACKUP\" \n" +
                "where tta.\"ID\" = :idApproval ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idApproval", idApproval);

        TransaksiApproval data =  namedParameterJdbcTemplate.queryForObject(baseQuery, parameterSource, new RowMapper<TransaksiApproval>() {
            @Override
            public TransaksiApproval mapRow(ResultSet resultSet, int i) throws SQLException {
                TransaksiApproval data = new TransaksiApproval();
                data.setNip(resultSet.getInt("nip"));
                data.setNamaNasabah(resultSet.getString("namaNasabah"));
                data.setIdApproval(resultSet.getInt("idApproval"));
                data.setNominalTransaksi(resultSet.getLong("nominalTransaksi"));
                data.setTanggal(resultSet.getString("tanggal"));
                data.setJenisTransaksi(resultSet.getString("jenisTransaksi"));
                data.setBuktiPembayaran(resultSet.getString("buktiPembayaran"));
                data.setDeskripsi(resultSet.getString("deskripsi"));
                data.setIdNasabah(resultSet.getString("idNasabah"));
                return data;
            }
        });

        return Optional.of(data);
    }
}
