package com.rzyk.Supervisor.service.impl;

import java.util.List;

import com.rzyk.Supervisor.domain.Supervisor;
import com.rzyk.Supervisor.mapper.SupervisorMapper;
import com.rzyk.Supervisor.service.ISupervisorService;
import com.rzyk.common.core.domain.entity.SysUser;
import com.rzyk.common.core.domain.model.LoginUser;
import com.rzyk.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 督导Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-25
 */
@Service
public class SupervisorServiceImpl implements ISupervisorService
{
    @Autowired
    private SupervisorMapper supervisorMapper;

    /**
     * 查询督导
     * 
     * @param id 督导主键
     * @return 督导
     */
    @Override
    public Supervisor selectSupervisorById(Long id)
    {
        return supervisorMapper.selectSupervisorById(id);
    }

    /**
     * 查询督导列表
     * 
     * @param supervisor 督导
     * @return 督导
     */
    @Override
    public List<Supervisor> selectSupervisorList(Supervisor supervisor)
    {
        return supervisorMapper.selectSupervisorList(supervisor);
    }

    /**
     * 新增督导
     * 
     * @param supervisor 督导
     * @return 结果
     */
    @Override
    public int insertSupervisor(Supervisor supervisor)
    {
        return supervisorMapper.insertSupervisor(supervisor);
    }

    /**
     * 修改督导
     * 
     * @param supervisor 督导
     * @return 结果
     */

    @Override
    public int updateSupervisor(Supervisor supervisor)
    {
        return supervisorMapper.updateSupervisor(supervisor);
    }

    /**
     * 批量删除督导
     * 
     * @param ids 需要删除的督导主键
     * @return 结果
     */
    @Override
    public int deleteSupervisorByIds(Long[] ids)
    {
        return supervisorMapper.deleteSupervisorByIds(ids);
    }

    /**
     * 删除督导信息
     * 
     * @param id 督导主键
     * @return 结果
     */
    @Override
    public int deleteSupervisorById(Long id)
    {
        return supervisorMapper.deleteSupervisorById(id);
    }

    @Override
    public List<Supervisor> selectSupervisorByUser(Long status,Long activeIndex,Long id) {
        List<Supervisor> supervisorListDb=  supervisorMapper.selectSupervisorByUser(status,activeIndex,id);
        return supervisorListDb;
    }
}
