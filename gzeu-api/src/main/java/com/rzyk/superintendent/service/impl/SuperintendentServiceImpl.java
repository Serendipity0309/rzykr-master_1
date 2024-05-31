package com.rzyk.superintendent.service.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.common.utils.DateUtils;
import com.rzyk.superintendent.domain.Superintendent;
import com.rzyk.superintendent.mapper.SuperintendentMapper;
import com.rzyk.superintendent.service.ISuperintendentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 监考老师信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@Service
public class SuperintendentServiceImpl implements ISuperintendentService
{
    @Autowired
    private SuperintendentMapper superintendentMapper;

    /**
     * 查询监考老师信息
     * 
     * @param id 监考老师信息主键
     * @return 监考老师信息
     */
    @Override
    public Superintendent selectSuperintendentById(String id)
    {
        return superintendentMapper.selectSuperintendentById(id);
    }

    /**
     * 查询监考老师信息列表
     * 
     * @param superintendent 监考老师信息
     * @return 监考老师信息
     */
    @Override
    public List<Superintendent> selectSuperintendentList(Superintendent superintendent)
    {
        return superintendentMapper.selectSuperintendentList(superintendent);
    }

    /**
     * 新增监考老师信息
     * 
     * @param superintendent 监考老师信息
     * @return 结果
     */
    @Override
    public int insertSuperintendent(Superintendent superintendent)
    {
        superintendent.setCreateTime(DateUtils.getNowDate());
        return superintendentMapper.insertSuperintendent(superintendent);
    }

    /**
     * 修改监考老师信息
     * 
     * @param superintendent 监考老师信息
     * @return 结果
     */
    @Override
    public int updateSuperintendent(Superintendent superintendent)
    {
        superintendent.setUpdateTime(DateUtils.getNowDate());
        return superintendentMapper.updateSuperintendent(superintendent);
    }

    /**
     * 批量删除监考老师信息
     * 
     * @param ids 需要删除的监考老师信息主键
     * @return 结果
     */
    @Override
    public int deleteSuperintendentByIds(String[] ids)
    {
        return superintendentMapper.deleteSuperintendentByIds(ids);
    }

    /**
     * 删除监考老师信息信息
     * 
     * @param id 监考老师信息主键
     * @return 结果
     */
    @Override
    public int deleteSuperintendentById(String id)
    {
        return superintendentMapper.deleteSuperintendentById(id);
    }

    @Override
    public String importDate(List<Superintendent> teacherInfos) {
        //记录成功数
        AtomicLong success = new AtomicLong();
        //记录失败数
        AtomicLong fail = new AtomicLong();
        //遍历依次录入
        for (Superintendent elm : teacherInfos) {
            //检查数据是否已经存在
            List<Superintendent> teacherInfosDB = superintendentMapper.selectSuperintendentList(elm);
            if (teacherInfosDB.isEmpty())
            //不存在就插入
            {
                superintendentMapper.insertSuperintendent(elm);
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
