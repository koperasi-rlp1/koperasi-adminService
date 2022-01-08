package com.admin.koperasi.service.controller;

import com.admin.koperasi.service.dto.NasabahDTO;
import com.admin.koperasi.service.service.NasabahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(path = "/admin/nasabah")
public class NasabahController {

    @Autowired
    private NasabahService service;

    @PutMapping(name = "/konfirmasiPembayaran")
    public ResponseEntity<?> konfirmasiPembayaran(@RequestBody NasabahDTO.KonfirmasiPendaftaran value){
        try{
            String response = service.konfimasiPembayaran(value);
            return ResponseEntity.ok(value);
        } catch (DataAccessException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
