package com.zc.followheart.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.page.PageMethod;
import com.zc.followheart.entity.*;
import com.zc.followheart.listener.ConverterDataListener;
import com.zc.followheart.mapper.ProRataShareMapper;
import com.zc.followheart.service.ProRataShareService;
import com.zc.followheart.service.ShareService;
import com.zc.followheart.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pro/rata/share")
@Slf4j
public class ProRataShareController {

    @Autowired
    ProRataShareService shareService;

    @Autowired
    ProRataShareMapper proRataShareMapper;

    @PostMapping("upload")
    @ResponseBody
    public ResponseEntity<String> converterRead(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        try {
            EasyExcel.read(file.getInputStream(), ProRataShareData.class, new ConverterDataListener(shareService))
                    .sheet().doRead();
        }catch (Exception e){
            return ResponseEntity.err(e.getMessage());
        }
        return ResponseEntity.success("ok");
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<DataGrid<ProRataShareData>> selectAll(@RequestParam(value = "page") Integer page,
                                                            @RequestParam(value = "limit") Integer limit,
                                                            @RequestParam(value = "name",required=false) String name
                                                            )  {
        System.out.println("page:"+page+"limit:"+limit);
        PageMethod.startPage(page,limit);
        List<ProRataShareData> list = proRataShareMapper.selectAll(name);
        return ResponseEntity.success(DataGrid.getDataGrid(list));
    }


    @PostMapping("/leadOut")
    @ResponseBody
    public void leadOut(HttpServletResponse response) throws IOException {
        log.info("leadOut...");
        List<ProRataShareData> list = proRataShareMapper.selectAll(null);
        ResponseUtil.setResponse(response,"分摊比例");
        EasyExcel.write(response.getOutputStream(), ProRataShareData.class).sheet("分摊比例").doWrite(list);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> add(@RequestBody ProRataShareData proRataShareData)  {
        System.out.println(JSON.toJSONString(proRataShareData));
        Long version  = proRataShareMapper.selectMaxVersion();
        System.out.println("version : "+ version);
        proRataShareData.setVersion(version);
        List<ProRataShareData> list = new ArrayList<>();
        list.add(proRataShareData);
        System.out.println(JSON.toJSONString(list));
        try{
            proRataShareMapper.batchInsert(list);
        }catch (Exception e){
            log.error("err:",e);
            return ResponseEntity.err(e.getMessage());
        }
        return ResponseEntity.success("ok");
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> update(@RequestBody ProRataShareData proRataShareData)  {
        System.out.println(JSON.toJSONString(proRataShareData));
        try{
            proRataShareMapper.updateById(proRataShareData);
        }catch (Exception e){
            log.error("err:",e);
            return ResponseEntity.err(e.getMessage());
        }
        return ResponseEntity.success("ok");
    }


    @GetMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id)  {
        System.out.println(id);
        proRataShareMapper.deleteById(id);
        return ResponseEntity.success("ok");
    }


//    @PostMapping("upload")
//    public void upload(MultipartFile file) throws Exception {
//        // 读取数据
//        List<ShareData> list = EasyExcel.read(file.getInputStream()).head(ShareData.class).sheet().doReadSync();
//        log.info("读取到数据size:{}", list.size());
//    }

}
