package com.admin.koperasi.service.controller;

import com.admin.koperasi.service.service.NasabahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/admin/nasabah")
public class NasabahController {

    @Autowired
    private NasabahService service;
}
