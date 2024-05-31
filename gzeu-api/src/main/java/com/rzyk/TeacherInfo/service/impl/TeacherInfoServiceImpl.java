package com.rzyk.TeacherInfo.service.impl;

import com.rzyk.TExamData.domain.TExamData;
import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.TeacherInfo.mapper.TeacherInfoMapper;
import com.rzyk.TeacherInfo.service.ITeacherInfoService;
import com.rzyk.common.utils.DateUtils;
import com.rzyk.utils.RzykUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 监考老师信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-25
 */
@Service
public class TeacherInfoServiceImpl implements ITeacherInfoService
{
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    /**
     * 查询监考老师信息
     * 
     * @param id 监考老师信息主键
     * @return 监考老师信息
     */
    @Override
    public TeacherInfo selectTeacherInfoById(String id)
    {
        return teacherInfoMapper.selectTeacherInfoById(id);
    }

    /**
     * 查询监考老师信息列表
     * 
     * @param teacherInfo 监考老师信息
     * @return 监考老师信息
     */
    @Override
    public List<TeacherInfo> selectTeacherInfoList(TeacherInfo teacherInfo)
    {
        return teacherInfoMapper.selectTeacherInfoList(teacherInfo);
    }

    /**
     * 新增监考老师信息
     * 
     * @param teacherInfo 监考老师信息
     * @return 结果
     */
    @Override
    public int insertTeacherInfo(TeacherInfo teacherInfo)
    {
        teacherInfo.setCreateTime(DateUtils.getNowDate());
        return teacherInfoMapper.insertTeacherInfo(teacherInfo);
    }

    /**
     * 修改监考老师信息
     * 
     * @param teacherInfo 监考老师信息
     * @return 结果
     */
    @Override
    public int updateTeacherInfo(TeacherInfo teacherInfo)
    {
        teacherInfo.setUpdateTime(DateUtils.getNowDate());
        return teacherInfoMapper.updateTeacherInfo(teacherInfo);
    }

    /**
     * 批量删除监考老师信息
     * 
     * @param ids 需要删除的监考老师信息主键
     * @return 结果
     */
    @Override
    public int deleteTeacherInfoByIds(String[] ids)
    {
        return teacherInfoMapper.deleteTeacherInfoByIds(ids);
    }

    /**
     * 删除监考老师信息信息
     * 
     * @param id 监考老师信息主键
     * @return 结果
     */
    @Override
    public int deleteTeacherInfoById(String id)
    {
        return teacherInfoMapper.deleteTeacherInfoById(id);
    }

    @Override
    public String importDate(List<TeacherInfo> teacherInfos) {
        //记录成功数
        AtomicLong success = new AtomicLong();
        //记录失败数
        AtomicLong fail = new AtomicLong();
        //遍历依次录入
        for (TeacherInfo teacherInfo : teacherInfos) {
            //检查数据是否已经存在
            List<TeacherInfo> teacherInfosDB = teacherInfoMapper.selectTeacherInfoList(teacherInfo);
            if (teacherInfosDB.isEmpty())
            //不存在就插入
            {
                teacherInfoMapper.insertTeacherInfo(teacherInfo);
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

    @Override
    public List<Map<String, Object>> selectTeacherPermission() {
        final String id = RzykUtils.getLoginUser().getString("id");
        return teacherInfoMapper.selectTeacherPermission(id);
    }
}
