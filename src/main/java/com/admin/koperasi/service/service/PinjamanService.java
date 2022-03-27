package com.admin.koperasi.service.service;

import com.admin.koperasi.service.dao.PinjamanDAO;
import com.admin.koperasi.service.dto.PinjamanDTO;
import com.admin.koperasi.service.model.Pinjaman;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class PinjamanService {

    @Autowired
    private PinjamanDAO dao;

    @Value("${file.path.pinjaman}")
    private String basePath;

    public Pinjaman transactional(PinjamanDTO.PinjamanParameter value) throws SQLException{
        Pinjaman pinjaman;
        if (Objects.equal(value.getStatusTransaksi(), "aprrove")){
            dao.savePinjamTransaksi(value);
            pinjaman = dao.findLastTransaksi();
            PinjamanDTO.PinjamanTerima pinjamanTerima = new PinjamanDTO.PinjamanTerima();
            pinjamanTerima.setIdNasabah(value.getIdNasabah());
            pinjamanTerima.setTotalPinjaman(value.getNominalPinjaman());
            pinjamanTerima.setSisaPinjaman(value.getNominalPinjaman());
            pinjamanTerima.setBulanBayar(value.getBulanBayar());
            pinjamanTerima.setSisaBulanBayar(value.getBulanBayar());
            pinjamanTerima.setIdApproval(value.getIdApproval());
            pinjamanTerima.setAdminApprove(value.getAdminAction());
            pinjamanTerima.setTujuanPinjam(value.getTujuanPinjam());
            pinjamanTerima.setIdTransaksi(pinjaman.getId());
            dao.saveTerima(pinjamanTerima);
            dao.deleteApproval(value.getIdApproval());
        } else if(Objects.equal(value.getStatusTransaksi(), "tolak")){
            dao.savePinjamTransaksi(value);
            pinjaman = dao.findLastTransaksi();
            PinjamanDTO.PinjamanTolak pinjamanTolak = new PinjamanDTO.PinjamanTolak();
            pinjamanTolak.setIdNasabah(value.getIdNasabah());
            pinjamanTolak.setTujuanPinjam(value.getTujuanPinjam());
            pinjamanTolak.setBulanBayar(value.getBulanBayar());
            pinjamanTolak.setAdminTolak(value.getAdminAction());
            pinjamanTolak.setAlasanTolak(value.getAlasanTolak());
            pinjamanTolak.setNominalTransaksi(value.getNominalPinjaman());
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
}
