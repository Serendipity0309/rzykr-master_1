package com.rzyk.TExamData.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.SimpleFormatter;

import com.rzyk.TExamData.domain.TExamData;
import com.rzyk.TExamData.mapper.TExamDataMapper;
import com.rzyk.TExamData.service.ITExamDataService;
import com.rzyk.utils.RzykUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

/**
 * 考试安排数据Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-07
 */
@Service
public class TExamDataServiceImpl implements ITExamDataService {
    @Resource
    private TExamDataMapper tExamDataMapper;

    @Override
    public String importDate(List<TExamData> tExamDataList, Long brach, String semester_sel) {
        //记录成功数
        AtomicLong success = new AtomicLong();
        //记录失败数
        AtomicLong fail = new AtomicLong();
        //遍历依次录入
        for (TExamData tExamData : tExamDataList) {
            //先查询看数据是否重复
            List<TExamData> tExamData_DB = tExamDataMapper.selectTExamDataList(tExamData.setBatch(brach).setSemester(semester_sel));
            if (tExamData_DB.size() > 0) {
                fail.incrementAndGet();
            } else {
                int i = tExamDataMapper.insertTExamData(tExamData.setBatch(brach).setSemester(semester_sel));
                success.incrementAndGet();
            }
        }
        return String.format("成功%s条,失败%s条", success, fail);
    }

    /**
     * 查询考试安排数据
     *
     * @param id 考试安排数据主键
     * @return 考试安排数据
     */
    @Override
    public TExamData selectTExamDataById(Long id) {
        return tExamDataMapper.selectTExamDataById(id);
    }

    @Override
    public List<TExamData> selectTExamDataListByKeyword(String date, String keyword) {
        return tExamDataMapper.selectTExamDataListByKeyword(date, keyword);
    }

    @Override
    public List<Map<String, String>> selectTableField() {
        return tExamDataMapper.selectTableField();
    }

    @Override
    public Map<String, List<TExamData>> getExamDataGroupByDate() {
        final String format = new SimpleDateFormat("yyyy/MM").format(new Date());
        final String[] date_split = format.split("/");
        String year = date_split[0];
        String month = date_split[1];
        String dateCurr="".concat(year).concat("-").concat(month);
        String dateNext=year+"-0"+(Integer.valueOf(month)+1);
        if (Integer.valueOf(month)==12){
            dateNext=(Integer.valueOf(year)+1)+"-"+"01";
        }
        String name = RzykUtils.getLoginUser().getString("name");
        List<TExamData> tExamDataList = tExamDataMapper.getExamDataGroupByDate(dateCurr, dateNext, name);
        Map<String, List<TExamData>> resMap = new LinkedHashMap<>();
        for (TExamData tExamData : tExamDataList) {
            if (resMap.containsKey(tExamData.getExamdate())){
                resMap.get(tExamData.getExamdate()).add(tExamData);
            }else {
                ArrayList<TExamData> arrayList = new ArrayList<>();
                arrayList.add(tExamData);
                resMap.put(tExamData.getExamdate(),arrayList);
            }
        }
        return resMap;
    }

    @Override
    public void exchangeExamPlace(String oringeId, String exchangeId) {
        tExamDataMapper.exchangeExamPlace(oringeId,exchangeId);
    }

    @Override
    public List<Map<String, String>> getSyncPlace(String id) {
        return tExamDataMapper.getSyncPlace(id);
    }

    @Override
    public List<Map<String ,String>> getAllteacher() {
        return tExamDataMapper.getAllteacher();
    }

    /**
     * 查询考试安排数据列表
     *
     * @param tExamData 考试安排数据
     * @return 考试安排数据
     */
    @Override
    public List<TExamData> selectTExamDataList(TExamData tExamData) {
        return tExamDataMapper.selectTExamDataList(tExamData);
    }

    /**
     * 新增考试安排数据
     *
     * @param tExamData 考试安排数据
     * @return 结果
     */
    @Override
    public int insertTExamData(TExamData tExamData) {
        return tExamDataMapper.insertTExamData(tExamData);
    }

    /**
     * 修改考试安排数据
     *
     * @param tExamData 考试安排数据
     * @return 结果
     */
    @Override
    public int updateTExamData(TExamData tExamData) {
        return tExamDataMapper.updateTExamData(tExamData);
    }

    /**
     * 批量删除考试安排数据
     *
     * @param ids 需要删除的考试安排数据主键
     * @return 结果
     */
    @Override
    public int deleteTExamDataByIds(Long[] ids) {
        return tExamDataMapper.deleteTExamDataByIds(ids);
    }

    /**
     * 删除考试安排数据信息
     *
     * @param id 考试安排数据主键
     * @return 结果
     */
    @Override
    public int deleteTExamDataById(Long id) {
        return tExamDataMapper.deleteTExamDataById(id);
    }

    @Override
    public List<TExamData> selectTExamDataByIds(List<String> ids) {
        return tExamDataMapper.selectTExamDataByIds(ids);
    }
}
