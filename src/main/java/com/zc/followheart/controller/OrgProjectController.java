package com.zc.followheart.controller;


import com.alibaba.fastjson.JSON;
import com.zc.followheart.entity.OrgProject;
import com.zc.followheart.entity.ResponseEntity;
import com.zc.followheart.mapper.OrgProjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("org/project")
@Slf4j
public class OrgProjectController {

    @Autowired
    OrgProjectMapper orgProjectMapper;

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<OrgProject>> selectAll()  {
        System.out.println(".......");
        List<OrgProject> list = orgProjectMapper.selectAll();
        return ResponseEntity.success(list);
    }
}
