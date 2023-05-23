package com.zc.followheart.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

@Data
public class UploadName {

    @ExcelProperty("名称")
    private String string;

    @ExcelProperty("日期")
    @DateTimeFormat("yyyy年MM月dd日")
    private String date;

    @ExcelProperty("金额")
    @NumberFormat("#.#")
    private String doubleData;
}
