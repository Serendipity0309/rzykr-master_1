package com.rzyk.Supervisor.mapper;

import com.rzyk.Supervisor.domain.Supervisor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 督导Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-25
 */
public interface SupervisorMapper 
{
    /**
     * 查询督导
     * 
     * @param id 督导主键
     * @return 督导
     */
    public Supervisor selectSupervisorById(Long id);

    /**
     * 查询督导列表
     * 
     * @param supervisor 督导
     * @return 督导集合
     */
    public List<Supervisor> selectSupervisorList(Supervisor supervisor);

    /**
     * 新增督导
     * 
     * @param supervisor 督导
     * @return 结果
     */
    public int insertSupervisor(Supervisor supervisor);

    /**
     * 修改督导
     * 
     * @param supervisor 督导
     * @return 结果
     */
    public int updateSupervisor(Supervisor supervisor);

    /**
     * 删除督导
     * 
     * @param id 督导主键
     * @return 结果
     */
    public int deleteSupervisorById(Long id);

    /**
     * 批量删除督导
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupervisorByIds(Long[] ids);

    List<Supervisor> selectSupervisorByUser(
            @Param("status") Long status,
            @Param("activeIndex") Long activeIndex,
            @Param("parentId") Long parentId);
}
