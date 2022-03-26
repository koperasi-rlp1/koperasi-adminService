package com.admin.koperasi.service.service;

import com.admin.koperasi.service.dao.TransaksiApprovalDAO;
import com.admin.koperasi.service.model.TransaksiApproval;
import com.admin.koperasi.service.model.datatables.DataTableRequest;
import com.admin.koperasi.service.model.datatables.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransaksiApprovalService {

    @Autowired
    private TransaksiApprovalDAO dao;

    public DataTableResponse<TransaksiApproval> datatablesApproval(DataTableRequest<TransaksiApproval> request){
        DataTableResponse<TransaksiApproval> data = new DataTableResponse<>();
        data.setData(dao.datatablesApproval(request));
        System.out.println(data.getData());
        data.setRecordTotal(dao.datatablesApprovalCount(request));
        data.setRecordFiltered(dao.datatablesApprovalCount(request));
        data.setDraw(request.getDraw());
        return data;
    }

    public Optional<TransaksiApproval> getTransaksiApproval(Integer idApproval){
        return dao.getDataTransaksiApproval(idApproval);
    }
}
