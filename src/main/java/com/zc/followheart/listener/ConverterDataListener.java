package com.zc.followheart.listener;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;

import com.zc.followheart.entity.ProRataShareData;
import com.zc.followheart.service.ProRataShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class ConverterDataListener implements ReadListener<ProRataShareData> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private List<ProRataShareData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private ProRataShareService shareService;

    public ConverterDataListener(ProRataShareService shareService) {
        this.shareService = shareService;
    }

    @Override
    public void invoke(ProRataShareData data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * @Description invokeHeadMap读取excel表头，校验表头是否正确
     * @author qingyun
     * @Date 2021年5月19日 上午10:44:43
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        Map<Integer, String> head = new HashMap<>();
        try {
            head = getIndexNameMap(ProRataShareData.class);   //通过class获取到使用@ExcelProperty注解配置的字段
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Set<Integer> keySet = head.keySet();  //解析到的excel表头和实体配置的进行比对
        for (Integer key : keySet) {
            if (StringUtils.isEmpty(headMap.get(key))) {
//                message.setType(Message.ERROR);
//                message.setMsg("表头第"+key+1+"列为空，请参照模板填写");
                throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel");
            }
            if (!headMap.get(key).getStringValue().equals(head.get(key))) {
//                message.setType(Message.ERROR);
                System.out.println("表头第"+key+1+"列【"+headMap.get(key)+"】与模板【"+head.get(key)+"】不一致，请参照模板填写");
                throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel");
            }
        }
    }

    /**
     * @Description 通过class获取类字段信息
     * @author qingyun
     * @Date 2021年5月19日 下午1:41:47
     */
    public Map<Integer, String> getIndexNameMap(Class clazz) throws NoSuchFieldException {
        Map<Integer, String> result = new HashMap<>();
        Field field;
        Field[] fields = clazz.getDeclaredFields();     //获取类中所有的属性
        for (int i = 0; i < fields.length; i++) {
            field = clazz.getDeclaredField(fields[i].getName());
            field.setAccessible(true);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);//获取根据注解的方式获取ExcelProperty修饰的字段
            if (excelProperty != null) {
                int index = excelProperty.index();         //索引值
                String[] values = excelProperty.value();   //字段值
                StringBuilder value = new StringBuilder();
                for (String v : values) {
                    value.append(v);
                }
                result.put(i, value.toString());
            }
        }
        return result;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        shareService.save(cachedDataList);
        log.info("存储数据库成功！");
    }
}
