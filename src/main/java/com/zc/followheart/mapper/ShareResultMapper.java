package com.zc.followheart.mapper;

import com.zc.followheart.entity.ProRataShareData;
import com.zc.followheart.entity.ShareResultData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ShareResultMapper {

    void batchInsert(List<ShareResultData> list);


    List<ShareResultData> selectAll();
}
