package com.zc.followheart.mapper;

import com.zc.followheart.entity.OrgProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrgProjectMapper {

    @Select("select id,project_name as projectName,project_code as projectCode,project_name2 as projectName2," +
            "org_name as orgName,bu_bp_name as buBpName,bu_bp_code as buBpCode from org_project order by project_code")
    List<OrgProject> selectAll();

    @Select("select project_name as projectName,project_code as projectCode,project_name2 as projectName2,org_name as orgName,\n" +
            "bu_bp_name as buBpName,bu_bp_code as buBpCode from org_project where project_name = #{name} ")
    OrgProject selectByProjectName(String name);
}
