package com.rzyk.notice.service.impl;

import java.util.List;

import com.rzyk.notice.domain.ProcessExam;
import com.rzyk.notice.mapper.ProcessExamMapper;
import com.rzyk.notice.service.IProcessExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流程实例关联试卷信息Service业务层处理
 * 
 * @author ccj
 * @date 2023-03-12
 */
@Service
public class ProcessExamServiceImpl implements IProcessExamService
{
    @Autowired
    private ProcessExamMapper processExamMapper;

    /**
     * 查询流程实例关联试卷信息
     * 
     * @param id 流程实例关联试卷信息主键
     * @return 流程实例关联试卷信息
     */
    @Override
    public ProcessExam selectProcessExamById(Long id)
    {
        return processExamMapper.selectProcessExamById(id);
    }

    /**
     * 查询流程实例关联试卷信息列表
     * 
     * @param processExam 流程实例关联试卷信息
     * @return 流程实例关联试卷信息
     */
    @Override
    public List<ProcessExam> selectProcessExamList(ProcessExam processExam)
    {
        return processExamMapper.selectProcessExamList(processExam);
    }

    /**
     * 新增流程实例关联试卷信息
     * 
     * @param processExam 流程实例关联试卷信息
     * @return 结果
     */
    @Override
    public int insertProcessExam(ProcessExam processExam)
    {
        return processExamMapper.insertProcessExam(processExam);
    }

    /**
     * 修改流程实例关联试卷信息
     * 
     * @param processExam 流程实例关联试卷信息
     * @return 结果
     */
    @Override
    public int updateProcessExam(ProcessExam processExam)
    {
        return processExamMapper.updateProcessExam(processExam);
    }

    /**
     * 批量删除流程实例关联试卷信息
     * 
     * @param ids 需要删除的流程实例关联试卷信息主键
     * @return 结果
     */
    @Override
    public int deleteProcessExamByIds(Long[] ids)
    {
        return processExamMapper.deleteProcessExamByIds(ids);
    }

    /**
     * 删除流程实例关联试卷信息信息
     * 
     * @param id 流程实例关联试卷信息主键
     * @return 结果
     */
    @Override
    public int deleteProcessExamById(Long id)
    {
        return processExamMapper.deleteProcessExamById(id);
    }
}
