package com.zc.followheart.entity;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class DataGrid<T> {
    private int total;

    private List<T> rows;

    public static <T>DataGrid<T> getDataGrid(List<T> list){
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        DataGrid<T> objectDataGrid = new DataGrid<>();
        objectDataGrid.setTotal((int)pageInfo.getTotal());
        objectDataGrid.setRows(list);
        return objectDataGrid;
    }
}
