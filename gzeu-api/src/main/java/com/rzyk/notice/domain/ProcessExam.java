package com.rzyk.notice.domain;

import com.rzyk.common.annotation.Excel;
import com.rzyk.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 流程实例关联试卷信息对象 process_exam
 * 
 * @author ccj
 * @date 2023-03-13
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProcessExam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 流程id */
    @Excel(name = "流程id")
    private String processId;

    /** 考试信息id */
    @Excel(name = "考试信息id")
    private Long examdataId;

    @Excel(name ="用户信息")
    private String UserId;
    @Excel(name ="学院id")
    private Long codeitemdesc;
}
