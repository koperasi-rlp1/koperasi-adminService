package com.admin.koperasi.service.model.datatables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableRequest<T> {

    private Integer draw, start, length, sortCol;
    private T extraParam;
    private String sortDir;
}
