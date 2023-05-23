package com.zc.followheart.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.page.PageMethod;
import com.zc.followheart.entity.*;
import com.zc.followheart.listener.ConverterDataListener;
import com.zc.followheart.mapper.ShareResultMapper;
import com.zc.followheart.service.ShareService;
import com.zc.followheart.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("share")
@Slf4j
public class ShareController {
    
    @Autowired
    ShareService shareService;

    @Autowired
    ShareResultMapper shareResultMapper;

    @PostMapping("upload")
    @ResponseBody
    public void readSyncWriteShareData(MultipartFile file, HttpServletResponse response) throws Exception {
        // 读取数据
        List<ShareData> list = EasyExcel.read(file.getInputStream()).head(ShareData.class).sheet().doReadSync();
        log.info("读取到数据size:{}", list.size());
        // 数据处理
        List<ShareResultData> resultDatas = shareService.delShareData(list);

        //获取模板
        String fileName = "D:\\twq\\测试\\分摊结果模板.xlsx";
        //写入文件导出
        ResponseUtil.setResponse(response,"分摊结果");
        EasyExcel.write(response.getOutputStream()).withTemplate(fileName).sheet("模板").doWrite(resultDatas);
    }


//    @PostMapping("upload")
//    @ResponseBody
//    public ResponseEntity<String> converterRead(MultipartFile file) throws IOException {
//        System.out.println(file.getOriginalFilename());
//        try {
//            EasyExcel.read(file.getInputStream(), ProRataShareData.class, new ConverterDataListener(shareService))
//                    .sheet().doRead();
//        }catch (Exception e){
//            return ResponseEntity.err(e.getMessage());
//        }
//        return ResponseEntity.success("ok");
//    }
//
//
//    @PostMapping("/leadOut")
//    @ResponseBody
//    public void leadOut(HttpServletResponse response) throws IOException {
//        log.info("leadOut...");
//        List<ProRataShareData> list = proRataShareMapper.selectAll(null);
//        ResponseUtil.setResponse(response,"分摊比例");
//        EasyExcel.write(response.getOutputStream(), ProRataShareData.class).sheet("分摊比例").doWrite(list);
//    }


    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<DataGrid<ShareResultData>> selectAll(@RequestParam(value = "page") Integer page,
                                                                @RequestParam(value = "limit") Integer limit

    )  {
        System.out.println("page:"+page+"limit:"+limit);
        PageMethod.startPage(page,limit);
        List<ShareResultData> list = shareResultMapper.selectAll();
        return ResponseEntity.success(DataGrid.getDataGrid(list));
    }


}
