package com.rzyk.Supervisor.service;

import com.rzyk.Supervisor.domain.Supervisor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 督导Service接口
 * 
 * @author ruoyi
 * @date 2022-12-25
 */
public interface ISupervisorService 
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
    @Transactional
    public int insertSupervisor(Supervisor supervisor);

    /**
     * 修改督导
     * 
     * @param supervisor 督导
     * @return 结果
     */
    @Transactional
    public int updateSupervisor(Supervisor supervisor);

    /**
     * 批量删除督导
     * 
     * @param ids 需要删除的督导主键集合
     * @return 结果
     */
    @Transactional
    public int deleteSupervisorByIds(Long[] ids);

    /**
     * 删除督导信息
     * 
     * @param id 督导主键
     * @return 结果
     */
    @Transactional
    public int deleteSupervisorById(Long id);
    /***
     * 根据用户查询督导信息

     */
    List<Supervisor> selectSupervisorByUser(Long status,Long activeIndex,Long id);
}
