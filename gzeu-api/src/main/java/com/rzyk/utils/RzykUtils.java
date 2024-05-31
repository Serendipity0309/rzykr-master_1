package com.rzyk.utils;


import com.alibaba.fastjson2.JSONObject;
import com.rzyk.TeacherInfo.service.ITeacherInfoService;
import com.rzyk.common.utils.SecurityUtils;
import com.rzyk.common.utils.StringUtils;
import com.rzyk.departmentHead.service.IDepartmentHeadService;
import com.rzyk.superintendent.service.ISuperintendentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class RzykUtils {
    @Autowired
    private ITeacherInfoService teacherInfoService;


    public static JSONObject getLoginUser(){
        String other = SecurityUtils.getLoginUser().getUser().getOther();
        log.info("获取用户：{}",other);
        return JSONObject.parseObject(other);
    }
    public  Object getUserInfoById(String id){
        Object teacherInfo = teacherInfoService.selectTeacherInfoById(id);
        if (ObjectUtils.isEmpty(teacherInfo)){
            throw new RuntimeException("用户不存在");
        }
        return teacherInfo;
    }

}
