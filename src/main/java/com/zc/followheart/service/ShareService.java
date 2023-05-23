package com.zc.followheart.service;

import com.zc.followheart.entity.OrgProject;
import com.zc.followheart.entity.ProRataShareData;
import com.zc.followheart.entity.ShareData;
import com.zc.followheart.entity.ShareResultData;
import com.zc.followheart.mapper.OrgProjectMapper;
import com.zc.followheart.mapper.ProRataShareMapper;
import com.zc.followheart.mapper.ShareResultMapper;
import com.zc.followheart.util.BigDecimalUtil;
import com.zc.followheart.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ShareService {

    @Autowired
    OrgProjectMapper orgProjectMapper;

    @Autowired
    ProRataShareMapper proRataShareMapper;

    @Autowired
    ShareResultMapper shareResultMapper;

    /**
     * 处理分摊数据
     *
     * @param shareDataList
     * @return
     */
    public List<ShareResultData> delShareData(List<ShareData> shareDataList) {
        // 记录分摊原数据
        List<ShareResultData> resultDataList = new ArrayList<>();

        // 版本号
        long version = DateUtil.getDateTime();
        // 处理数据
        for (ShareData shareData : shareDataList) {
            //通过人名查询项目分摊比例
            List<ProRataShareData> proRataShareDataList = proRataShareMapper.selectByName(shareData.getName());
            if (CollectionUtils.isEmpty(proRataShareDataList)) {
                log.info("未查询到项目分摊比例:姓名:{}", shareData.getName());
                ShareResultData shareResultData = new ShareResultData();
                BeanUtils.copyProperties(shareData, shareResultData);
                shareResultData.setVersion(version);
                resultDataList.add(shareResultData);
            } else {
                for (ProRataShareData proRataShareData : proRataShareDataList) {
                    ShareResultData shareResultData = new ShareResultData();
                    BeanUtils.copyProperties(shareData, shareResultData);
                    // 工资分摊
                    shareResultData.setProjectSalaryShare(BigDecimalUtil.mul(proRataShareData.getScale(), shareResultData.getAmountWagesPayable()));
                    // 社保分摊
                    shareResultData.setProjectSocialShare(BigDecimalUtil.mul(proRataShareData.getScale(), shareResultData.getAmountSocial()));
                    // 公积金分摊
                    shareResultData.setProjectGjjShare(BigDecimalUtil.mul(proRataShareData.getScale(), shareResultData.getAmountGjj()));
                    // 分摊比例
                    shareResultData.setProRataShare(proRataShareData.getScale());
                    //项目
                    shareResultData.setProjectName(proRataShareData.getProject());

                    //辅助列
//                    shareResultData.setAssist();
                    OrgProject orgProject = orgProjectMapper.selectByProjectName(proRataShareData.getProject());
                    if (null != orgProject) {
                        // 项目编码
                        shareResultData.setProjectCode(orgProject.getProjectCode());
                        // BU和BP编码
                        shareResultData.setBuBpCode(orgProject.getBuBpCode());
                        // BU和BP名称
                        shareResultData.setBuBpName(orgProject.getBuBpName());
                    }
                    // 设置版本号
                    shareResultData.setVersion(version);
                    resultDataList.add(shareResultData);
                }
            }
        }

        try{
            shareResultMapper.batchInsert(resultDataList);
        }catch (Exception e){
            log.error("分摊结果保存异常：{}",e.getMessage());
        }

        // 记录分摊结果表
        return resultDataList;
    }
}
