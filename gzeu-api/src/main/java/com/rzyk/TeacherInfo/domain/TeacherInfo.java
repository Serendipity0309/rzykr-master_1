package com.rzyk.TeacherInfo.domain;

import com.rzyk.common.annotation.Excel;
import com.rzyk.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 监考老师信息对象 teacher_info
 * 
 * @author ruoyi
 * @date 2022-12-25
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 企业微信用户id */
    @Excel(name = "企业微信用户id")
    private String userId;

    /** 父id */
    @Excel(name = "父id")
    private String parentId;

    /** 角色(0 老师 1系主任 2督导) */
    @Excel(name = "角色(0 老师 1系主任 2督导)")
    private String position;

    /** 工号 */
    @Excel(name = "工号")
    private String jobId;

    /** 人事部主键 */
    @Excel(name = "人事部主键")
    private String guikey;

    /** 部门 */
    @Excel(name = "部门")
    private String codeitemdesc;

    /** 0是导入;1是自建 */
    @Excel(name = "0是导入;1是自建")
    private Long type;
    /**
     * 学院id
     */
    private Long codeitemdescId;


}
