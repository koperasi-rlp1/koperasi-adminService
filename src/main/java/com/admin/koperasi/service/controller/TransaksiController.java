package com.admin.koperasi.service.controller;

import com.admin.koperasi.service.dto.PinjamanDTO;
import com.admin.koperasi.service.model.DataKoperasi;
import com.admin.koperasi.service.model.DataNasabah;
import com.admin.koperasi.service.model.Transaksi;
import com.admin.koperasi.service.model.TransaksiApproval;
import com.admin.koperasi.service.model.datatables.DataTableRequest;
import com.admin.koperasi.service.model.datatables.DataTableResponse;
import com.admin.koperasi.service.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/transaksi")
public class TransaksiController {

    @Autowired
    private TransaksiService service;

    @PostMapping("/transactional/{statusApproval}")
    public ResponseEntity<?> transactional(
            @RequestBody Transaksi transaksi,
            @PathVariable("statusApproval") String statusApproval
    ){
        return ResponseEntity.ok().body(service.transactional(transaksi,statusApproval));
    }

    @PostMapping("/datatablesDataNasabah")
    public ResponseEntity<DataTableResponse<DataNasabah>> datatablesConfirm(
            @RequestBody DataTableRequest<DataNasabah> request
    ){
        return ResponseEntity.ok().body(service.datatablesDataNasabah(request));
    }

    @GetMapping("/data-koperasi")
    public ResponseEntity<?> findDataSaldoNasabah(){
        try{
            DataKoperasi data = service.findDataKoperasi();
            return ResponseEntity.ok().body(data);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
