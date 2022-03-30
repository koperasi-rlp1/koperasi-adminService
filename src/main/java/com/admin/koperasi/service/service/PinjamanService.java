package com.admin.koperasi.service.service;

import com.admin.koperasi.service.dao.NasabahDAO;
import com.admin.koperasi.service.dao.PinjamanDAO;
import com.admin.koperasi.service.dto.PinjamanDTO;
import com.admin.koperasi.service.model.Nasabah;
import com.admin.koperasi.service.model.Pinjaman;
import com.admin.koperasi.service.model.TransaksiApproval;
import com.admin.koperasi.service.model.datatables.DataTableRequest;
import com.admin.koperasi.service.model.datatables.DataTableResponse;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class PinjamanService {

    @Autowired
    private PinjamanDAO dao;

    @Autowired
    private NasabahDAO nasabahDAO;

    @Value("${file.path.pinjaman}")
    private String basePath;

    public DataTableResponse<PinjamanDTO.PinjamanParameter> datatablesConfirm(DataTableRequest<PinjamanDTO.PinjamanParameter> request){
        DataTableResponse<PinjamanDTO.PinjamanParameter> data = new DataTableResponse<>();
        data.setData(dao.datatablesConfirm(request));
        System.out.println(data.getData());
        data.setRecordTotal(dao.datatablesConfirnCount(request));
        data.setRecordFiltered(dao.datatablesConfirnCount(request));
        data.setDraw(request.getDraw());
        return data;
    }

    public DataTableResponse<PinjamanDTO.PinjamanParameter> datatablesBayar(DataTableRequest<PinjamanDTO.PinjamanParameter> request){
        DataTableResponse<PinjamanDTO.PinjamanParameter> data = new DataTableResponse<>();
        data.setData(dao.datatablesBayar(request));
        System.out.println(data.getData());
        data.setRecordTotal(dao.datatablesBayarCount(request));
        data.setRecordFiltered(dao.datatablesBayarCount(request));
        data.setDraw(request.getDraw());
        return data;
    }

    public DataTableResponse<PinjamanDTO.PinjamanApproval> datatablesApproval(DataTableRequest<PinjamanDTO.PinjamanApproval> request){
        DataTableResponse<PinjamanDTO.PinjamanApproval> data = new DataTableResponse<>();
        data.setData(dao.datatablesApproval(request));
        System.out.println(data.getData());
        data.setRecordTotal(dao.datatablesApprovalCount(request));
        data.setRecordFiltered(dao.datatablesApprovalCount(request));
        data.setDraw(request.getDraw());
        return data;
    }

    @Transactional
    public Pinjaman bayarPinjaman(Long id) throws SQLException {
        Pinjaman pinjaman = new Pinjaman();
        Optional<PinjamanDTO.PinjamanTerima> pinjamanTerima = dao.getPinjamanByid(id);
        PinjamanDTO.PinjamanParameter parameter = new PinjamanDTO.PinjamanParameter();
        parameter.setIdNasabah(pinjamanTerima.get().getIdNasabah());
        parameter.setNoPinjaman(pinjamanTerima.get().getId());
        parameter.setStatusTransaksi("bayar");
        Optional<Nasabah> nasabah = nasabahDAO.findNasabahByIdBackup(pinjamanTerima.get().getIdNasabah());
        String guru = "GURU";
        String pns = "GURU (PNS)";
        String tu = "STAF TU";
        LocalDate localDate = LocalDate.now();
        Long potongan;
        Long sisaPinjaman;
        if(Objects.equal(nasabah.get().getJabatan(), guru)){
            if (localDate.getDayOfMonth() == 17){
                dao.savePinjamTransaksi(parameter);
                potongan = pinjamanTerima.get().getTotalPinjaman()/pinjamanTerima.get().getBulanBayar();
                sisaPinjaman = pinjamanTerima.get().getTotalPinjaman() - potongan;
                pinjamanTerima.get().setSisaPinjaman(sisaPinjaman);
                pinjamanTerima.get().setSisaBulanBayar(pinjamanTerima.get().getBulanBayar()-1);
                dao.potonganBayar(pinjamanTerima.get());
                pinjaman.setDeskripsi("berhasil");
            }
        } else if(Objects.equal(nasabah.get().getJabatan(), pns)){
            if (localDate.getDayOfMonth() == 1){
                dao.savePinjamTransaksi(parameter);
                potongan = pinjamanTerima.get().getTotalPinjaman()/pinjamanTerima.get().getBulanBayar();
                sisaPinjaman = pinjamanTerima.get().getTotalPinjaman() - potongan;
                pinjamanTerima.get().setSisaPinjaman(sisaPinjaman);
                pinjamanTerima.get().setSisaBulanBayar(pinjamanTerima.get().getBulanBayar()-1);
                dao.potonganBayar(pinjamanTerima.get());
                pinjaman.setDeskripsi("berhasil");
            }
        } else if(Objects.equal(nasabah.get().getJabatan(), tu)){
            if (localDate.getDayOfMonth() == 30){
                dao.savePinjamTransaksi(parameter);
                potongan = pinjamanTerima.get().getTotalPinjaman()/pinjamanTerima.get().getBulanBayar();
                sisaPinjaman = pinjamanTerima.get().getTotalPinjaman() - potongan;
                pinjamanTerima.get().setSisaPinjaman(sisaPinjaman);
                pinjamanTerima.get().setSisaBulanBayar(pinjamanTerima.get().getBulanBayar()-1);
                dao.potonganBayar(pinjamanTerima.get());
                pinjaman.setDeskripsi("berhasil");
            }
        } else {
            potongan = pinjamanTerima.get().getTotalPinjaman()/pinjamanTerima.get().getBulanBayar();
            sisaPinjaman = pinjamanTerima.get().getTotalPinjaman() - potongan;
            parameter.setNominalTransaksi(potongan);
            dao.savePinjamTransaksi(parameter);
            pinjamanTerima.get().setSisaPinjaman(sisaPinjaman);
            pinjamanTerima.get().setSisaBulanBayar(pinjamanTerima.get().getBulanBayar()-1);
            dao.potonganBayar(pinjamanTerima.get());
            pinjaman.setDeskripsi("berhasil");
        }
        return pinjaman;
    }

    @Transactional
    public Pinjaman transactional(PinjamanDTO.PinjamanParameter value) throws SQLException{
        Pinjaman pinjaman;
        dao.savePinjamTransaksi(value);
        System.out.println(value.getStatusTransaksi());
        String approve = "approve";
        String tolak = "tolak";
        if (Objects.equal(value.getStatusTransaksi(), approve)){
            pinjaman = dao.findLastTransaksi();
            PinjamanDTO.PinjamanTerima pinjamanTerima = new PinjamanDTO.PinjamanTerima();
            pinjamanTerima.setIdNasabah(value.getIdNasabah());
            pinjamanTerima.setTotalPinjaman(value.getNominalTransaksi());
            pinjamanTerima.setSisaPinjaman(value.getNominalTransaksi());
            pinjamanTerima.setBulanBayar(value.getBulanBayar());
            pinjamanTerima.setSisaBulanBayar(value.getBulanBayar());
            pinjamanTerima.setIdApproval(value.getIdApproval());
            pinjamanTerima.setAdminApprove(value.getAdminAction());
            pinjamanTerima.setTujuanPinjam(value.getTujuanPinjam());
            pinjamanTerima.setIdTransaksi(pinjaman.getId());
            dao.saveTerima(pinjamanTerima);
            dao.deleteApproval(value.getIdApproval());
        } else if(Objects.equal(value.getStatusTransaksi(), tolak)){
            pinjaman = dao.findLastTransaksi();
            PinjamanDTO.PinjamanTolak pinjamanTolak = new PinjamanDTO.PinjamanTolak();
            pinjamanTolak.setIdNasabah(value.getIdNasabah());
            pinjamanTolak.setTujuanPinjam(value.getTujuanPinjam());
            pinjamanTolak.setBulanBayar(value.getBulanBayar());
            pinjamanTolak.setAdminTolak(value.getAdminAction());
            pinjamanTolak.setAlasanTolak(value.getAlasanTolak());
            pinjamanTolak.setNominalTransaksi(value.getNominalTransaksi());
            pinjamanTolak.setIdTransaksi(pinjaman.getId());
            dao.saveTolak(pinjamanTolak);
            dao.deleteApproval(value.getIdApproval());
        }
        pinjaman = dao.findLastTransaksi();
        return pinjaman;
    }

    public void konfirmasiPinjaman(Pinjaman value) throws SQLException{

        dao.updatePinjamanTransaksi(value);
    }

    public String uploadFile(MultipartFile file){
        try{
            Path root = Paths.get(basePath);
            String[] fileFrags = file.getOriginalFilename().split("\\.");
            String extension = fileFrags[fileFrags.length - 1];
            String uuid = UUID.randomUUID().toString() + "." + extension;
            Files.copy(file.getInputStream(), root.resolve(uuid));
            return uuid;
        } catch (IOException e){
            throw new RuntimeException("could not store the file. error : " + e.getMessage());
        }
    }

    public Resource load(String fileName){
        try{
            Path root = Paths.get(basePath);
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return  resource;
            } else {
                throw  new RuntimeException("couldn't found the file");
            }
        } catch (MalformedURLException a){
            throw  new RuntimeException("Cannot show picture");
        }
    }

    public Optional<PinjamanDTO.PinjamanApproval> getTransaksiApproval(Long idApproval){
        return dao.getDataTransaksiApproval(idApproval);
    }


    public Optional<Pinjaman> getTransaksi(Long idApproval){
        return dao.getDataTransaksi(idApproval);
    }

}
