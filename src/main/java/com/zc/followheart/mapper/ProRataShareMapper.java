package com.zc.followheart.mapper;

import com.zc.followheart.entity.ProRataShareData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProRataShareMapper {

    void batchInsert(List<ProRataShareData> list);


    @Select("select ceo_office as ceoOffice,department,organization,unit," +
            "expense_account as expenseAccount,name,project,scale from pro_rata_share " +
            "where version = (select MAX(version) from pro_rata_share A where A.is_deleted = 0) and name = #{name} and is_deleted = 0 ")
    List<ProRataShareData> selectByName(String name);



    List<ProRataShareData> selectAll(String name);

    @Update("update pro_rata_share set is_deleted=1 where id = #{id}")
    void deleteById(Long id);

    @Select("select MAX(version) from pro_rata_share where is_deleted = 0")
    Long selectMaxVersion();

    void updateById(ProRataShareData data);
}
