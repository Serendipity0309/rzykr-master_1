package com.rzyk.TExamData.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rzyk.TExamData.domain.TExamData;
import org.apache.ibatis.annotations.Param;

/**
 * 考试安排数据Service接口
 * 
 * @author ruoyi
 * @date 2022-11-07
 */
public interface ITExamDataService 
{
    /**
     * 查询考试安排数据
     * 
     * @param id 考试安排数据主键
     * @return 考试安排数据
     */
    public TExamData selectTExamDataById(Long id);

    /**
     * 查询考试安排数据列表
     * 
     * @param tExamData 考试安排数据
     * @return 考试安排数据集合
     */
    public List<TExamData> selectTExamDataList(TExamData tExamData);

    /**
     * 新增考试安排数据
     * 
     * @param tExamData 考试安排数据
     * @return 结果
     */
    public int insertTExamData(TExamData tExamData);

    /**
     * 修改考试安排数据
     * 
     * @param tExamData 考试安排数据
     * @return 结果
     */
    public int updateTExamData(TExamData tExamData);

    /**
     * 批量删除考试安排数据
     * 
     * @param ids 需要删除的考试安排数据主键集合
     * @return 结果
     */
    public int deleteTExamDataByIds(Long[] ids);

    /**
     * 删除考试安排数据信息
     * 
     * @param id 考试安排数据主键
     * @return 结果
     */
    public int deleteTExamDataById(Long id);

    public String importDate(List<TExamData> tExamDataList,Long brach,String semester_sel);

    List<TExamData> selectTExamDataByIds(List<String> ids);

    List<TExamData> selectTExamDataListByKeyword(String date, String keyword);

    List<Map<String, String>> selectTableField();

    Map<String, List<TExamData>>getExamDataGroupByDate();

    List<Map<String ,String>> getAllteacher();

    List<Map<String,String>> getSyncPlace(String id);

    void exchangeExamPlace(String oringeId, String exchangeId);
}
