package com.rzyk.departmentHead.mapper;

import java.util.List;
import com.rzyk.departmentHead.domain.DepartmentHead;

/**
 * 监考老师信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
public interface DepartmentHeadMapper 
{
    /**
     * 查询监考老师信息
     * 
     * @param id 监考老师信息主键
     * @return 监考老师信息
     */
    public DepartmentHead selectDepartmentHeadById(String id);

    /**
     * 查询监考老师信息列表
     * 
     * @param departmentHead 监考老师信息
     * @return 监考老师信息集合
     */
    public List<DepartmentHead> selectDepartmentHeadList(DepartmentHead departmentHead);

    /**
     * 新增监考老师信息
     * 
     * @param departmentHead 监考老师信息
     * @return 结果
     */
    public int insertDepartmentHead(DepartmentHead departmentHead);

    /**
     * 修改监考老师信息
     * 
     * @param departmentHead 监考老师信息
     * @return 结果
     */
    public int updateDepartmentHead(DepartmentHead departmentHead);

    /**
     * 删除监考老师信息
     * 
     * @param id 监考老师信息主键
     * @return 结果
     */
    public int deleteDepartmentHeadById(String id);

    /**
     * 批量删除监考老师信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDepartmentHeadByIds(String[] ids);
}
