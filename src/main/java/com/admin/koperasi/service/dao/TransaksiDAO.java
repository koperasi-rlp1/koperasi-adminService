package com.admin.koperasi.service.dao;

import com.admin.koperasi.service.dto.PinjamanDTO;
import com.admin.koperasi.service.model.*;
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

    public Long datatablesConfirnDataNasabah(DataTableRequest<DataNasabah> request){
        String baseQuery = "select count(*) as row_count \n" +
                "from(\n" +
                "\tselect row_number() over (order by nip) as no, nip, namaNasabah, COALESCE(sum(jumlahPinjaman), 0) as jumlahPinjaman,\n" +
                "\tCOALESCE(sum(jumlahSimpanan), 0) as jumlahSimpanan, email, noHp \n" +
                "\tfrom (\n" +
                "\t\tselect \"NIP\" as nip,\n" +
                "\t\t\"NAMA_NASABAH\" as namaNasabah,\n" +
                "\t\t\"EMAIL\" as email,\n" +
                "\t\t\"NO_HP\" as noHp,\n" +
                "\t\t0 as jumlahSimpanan,\n" +
                "\t\t0 as jumlahPinjaman\n" +
                "\t\tfrom \"NASABAH\"\n" +
                "\t\tunion all\n" +
                "\t\tselect n.\"NIP\" as nip,\n" +
                "\t\tn.\"NAMA_NASABAH\" as namaNasabah,\n" +
                "\t\tn.\"EMAIL\" as email,\n" +
                "\t\tn.\"NO_HP\" as noHp,\n" +
                "\t\tsum(tt.\"NOMINAL_TRANSAKSI\")+100000 as jumlahSimpanan,\n" +
                "\t\t0 as jumlahPinjaman\n" +
                "\t\tfrom \"TN_TRANSAKSI\" tt join \"NASABAH\" n on tt.\"ID_NASABAH\" = n.\"ID_BACKUP\" \n" +
                "\t\tgroup by nip, namaNasabah, email, noHp\n" +
                "\t\tunion all\n" +
                "\t\tselect n.\"NIP\" as nip,\n" +
                "\t\tn.\"NAMA_NASABAH\" as namaNasabah,\n" +
                "\t\tn.\"EMAIL\" as email,\n" +
                "\t\tn.\"NO_HP\" as noHp,\n" +
                "\t\t0 as jumlahSimpanan,\n" +
                "\t\tsum(tp.\"SISA_PINJAMAN\") as jumlahPinjaman \n" +
                "\t\tfrom \"TN_PINJAMAN\" tp join \"NASABAH\" n on tp.\"ID_NASABAH\" = n.\"ID_BACKUP\"\n" +
                "\t\twhere tp.\"SISA_PINJAMAN\" > 0 \n" +
                "\t\tgroup by nip, namaNasabah, email, noHp\n" +
                "\t) as datas group by nip, namaNasabah,email, noHP\n" +
                ")as a ";

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

        return this.namedParameterJdbcTemplate.queryForObject(
                query.toString(),parameterSource,(resultSet, i) -> resultSet.getLong("row_count")
        );
    }

    public List<DataNasabah> datatablesDataNasabah(DataTableRequest<DataNasabah> request){
        String baseQuery = "select a.* \n" +
                "from(\n" +
                "\tselect row_number() over (order by nip) as no, nip, namaNasabah, COALESCE(sum(jumlahPinjaman), 0) as jumlahPinjaman,\n" +
                "\tCOALESCE(sum(jumlahSimpanan), 0) as jumlahSimpanan, email, noHp \n" +
                "\tfrom (\n" +
                "\t\tselect \"NIP\" as nip,\n" +
                "\t\t\"NAMA_NASABAH\" as namaNasabah,\n" +
                "\t\t\"EMAIL\" as email,\n" +
                "\t\t\"NO_HP\" as noHp,\n" +
                "\t\t0 as jumlahSimpanan,\n" +
                "\t\t0 as jumlahPinjaman\n" +
                "\t\tfrom \"NASABAH\"\n" +
                "\t\tunion all\n" +
                "\t\tselect n.\"NIP\" as nip,\n" +
                "\t\tn.\"NAMA_NASABAH\" as namaNasabah,\n" +
                "\t\tn.\"EMAIL\" as email,\n" +
                "\t\tn.\"NO_HP\" as noHp,\n" +
                "\t\tCOALESCE(sum(tt.\"NOMINAL_TRANSAKSI\"), 0)+100000 as jumlahSimpanan,\n" +
                "\t\t0 as jumlahPinjaman\n" +
                "\t\tfrom \"NASABAH\" n left join \"TN_TRANSAKSI\" tt on tt.\"ID_NASABAH\" = n.\"ID_BACKUP\" \n" +
                "\t\tgroup by nip, namaNasabah, email, noHp\n" +
                "\t\tunion all\n" +
                "\t\tselect n.\"NIP\" as nip,\n" +
                "\t\tn.\"NAMA_NASABAH\" as namaNasabah,\n" +
                "\t\tn.\"EMAIL\" as email,\n" +
                "\t\tn.\"NO_HP\" as noHp,\n" +
                "\t\t0 as jumlahSimpanan,\n" +
                "\t\tsum(tp.\"SISA_PINJAMAN\") as jumlahPinjaman \n" +
                "\t\tfrom \"TN_PINJAMAN\" tp join \"NASABAH\" n on tp.\"ID_NASABAH\" = n.\"ID_BACKUP\"\n" +
                "\t\twhere tp.\"SISA_PINJAMAN\" > 0 \n" +
                "\t\tgroup by nip, namaNasabah, email, noHp\n" +
                "\t) as datas group by nip, namaNasabah,email, noHP\n" +
                ")as a ";

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

        return namedParameterJdbcTemplate.query(query.toString(), parameterSource, new BeanPropertyRowMapper<>(DataNasabah.class));
    }

    public DataKoperasi findDataKoperasi() throws EmptyResultDataAccessException{
        String baseQuery = "select 'KOPERASI APP' as namaKoperasi, COALESCE(sum(jumlahSimpanan-tarikSimpanan), 0) as jumlahSimpanan,\n" +
                "COALESCE(sum(trSimpanan), 0) as trSimpanan, COALESCE(sum(jumlahPinjaman), 0) as jumlahPinjaman,\n" +
                "COALESCE(sum(trPinjaman), 0) as trPinjaman, sum(jumlahNasabah) as jumlahNasabah\n" +
                "from(\n" +
                "\tselect \n" +
                "\tsum(tt.\"NOMINAL_TRANSAKSI\") as jumlahSimpanan,\n" +
                "\t0 as tarikSimpanan,\n" +
                "\tcount(*) as trSimpanan,\n" +
                "\t0 as jumlahPinjaman,\n" +
                "\t0 as jumlahNasabah,\n" +
                "\t0 as trPinjaman\n" +
                "\tfrom \"TN_TRANSAKSI\" tt where tt.\"FLAG\" is null\n" +
                "\tunion all\n" +
                "\tselect \n" +
                "\tcount(*)*100000 as jumlahSimpanan,\n" +
                "\t0 as tarikSimpanan,\n" +
                "\t0 as trSimpanan,\n" +
                "\t0 as jumlahPinjaman,\n" +
                "\tcount(*) as jumlahNasabah,\n" +
                "\t0 as trPinjaman\n" +
                "\tfrom \"NASABAH\"\n" +
                "\tunion all \n" +
                "\tselect \n" +
                "\t0 as jumlahSimpanan,\n" +
                "\tCOALESCE(sum(tt.\"NOMINAL_TRANSAKSI\"), 0) as tarikSimpanan,\n" +
                "\t0 as trSimpanan,\n" +
                "\t0 as jumlahPinjaman,\n" +
                "\t0 as jumlahNasabah,\n" +
                "\t0 as trPinjaman\n" +
                "\tfrom \"TN_TRANSAKSI\" tt where tt.\"FLAG\" is not null\n" +
                "\tunion all\n" +
                "\tselect \n" +
                "\t0 as jumlahSimpanan,\n" +
                "\t0 as tarikSimpanan,\n" +
                "\t0 as trSimpanan,\n" +
                "\tCOALESCE(sum(tp.\"SISA_PINJAMAN\"), 0) as jumlahPinjaman,\n" +
                "\t0 as jumlahNasabah,\n" +
                "\tcount(*) as trPinjaman\n" +
                "\tfrom \"TN_PINJAMAN\" tp where tp.\"SISA_PINJAMAN\" > 0\n" +
                ")as data group by namaKoperasi";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.queryForObject(baseQuery, parameterSource, new BeanPropertyRowMapper<>(DataKoperasi.class));
    }

}
