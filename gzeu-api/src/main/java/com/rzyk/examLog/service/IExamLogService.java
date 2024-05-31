package com.rzyk.examLog.service;

import com.rzyk.examLog.domain.ExamLog;

import java.util.List;

/**
 * 试卷操作日志Service接口
 *
 * @author ruoyi
 * @date 2023-06-07
 */
public interface IExamLogService {
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
     * 批量删除试卷操作日志
     *
     * @param ids 需要删除的试卷操作日志主键集合
     * @return 结果
     */
    public int deleteExamLogByIds(Long[] ids);

    /**
     * 删除试卷操作日志信息
     *
     * @param id 试卷操作日志主键
     * @return 结果
     */
    public int deleteExamLogById(Long id);

    public ExamLog selectExamLogByExamId(String id);
    /**
     * 判断主监考和副监考是否领卷
     */
    public Boolean invigilatorGet(String id) ;

    public Boolean  invigilatorSubmit(String id);

    void teacherLeadsThePaper(Long id);

    void teacherHandThePaper(Long id, String selectList);

    Object countAll();

    void addReasonLog(Long id, String reason);
}
