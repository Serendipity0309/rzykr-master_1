package com.rzyk.departmentHead.service.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzyk.departmentHead.mapper.DepartmentHeadMapper;
import com.rzyk.departmentHead.domain.DepartmentHead;
import com.rzyk.departmentHead.service.IDepartmentHeadService;

/**
 * 监考老师信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@Service
public class DepartmentHeadServiceImpl implements IDepartmentHeadService 
{
    @Autowired
    private DepartmentHeadMapper departmentHeadMapper;

    /**
     * 查询监考老师信息
     * 
     * @param id 监考老师信息主键
     * @return 监考老师信息
     */
    @Override
    public DepartmentHead selectDepartmentHeadById(String id)
    {
        return departmentHeadMapper.selectDepartmentHeadById(id);
    }

    /**
     * 查询监考老师信息列表
     * 
     * @param departmentHead 监考老师信息
     * @return 监考老师信息
     */
    @Override
    public List<DepartmentHead> selectDepartmentHeadList(DepartmentHead departmentHead)
    {
        return departmentHeadMapper.selectDepartmentHeadList(departmentHead);
    }

    /**
     * 新增监考老师信息
     * 
     * @param departmentHead 监考老师信息
     * @return 结果
     */
    @Override
    public int insertDepartmentHead(DepartmentHead departmentHead)
    {
        departmentHead.setCreateTime(DateUtils.getNowDate());
        return departmentHeadMapper.insertDepartmentHead(departmentHead);
    }

    /**
     * 修改监考老师信息
     * 
     * @param departmentHead 监考老师信息
     * @return 结果
     */
    @Override
    public int updateDepartmentHead(DepartmentHead departmentHead)
    {
        departmentHead.setUpdateTime(DateUtils.getNowDate());
        return departmentHeadMapper.updateDepartmentHead(departmentHead);
    }

    /**
     * 批量删除监考老师信息
     * 
     * @param ids 需要删除的监考老师信息主键
     * @return 结果
     */
    @Override
    public int deleteDepartmentHeadByIds(String[] ids)
    {
        return departmentHeadMapper.deleteDepartmentHeadByIds(ids);
    }

    /**
     * 删除监考老师信息信息
     * 
     * @param id 监考老师信息主键
     * @return 结果
     */
    @Override
    public int deleteDepartmentHeadById(String id)
    {
        return departmentHeadMapper.deleteDepartmentHeadById(id);
    }

    @Override
    public String importDate(List<DepartmentHead> teacherInfos) {
        //记录成功数
        AtomicLong success = new AtomicLong();
        //记录失败数
        AtomicLong fail = new AtomicLong();
        //遍历依次录入
        for (DepartmentHead elm : teacherInfos) {
            //检查数据是否已经存在
            List<DepartmentHead> teacherInfosDB = departmentHeadMapper.selectDepartmentHeadList(elm);
            if (teacherInfosDB.isEmpty())
            //不存在就插入
            {
                departmentHeadMapper.insertDepartmentHead(elm);
                success.incrementAndGet();
            }else
            {
                //如果存在跳过本条数据
                fail.incrementAndGet();
                continue;
            }
        }
        return String.format("成功%s条,失败%s条",success,fail);
    }
}
