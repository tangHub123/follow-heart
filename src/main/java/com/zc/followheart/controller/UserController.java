package com.zc.followheart.controller;

import com.zc.followheart.entity.ResponseEntity;
import com.zc.followheart.entity.User;
import com.zc.followheart.service.UploadDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    UploadDataService uploadDataService;


    @PostMapping("login")
    @ResponseBody
    public ResponseEntity<String> login() {
        return ResponseEntity.success("");
    }


    @GetMapping("info")
    @ResponseBody
    public ResponseEntity<User> info() {
        User user = new User();
        user.setName("admin");
        return ResponseEntity.success(user);
    }


    @PostMapping("logout")
    @ResponseBody
    public ResponseEntity<String> logout() {
        User user = new User();
        user.setName("admin");
        return ResponseEntity.success("");
    }
}
