package com.rzyk.Supervisor.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.rzyk.common.annotation.Excel;
import com.rzyk.common.core.domain.BaseEntity;
import org.springframework.cache.annotation.Caching;

/**
 * 督导对象 supervisor
 *
 * @author ruoyi
 * @date 2022-12-25
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Supervisor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 试卷编号
     */
    @Excel(name = "试卷编号")
    private String paperNumber;

    /**
     * 状态(0 未处理 1已处理 )
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 角色(1 老师,2 系主任 3督导)
     */
    @Excel(name = "角色")
    private String role;
    /**
     * 流程状态(1是领卷,2是交卷,3是系主任审批,4是督导审批)
     */
    @Excel(name = "流程状态")
    private Integer activeIndex;

    /**
     * 当前提交人
     */
    @Excel(name = "当前提交人")
    private String currentSubmitterName;

    /**
     * 当前提交人userId
     */
    @Excel(name = "当前提交人userId")
    private String submitterUserId;

    /**
     * 微信userId
     */
    @Excel(name = "微信userId")
    private String weichatId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

//    public void setId(Long id)
//    {
//        this.id = id;
//    }
//
//    public Long getId()
//    {
//        return id;
//    }
//    public void setPaperNumber(String paperNumber)
//    {
//        this.paperNumber = paperNumber;
//    }
//
//    public String getPaperNumber()
//    {
//        return paperNumber;
//    }
//    public void setStatus(String status)
//    {
//        this.status = status;
//    }
//
//    public String getStatus()
//    {
//        return status;
//    }
//    public void setCurrentSubmitterName(String currentSubmitterName)
//    {
//        this.currentSubmitterName = currentSubmitterName;
//    }
//
//    public String getCurrentSubmitterName()
//    {
//        return currentSubmitterName;
//    }
//    public void setSubmitterUserId(String submitterUserId)
//    {
//        this.submitterUserId = submitterUserId;
//    }
//
//    public String getSubmitterUserId()
//    {
//        return submitterUserId;
//    }
//    public void setWeichatId(String weichatId)
//    {
//        this.weichatId = weichatId;
//    }
//
//    public String getWeichatId()
//    {
//        return weichatId;
//    }
//    public void setCreatedBy(String createdBy)
//    {
//        this.createdBy = createdBy;
//    }
//
//    public String getCreatedBy()
//    {
//        return createdBy;
//    }
//    public void setCreatedTime(Date createdTime)
//    {
//        this.createdTime = createdTime;
//    }
//
//    public Date getCreatedTime()
//    {
//        return createdTime;
//    }
//    public void setUpdatedBy(String updatedBy)
//    {
//        this.updatedBy = updatedBy;
//    }
//
//    public String getUpdatedBy()
//    {
//        return updatedBy;
//    }
//    public void setUpdatedTime(Date updatedTime)
//    {
//        this.updatedTime = updatedTime;
//    }
//
//    public Date getUpdatedTime()
//    {
//        return updatedTime;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("id", getId())
//            .append("paperNumber", getPaperNumber())
//            .append("status", getStatus())
//            .append("currentSubmitterName", getCurrentSubmitterName())
//            .append("submitterUserId", getSubmitterUserId())
//            .append("weichatId", getWeichatId())
//            .append("createdBy", getCreatedBy())
//            .append("createdTime", getCreatedTime())
//            .append("updatedBy", getUpdatedBy())
//            .append("updatedTime", getUpdatedTime())
//            .toString();
//    }
}
