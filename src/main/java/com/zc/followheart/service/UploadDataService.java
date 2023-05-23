package com.zc.followheart.service;

import com.zc.followheart.entity.UploadData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadDataService {

    public void save(List<UploadData> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    }
}
