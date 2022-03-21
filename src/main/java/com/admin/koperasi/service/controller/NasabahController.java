package com.admin.koperasi.service.controller;

import com.admin.koperasi.service.dto.NasabahDTO;
import com.admin.koperasi.service.model.Nasabah;
import com.admin.koperasi.service.service.NasabahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin/nasabah")
public class NasabahController {

    @Autowired
    private NasabahService service;

    @PutMapping(path = "/konfirmasiPembayaran")
    public ResponseEntity<?> konfirmasiPembayaran(@RequestBody NasabahDTO.KonfirmasiPendaftaran value){
        try{
            String response = service.konfimasiPembayaran(value);
            return ResponseEntity.ok(response);
        } catch (DataAccessException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/data-aprroval")
    public ResponseEntity<?> dataNasabahApproval(){
        try{
            List<Nasabah> data = service.findNasabahByStatusKeanggotaan(1);
            return ResponseEntity.ok(data);
        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/data-by/{idBackup}")
    public ResponseEntity<?> dataNasabahApproval(
            @PathVariable("idBackup") String idBackup
    ){
        try{
            Optional<Nasabah> data = service.findByIdBackup(idBackup);
            return ResponseEntity.ok(data);
        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
