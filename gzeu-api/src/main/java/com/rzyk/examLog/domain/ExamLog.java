package com.rzyk.examLog.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rzyk.common.annotation.Excel;
import com.rzyk.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 试卷操作日志对象 exam_log
 * 
 * @author ruoyi
 * @date 2023-06-07
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExamLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 试卷ID */
    @Excel(name = "试卷ID")
    private String examId;

    /** 主监考ID */
    @Excel(name = "主监考ID")
    private String chiefinvId;

    /** 副监考ID */
    @Excel(name = "副监考ID")
    private String assistantinvId;

    /** 主监考是否领取(Y/N) */
    @Excel(name = "主监考是否领取(Y/N)")
    private String chiefinvIsGet;

    /** 副监考是否领取(Y/N) */
    @Excel(name = "副监考是否领取(Y/N)")
    private String assistantinvIsGet;

    /** 授课老师领卷时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "授课老师领卷时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date invigilation;

    /** 授课老师最后一次提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "授课老师最后一次提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastSubmissionTime;

    /** 授课老师提交次数 */
    @Excel(name = "授课老师提交次数")
    private Long submitCount;

    /** 驳回时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "驳回时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rejectTime;

    /** 驳回次数 */
    @Excel(name = "驳回次数")
    private Long rejectCount;

    /** 监考老师交卷时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "监考老师交卷时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date invigilateSubmitTime;

    /** 副监考老师领取时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "副监考老师领取时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date assistantinvInvigilateFetchTime;

    /** 监考老师领取试卷(Y/N) */
    @Excel(name = "监考老师领取试卷(Y/N)")
    private String invigilatePaperStatus;

    /** 试卷状态 */
    @Excel(name = "试卷状态")
    private String examStatus;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 主监考老师领取时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "主监考老师领取时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date chiefinvInvigilateFetchTime;

    /** 主监考是否交卷(Y/N) */
    @Excel(name = "主监考是否交卷(Y/N)")
    private String chiefinvIsSubmit;

    /** 副监考是否交卷(Y/N) */
    @Excel(name = "副监考是否交卷(Y/N)")
    private String assistantinvIsSubmit;
    /** 材料提交 */
    @Excel(name = "材料提交")
    private String materialSubmission;
    /** 驳回状态 */
    @Excel(name = "驳回状态")
    private String rejectStatus; /** 驳回状态 */
    @Excel(name = "授课老师")
    private String teacherId;

}
