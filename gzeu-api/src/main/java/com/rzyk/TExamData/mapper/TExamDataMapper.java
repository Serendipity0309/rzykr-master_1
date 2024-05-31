package com.rzyk.TExamData.mapper;

import java.util.List;
import java.util.Map;

import com.rzyk.TExamData.domain.TExamData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 考试安排数据Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-07
 */
public interface TExamDataMapper
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
     * 删除考试安排数据
     * 
     * @param id 考试安排数据主键
     * @return 结果
     */
    public int deleteTExamDataById(Long id);

    /**
     * 批量删除考试安排数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTExamDataByIds(Long[] ids);

    List<TExamData> selectTExamDataByIds(@Param("ids") List<String> ids);

    List<TExamData> selectTExamDataListByKeyword(@Param("date") String date,@Param("keyword")  String keyword);

    List<Map<String, String>> selectTableField();

    List<TExamData> getExamDataGroupByDate(@Param("dateCurr") String year,@Param("dateNext") String month,@Param("name") String name);

    List<Map<String ,String>> getAllteacher();

    List<Map<String,String>> getSyncPlace( String id);

    void exchangeExamPlace(@Param("oringeId") String oringeId,@Param("exchangeId") String exchangeId);
}
