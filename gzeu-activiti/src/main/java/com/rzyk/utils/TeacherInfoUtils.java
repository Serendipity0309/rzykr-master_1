package com.rzyk.utils;

import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.TeacherInfo.service.ITeacherInfoService;
import com.rzyk.departmentHead.service.IDepartmentHeadService;
import com.rzyk.superintendent.service.ISuperintendentService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过监考老师表,督导表,系主任表获取老师信息
 */
@Component
public class TeacherInfoUtils {
    @Autowired
    private  ITeacherInfoService teacherInfoService;


    public  Object getUserInfoById(String id){
        Object teacherInfo = teacherInfoService.selectTeacherInfoById(id);

        if (ObjectUtils.isEmpty(teacherInfo)) {
            throw new RuntimeException("用户不存在");
        }
        return teacherInfo;
    }
}

