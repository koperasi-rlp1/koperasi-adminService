package com.admin.koperasi.service.controller;

import com.admin.koperasi.service.model.Nasabah;
import com.admin.koperasi.service.model.Transaksi;
import com.admin.koperasi.service.model.TransaksiApproval;
import com.admin.koperasi.service.model.datatables.DataTableRequest;
import com.admin.koperasi.service.model.datatables.DataTableResponse;
import com.admin.koperasi.service.service.TransaksiApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/transaksi-approval")
public class TransaksiApprovalController {

    @Autowired
    private TransaksiApprovalService service;

    @PostMapping("/datatables")
    public ResponseEntity<DataTableResponse<TransaksiApproval>> datatables(
            @RequestBody DataTableRequest<TransaksiApproval> request
    ){
        return ResponseEntity.ok().body(service.datatablesApproval(request));
    }

    @GetMapping(path = "/data-by/{idApproval}")
    public ResponseEntity<?> dataNasabahApproval(
            @PathVariable("idApproval") Integer idApproval
    ){
        try{
            Optional<TransaksiApproval> data = service.getTransaksiApproval(idApproval);
            return ResponseEntity.ok(data);
        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

