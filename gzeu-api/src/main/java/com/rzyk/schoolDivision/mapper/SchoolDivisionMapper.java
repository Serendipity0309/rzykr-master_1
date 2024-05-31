package com.rzyk.schoolDivision.mapper;

import java.util.List;
import com.rzyk.schoolDivision.domain.SchoolDivision;

/**
 * 分院管理Mapper接口
 * 
 * @author ccj
 * @date 2023-03-15
 */
public interface SchoolDivisionMapper 
{
    /**
     * 查询分院管理
     * 
     * @param id 分院管理主键
     * @return 分院管理
     */
    public SchoolDivision selectSchoolDivisionById(Long id);

    /**
     * 查询分院管理列表
     * 
     * @param schoolDivision 分院管理
     * @return 分院管理集合
     */
    public List<SchoolDivision> selectSchoolDivisionList(SchoolDivision schoolDivision);

    /**
     * 新增分院管理
     * 
     * @param schoolDivision 分院管理
     * @return 结果
     */
    public int insertSchoolDivision(SchoolDivision schoolDivision);

    /**
     * 修改分院管理
     * 
     * @param schoolDivision 分院管理
     * @return 结果
     */
    public int updateSchoolDivision(SchoolDivision schoolDivision);

    /**
     * 删除分院管理
     * 
     * @param id 分院管理主键
     * @return 结果
     */
    public int deleteSchoolDivisionById(Long id);

    /**
     * 批量删除分院管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSchoolDivisionByIds(Long[] ids);
}
