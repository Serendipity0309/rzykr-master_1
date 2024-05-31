package com.rzyk.notice.mapper;

import com.rzyk.notice.domain.ProcessExam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 流程实例关联试卷信息Mapper接口
 * 
 * @author ccj
 * @date 2023-03-12
 */
@Mapper
public interface ProcessExamMapper 
{
    /**
     * 查询流程实例关联试卷信息
     * 
     * @param id 流程实例关联试卷信息主键
     * @return 流程实例关联试卷信息
     */
    public ProcessExam selectProcessExamById(Long id);

    /**
     * 查询流程实例关联试卷信息列表
     * 
     * @param processExam 流程实例关联试卷信息
     * @return 流程实例关联试卷信息集合
     */
    public List<ProcessExam> selectProcessExamList(ProcessExam processExam);

    /**
     * 新增流程实例关联试卷信息
     * 
     * @param processExam 流程实例关联试卷信息
     * @return 结果
     */
    public int insertProcessExam(ProcessExam processExam);

    /**
     * 修改流程实例关联试卷信息
     * 
     * @param processExam 流程实例关联试卷信息
     * @return 结果
     */
    public int updateProcessExam(ProcessExam processExam);

    /**
     * 删除流程实例关联试卷信息
     * 
     * @param id 流程实例关联试卷信息主键
     * @return 结果
     */
    public int deleteProcessExamById(Long id);

    /**
     * 批量删除流程实例关联试卷信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessExamByIds(Long[] ids);
}
