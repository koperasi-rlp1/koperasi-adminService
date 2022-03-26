package com.admin.koperasi.service.controller;

import com.admin.koperasi.service.model.Transaksi;
import com.admin.koperasi.service.model.TransaksiApproval;
import com.admin.koperasi.service.model.datatables.DataTableRequest;
import com.admin.koperasi.service.model.datatables.DataTableResponse;
import com.admin.koperasi.service.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
