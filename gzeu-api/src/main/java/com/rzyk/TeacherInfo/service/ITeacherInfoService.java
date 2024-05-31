package com.rzyk.TeacherInfo.service;


import com.rzyk.TeacherInfo.domain.TeacherInfo;
import java.util.List;
import java.util.Map;

/**
 * 监考老师信息Service接口
 * 
 * @author ruoyi
 * @date 2022-12-25
 */
public interface ITeacherInfoService 
{
    /**
     * 查询监考老师信息
     * 
     * @param id 监考老师信息主键
     * @return 监考老师信息
     */
    public TeacherInfo selectTeacherInfoById(String id);

    /**
     * 查询监考老师信息列表
     * 
     * @param teacherInfo 监考老师信息
     * @return 监考老师信息集合
     */
    public List<TeacherInfo> selectTeacherInfoList(TeacherInfo teacherInfo);

    /**
     * 新增监考老师信息
     * 
     * @param teacherInfo 监考老师信息
     * @return 结果
     */
    public int insertTeacherInfo(TeacherInfo teacherInfo);

    /**
     * 修改监考老师信息
     * 
     * @param teacherInfo 监考老师信息
     * @return 结果
     */
    public int updateTeacherInfo(TeacherInfo teacherInfo);

    /**
     * 批量删除监考老师信息
     * 
     * @param ids 需要删除的监考老师信息主键集合
     * @return 结果
     */
    public int deleteTeacherInfoByIds(String[] ids);

    /**
     * 删除监考老师信息信息
     * 
     * @param id 监考老师信息主键
     * @return 结果
     */
    public int deleteTeacherInfoById(String id);

    String importDate(List<TeacherInfo> teacherInfos);

    List<Map<String, Object>> selectTeacherPermission();
}
