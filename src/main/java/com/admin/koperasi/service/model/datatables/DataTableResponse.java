package com.admin.koperasi.service.model.datatables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataTableResponse<T> {

    private List<T> data;
    private Integer draw;
    private Long recordFiltered, recordTotal;
}
