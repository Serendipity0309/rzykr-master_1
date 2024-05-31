package com.rzyk.examLog.mapper;

import java.util.List;
import java.util.Map;

import com.rzyk.examLog.domain.ExamLog;
import org.apache.ibatis.annotations.Param;

/**
 * 试卷操作日志Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-07
 */
public interface ExamLogMapper 
{
    /**
     * 查询试卷操作日志
     * 
     * @param id 试卷操作日志主键
     * @return 试卷操作日志
     */
    public ExamLog selectExamLogById(Long id);

    /**
     * 查询试卷操作日志列表
     * 
     * @param examLog 试卷操作日志
     * @return 试卷操作日志集合
     */
    public List<ExamLog> selectExamLogList(ExamLog examLog);

    /**
     * 新增试卷操作日志
     * 
     * @param examLog 试卷操作日志
     * @return 结果
     */
    public int insertExamLog(ExamLog examLog);

    /**
     * 修改试卷操作日志
     * 
     * @param examLog 试卷操作日志
     * @return 结果
     */
    public int updateExamLog(ExamLog examLog);

    /**
     * 删除试卷操作日志
     * 
     * @param id 试卷操作日志主键
     * @return 结果
     */
    public int deleteExamLogById(Long id);

    /**
     * 批量删除试卷操作日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExamLogByIds(Long[] ids);

    ExamLog selectExamLogByExamId(String id);

    List<Map<String,String>> selectExamlogForCountInvigilateAscLimit(int par);

    List<Map<String,String>> selectExamlogForCountInvigilateDescLimit(int par);

    List<Map<String,String>> selectExamlogRejectAscLimit(int par);

    List<Map<String,String>> selectExamlogRejectDescLimit(int par);

    void addReasonLog(@Param("id") Long id,@Param("reason")  String reason);


    List<Map<String, String>> selectExamLogListDocument(int i);

    Integer count();

    Integer countTeacher();
}
