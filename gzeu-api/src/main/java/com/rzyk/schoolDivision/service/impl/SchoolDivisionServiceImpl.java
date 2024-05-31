package com.rzyk.schoolDivision.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzyk.schoolDivision.mapper.SchoolDivisionMapper;
import com.rzyk.schoolDivision.domain.SchoolDivision;
import com.rzyk.schoolDivision.service.ISchoolDivisionService;

/**
 * 分院管理Service业务层处理
 * 
 * @author ccj
 * @date 2023-03-15
 */
@Service
public class SchoolDivisionServiceImpl implements ISchoolDivisionService 
{
    @Autowired
    private SchoolDivisionMapper schoolDivisionMapper;

    /**
     * 查询分院管理
     * 
     * @param id 分院管理主键
     * @return 分院管理
     */
    @Override
    public SchoolDivision selectSchoolDivisionById(Long id)
    {
        return schoolDivisionMapper.selectSchoolDivisionById(id);
    }

    /**
     * 查询分院管理列表
     * 
     * @param schoolDivision 分院管理
     * @return 分院管理
     */
    @Override
    public List<SchoolDivision> selectSchoolDivisionList(SchoolDivision schoolDivision)
    {
        return schoolDivisionMapper.selectSchoolDivisionList(schoolDivision);
    }

    /**
     * 新增分院管理
     * 
     * @param schoolDivision 分院管理
     * @return 结果
     */
    @Override
    public int insertSchoolDivision(SchoolDivision schoolDivision)
    {
        return schoolDivisionMapper.insertSchoolDivision(schoolDivision);
    }

    /**
     * 修改分院管理
     * 
     * @param schoolDivision 分院管理
     * @return 结果
     */
    @Override
    public int updateSchoolDivision(SchoolDivision schoolDivision)
    {
        return schoolDivisionMapper.updateSchoolDivision(schoolDivision);
    }

    /**
     * 批量删除分院管理
     * 
     * @param ids 需要删除的分院管理主键
     * @return 结果
     */
    @Override
    public int deleteSchoolDivisionByIds(Long[] ids)
    {
        return schoolDivisionMapper.deleteSchoolDivisionByIds(ids);
    }

    /**
     * 删除分院管理信息
     * 
     * @param id 分院管理主键
     * @return 结果
     */
    @Override
    public int deleteSchoolDivisionById(Long id)
    {
        return schoolDivisionMapper.deleteSchoolDivisionById(id);
    }
}
