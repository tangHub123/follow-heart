package com.zc.followheart.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

@Data
public class ShareData {

    @ExcelProperty("摘要")
    private String makeSum;

    @ExcelProperty("公司")
    private String company;

    @ExcelProperty("月份")
    private String month;

    @ExcelProperty("部门")
    private String department;

    @ExcelProperty("四级部门")
    private String fourthDepartment;

    @ExcelProperty("调整四级部门")
    private String adjustFourthDepartment;

    @ExcelProperty("部门编码")
    private String deptCode;

    @ExcelProperty("组织")
    private String organization;

    @ExcelProperty("科目")
    private String subject;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("科目编码（工资）")
    private String subjectCodeSalary;

    @ExcelProperty("会计科目（工资）")
    private String ledgerSubjectSalary;

    @ExcelProperty("费用项目（工资）")
    private String expenseItemSalary;

    @ExcelProperty("应发工资金额")
    @NumberFormat("#.##")
    private String amountWagesPayable;


    @ExcelProperty("科目编码（单位社保）")
    private String subjectCodeSocial;

    @ExcelProperty("会计科目（单位社保）")
    private String ledgerSubjectSocial;

    @ExcelProperty("费用项目（单位社保）")
    private String expenseItemSocial;

    @ExcelProperty("单位社保金额")
    @NumberFormat("#.##")
    private String amountSocial;

    @ExcelProperty("科目编码（单位公积金）")
    private String subjectCodeGjj;

    @ExcelProperty("会计科目（单位公积金）")
    private String ledgerSubjectGjj;

    @ExcelProperty("费用项目（单位公积金）")
    private String expenseItemGjj;

    @ExcelProperty("单位公积金金额")
    @NumberFormat("#.##")
    private String amountGjj;

    @ExcelIgnore
    private Long version;

}
