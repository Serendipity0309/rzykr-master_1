package com.rzyk.TExamData.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.rzyk.common.annotation.Excel;
import com.rzyk.common.core.domain.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 考试安排数据对象 t_exam_data
 * 
 * @author ruoyi
 * @date 2022-11-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TExamData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一确定编号，自增 */
    private Long id;

    /** 课程编号 */
    @Excel(name = "课程编码")
    private String coursenumber;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String coursename;

    /** 授课教师 */
    @Excel(name = "授课教师")
    private String teacher;

    /** 教学班名称 */
    @Excel(name = "教学班名称")
    private String teachingclass;

    /** 学生班级 */
    @Excel(name = "学生班级")
    private String studentclass;

    /** 人数 */
    @Excel(name = "人数")
    private Integer num;

    /** 考试地点 */
    @Excel(name = "考试地点")
    private String place;

    /** 考场总人数 */
    @Excel(name = "考场总人数")
    private Integer totalnum;

    /** 考试时间 */
    @Excel(name = "考试时间")
    private String examtime;

    /** 考试日期 */
    @Excel(name = "考试日期")
    private String examdate;

    /** 主考人员 */
    @Excel(name = "主考人员")
    private String examiners;

    /** 主监考 */
    @Excel(name = "主监考")
    private String chiefinv;

    /** 副监考 */
    @Excel(name = "副监考")
    private String assistantinv;

    /** 试卷编号 */
    @Excel(name = "试卷编号")
    private String papersnum;

    /** 开课校区 */
    @Excel(name = "开课校区")
    private String classescampus;

    /** 考试所在校区 */
    @Excel(name = "考试所在校区")
    private String examcampus;

    /** 开课院系 */
    @Excel(name = "开课院系")
    private String classessdept;

    /** 上课院系 */
    @Excel(name = "上课院系")
    private String toteachsdept;

    /** 所属校区 */
    @Excel(name = "所属校区")
    private String campus;

    /** 考场容量 */
    @Excel(name = "考场容量")
    private Integer capacity;

    /** 考试节次 */
    @Excel(name = "考试节次")
    private String examsection;

    /** 所属学院 */
    @Excel(name = "所属学院")
    private String college;

    /** 所属学期 */
    @Excel(name = "所属学期")
    private String semester;

    /** 上传日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploaddate;

    /** 分组名 */
    @Excel(name = "分组名")
    private String groupname;

    /** 打印页面编号 */
    @Excel(name = "打印页面编号")
    private String printnum;

    /** 打印批次 */
    @Excel(name = "打印批次")
    private Long batch;


    private Integer status;
 /*   public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCoursenumber(String coursenumber) 
    {
        this.coursenumber = coursenumber;
    }

    public String getCoursenumber() 
    {
        return coursenumber;
    }
    public void setCoursename(String coursename) 
    {
        this.coursename = coursename;
    }

    public String getCoursename() 
    {
        return coursename;
    }
    public void setTeacher(String teacher) 
    {
        this.teacher = teacher;
    }

    public String getTeacher() 
    {
        return teacher;
    }
    public void setTeachingclass(String teachingclass) 
    {
        this.teachingclass = teachingclass;
    }

    public String getTeachingclass() 
    {
        return teachingclass;
    }
    public void setStudentclass(String studentclass) 
    {
        this.studentclass = studentclass;
    }

    public String getStudentclass() 
    {
        return studentclass;
    }
    public void setNum(Integer num) 
    {
        this.num = num;
    }

    public Integer getNum() 
    {
        return num;
    }
    public void setPlace(String place) 
    {
        this.place = place;
    }

    public String getPlace() 
    {
        return place;
    }
    public void setTotalnum(Integer totalnum) 
    {
        this.totalnum = totalnum;
    }

    public Integer getTotalnum() 
    {
        return totalnum;
    }
    public void setExamtime(String examtime) 
    {
        this.examtime = examtime;
    }

    public String getExamtime() 
    {
        return examtime;
    }
    public void setExamdate(String examdate) 
    {
        this.examdate = examdate;
    }

    public String getExamdate() 
    {
        return examdate;
    }
    public void setExaminers(String examiners) 
    {
        this.examiners = examiners;
    }

    public String getExaminers() 
    {
        return examiners;
    }
    public void setChiefinv(String chiefinv) 
    {
        this.chiefinv = chiefinv;
    }

    public String getChiefinv() 
    {
        return chiefinv;
    }
    public void setAssistantinv(String assistantinv) 
    {
        this.assistantinv = assistantinv;
    }

    public String getAssistantinv() 
    {
        return assistantinv;
    }
    public void setPapersnum(String papersnum) 
    {
        this.papersnum = papersnum;
    }

    public String getPapersnum() 
    {
        return papersnum;
    }
    public void setClassescampus(String classescampus) 
    {
        this.classescampus = classescampus;
    }

    public String getClassescampus() 
    {
        return classescampus;
    }
    public void setExamcampus(String examcampus) 
    {
        this.examcampus = examcampus;
    }

    public String getExamcampus() 
    {
        return examcampus;
    }
    public void setClassessdept(String classessdept) 
    {
        this.classessdept = classessdept;
    }

    public String getClassessdept() 
    {
        return classessdept;
    }
    public void setToteachsdept(String toteachsdept) 
    {
        this.toteachsdept = toteachsdept;
    }

    public String getToteachsdept() 
    {
        return toteachsdept;
    }
    public void setCampus(String campus) 
    {
        this.campus = campus;
    }

    public String getCampus() 
    {
        return campus;
    }
    public void setCapacity(Integer capacity) 
    {
        this.capacity = capacity;
    }

    public Integer getCapacity() 
    {
        return capacity;
    }
    public void setExamsection(String examsection) 
    {
        this.examsection = examsection;
    }

    public String getExamsection() 
    {
        return examsection;
    }
    public void setCollege(String college) 
    {
        this.college = college;
    }

    public String getCollege() 
    {
        return college;
    }
    public void setSemester(String semester) 
    {
        this.semester = semester;
    }

    public String getSemester() 
    {
        return semester;
    }
    public void setUploaddate(Date uploaddate) 
    {
        this.uploaddate = uploaddate;
    }

    public Date getUploaddate() 
    {
        return uploaddate;
    }
    public void setGroupname(String groupname) 
    {
        this.groupname = groupname;
    }

    public String getGroupname() 
    {
        return groupname;
    }
    public void setPrintnum(String printnum) 
    {
        this.printnum = printnum;
    }

    public String getPrintnum() 
    {
        return printnum;
    }
    public void setBatch(Long batch) 
    {
        this.batch = batch;
    }

    public Long getBatch() 
    {
        return batch;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("coursenumber", getCoursenumber())
            .append("coursename", getCoursename())
            .append("teacher", getTeacher())
            .append("teachingclass", getTeachingclass())
            .append("studentclass", getStudentclass())
            .append("num", getNum())
            .append("place", getPlace())
            .append("totalnum", getTotalnum())
            .append("examtime", getExamtime())
            .append("examdate", getExamdate())
            .append("examiners", getExaminers())
            .append("chiefinv", getChiefinv())
            .append("assistantinv", getAssistantinv())
            .append("papersnum", getPapersnum())
            .append("classescampus", getClassescampus())
            .append("examcampus", getExamcampus())
            .append("classessdept", getClassessdept())
            .append("toteachsdept", getToteachsdept())
            .append("campus", getCampus())
            .append("capacity", getCapacity())
            .append("examsection", getExamsection())
            .append("college", getCollege())
            .append("semester", getSemester())
            .append("uploaddate", getUploaddate())
            .append("groupname", getGroupname())
            .append("printnum", getPrintnum())
            .append("batch", getBatch())
            .toString();
    }*/
}
