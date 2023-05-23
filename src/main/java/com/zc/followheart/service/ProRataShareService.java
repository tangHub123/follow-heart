package com.zc.followheart.service;

import com.alibaba.fastjson2.JSON;
import com.zc.followheart.entity.ProRataShareData;
import com.zc.followheart.mapper.ProRataShareMapper;
import com.zc.followheart.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProRataShareService {

    @Autowired
    ProRataShareMapper mapper;

    public void save(List<ProRataShareData> list) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        log.info("ProRataShareService save size:{}",list.size());
        long time = DateUtil.getDateTime();
        list.forEach(d->d.setVersion(time));
        mapper.batchInsert(list);
    }

}
