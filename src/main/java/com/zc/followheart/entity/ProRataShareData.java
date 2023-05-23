package com.zc.followheart.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

/**
 * 分摊比例数据
 */
@Data
public class ProRataShareData {

    @ExcelProperty("比例")
    @NumberFormat("#.##")
    private String scale;

    @ExcelProperty("项目")
    private String project;

    @ExcelProperty("总裁办")
    private String ceoOffice;

    @ExcelProperty("部门")
    private String department;

    @ExcelProperty("组织")
    private String organization;

    @ExcelProperty("单位")
    private String unit;

    @ExcelProperty("费用科目")
    private String expenseAccount;

    @ExcelProperty("姓名")
    private String name;

    @ExcelIgnore
    private Long version;

    @ExcelIgnore
    private Long id;

}
