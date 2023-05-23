package com.zc.followheart.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson2.JSON;
import com.zc.followheart.entity.UploadData;
import com.zc.followheart.entity.UploadName;
import com.zc.followheart.listener.ConverterDataListener;
import com.zc.followheart.listener.UploadDataListener;
import com.zc.followheart.service.UploadDataService;
import com.zc.followheart.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @Autowired
    UploadDataService uploadDataService;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadDataService)).sheet().doRead();
        return "success";
    }


    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
//    @PostMapping("converterRead")
//    @ResponseBody
//    public String converterRead(MultipartFile file) throws IOException {
//
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
//        EasyExcel.read(file.getInputStream(), UploadName.class, new ConverterDataListener())
//                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
//                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
//                // .registerConverter(new CustomStringStringConverter())
//                // 读取sheet
//                //.headRowNumber(2) 多行头
//                .sheet().doRead();
//        return "success";
//    }


    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
//    @PostMapping("readAndWrite")
//    @ResponseBody
//    public String readAndWrite(MultipartFile file) throws IOException {
//
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
//        EasyExcel.read(file.getInputStream(), UploadName.class, new ConverterDataListener())
//                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
//                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
//                // .registerConverter(new CustomStringStringConverter())
//                // 读取sheet
//                //.headRowNumber(2) 多行头
//                .sheet().doRead();
//
//        return "success";
//    }


    /**
     * 同步读取 大数据量不推荐
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("readSync")
    @ResponseBody
    public String readSync(MultipartFile file) throws IOException {
        List<UploadName> list = EasyExcel.read(file.getInputStream()).head(UploadName.class).sheet().doReadSync();
        log.info("读取到数据:{}", JSON.toJSONString(list));
        return "success";
    }


    /**
     * 同步读取 同时写入文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("readSyncAndWrite")
    @ResponseBody
    public String readSyncWrite(MultipartFile file, HttpServletResponse response) throws IOException {
        List<UploadName> list = EasyExcel.read(file.getInputStream()).head(UploadName.class).sheet().doReadSync();
        log.info("读取到数据:{}", JSON.toJSONString(list));

        //写入文件导出
        ResponseUtil.setResponse(response,"测试");
        EasyExcel.write(response.getOutputStream(), UploadName.class).sheet("模板").doWrite(list);
        return "success";
    }



}
