package com.admin.koperasi.service.controller;

import com.admin.koperasi.service.dto.PinjamanDTO;
import com.admin.koperasi.service.model.Pinjaman;
import com.admin.koperasi.service.model.Transaksi;
import com.admin.koperasi.service.service.PinjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/admin/pinjaman")
public class PinjamanController {

    @Autowired
    private PinjamanService service;

    @PostMapping("/transactional")
    public ResponseEntity<?> transactional(
            @RequestBody PinjamanDTO.PinjamanParameter pinjamanParameter
    ){
        try{
            Pinjaman pinjaman = service.transactional(pinjamanParameter);
            return ResponseEntity.ok().body(pinjaman);
        } catch (SQLException throwables) {
            return new ResponseEntity<>(throwables.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/konfirmasi-pinjaman")
    public ResponseEntity<?> konfirmasiPinjaman(
            @RequestBody Pinjaman pinjaman
    ){
        try{
            Pinjaman pinjaman2 = new Pinjaman();
            service.konfirmasiPinjaman(pinjaman2);
            return ResponseEntity.ok().body(pinjaman2);
        } catch (SQLException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/filesupload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = null;
        Map<String, Object> pesan = new HashMap<>();
        try {
            String namaFile = service.uploadFile(file);
            pesan.put("file", namaFile);
            return ResponseEntity.ok().body(pesan);
        } catch (Exception exception) {
            pesan.put("pesan", "cannot input file");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }
}
