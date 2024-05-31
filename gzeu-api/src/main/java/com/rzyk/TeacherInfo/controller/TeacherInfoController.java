package com.rzyk.TeacherInfo.controller;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.rzyk.Supervisor.domain.Supervisor;
import com.rzyk.Supervisor.service.ISupervisorService;
import com.rzyk.TExamData.domain.TExamData;
import com.rzyk.TExamData.service.ITExamDataService;
import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.TeacherInfo.service.ITeacherInfoService;
import com.rzyk.common.annotation.Log;
import com.rzyk.common.core.controller.BaseController;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.core.domain.entity.SysUser;
import com.rzyk.common.core.domain.model.LoginUser;
import com.rzyk.common.core.page.TableDataInfo;
import com.rzyk.common.enums.BusinessType;
import com.rzyk.common.exception.ServiceException;
import com.rzyk.common.utils.SecurityUtils;
import com.rzyk.common.utils.StringUtils;
import com.rzyk.common.utils.poi.ExcelUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 监考老师信息Controller
 *
 * @author ruoyi
 * @date 2022-12-25
 */
@RestController
@RequestMapping("/system/info")
public class TeacherInfoController extends BaseController {
    @Autowired
    private ITeacherInfoService teacherInfoService;

    @Autowired
    private ITExamDataService tExamDataService;

    @Autowired
    private ISupervisorService supervisorService;

    /**
     * 查询监考老师信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeacherInfo teacherInfo) {
        startPage();
        List<TeacherInfo> list = teacherInfoService.selectTeacherInfoList(teacherInfo);
        return getDataTable(list);
    }

    /**
     * 导出监考老师信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "监考老师信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TeacherInfo teacherInfo) {
        List<TeacherInfo> list = teacherInfoService.selectTeacherInfoList(teacherInfo);
        ExcelUtil<TeacherInfo> util = new ExcelUtil<TeacherInfo>(TeacherInfo.class);
        util.exportExcel(response, list, "监考老师信息数据");
    }

    /**
     * 获取监考老师信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(teacherInfoService.selectTeacherInfoById(id));
    }

    /**
     * 新增监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "监考老师信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeacherInfo teacherInfo) {
        return toAjax(teacherInfoService.insertTeacherInfo(teacherInfo));
    }

    /**
     * 修改监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "监考老师信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeacherInfo teacherInfo) {
        return toAjax(teacherInfoService.updateTeacherInfo(teacherInfo));
    }

    /**
     * 删除监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "监考老师信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(teacherInfoService.deleteTeacherInfoByIds(ids));
    }

    //    @PreAuthorize("@ss.hasPermi('')") todo 权限未定
    @Log(title = "监考老师当天监考信息")
    @GetMapping("/currdayInfo/{index}")
    public TableDataInfo getCurrDayInfo(@PathVariable String index) {
        List<TExamData> tExamDataListDb = new ArrayList<>();

        //获取当前登陆人信息
        SysUser user = SecurityUtils.getLoginUser().getUser();
        JSONObject jsonObject = JSONObject.parseObject(user.getOther());
        String name = null;
        if (ObjectUtils.isEmpty(jsonObject) && StringUtils.isBlank(jsonObject.getString("name"))) {
            name = "";
        } else {
            name = jsonObject.getString("name");
        }
        //创建当前查询得参数
        TExamData tExamDataParam = new TExamData();
        //设置老师
        tExamDataParam.setTeacher(name);
        //获取当前时间
        Date date = new Date();
        //时间格式化
        String dateNow = new SimpleDateFormat("YYYY-MM-dd").format(date);
        //设置时间
        tExamDataParam.setExamdate(dateNow);
        //用于封装考试信息id
        ArrayList<String> ids = new ArrayList<>();
        String status = null;
        //当index为3时查询已提交数据
        status = "0";
        //根据名字,角色和状态查询督导信息
        List<Supervisor> supervisorListTo3 = supervisorService.selectSupervisorList(
                new Supervisor().setCreatedBy(name).setStatus(status).setRole("1").setActiveIndex(2));
        for (Supervisor supervisor : supervisorListTo3) {
            ids.add(supervisor.getPaperNumber());
        }
        List<TExamData> tExamDataListDbByIdsTo3 = new ArrayList<>();
        if (!ids.isEmpty()) {
            tExamDataListDbByIdsTo3 = tExamDataService.selectTExamDataByIds(ids);
        }
        ids.clear();
        if (Integer.valueOf(index) == 3) {
            return getDataTable(tExamDataListDbByIdsTo3);
        }
        //根据名字,角色和状态查询督导信息
        List<Supervisor> supervisorListTo2 = supervisorService.selectSupervisorList(
                new Supervisor().setCreatedBy(name).setStatus(status).setRole("1").setActiveIndex(1));
        for (Supervisor supervisor : supervisorListTo2) {
            ids.add(supervisor.getPaperNumber());
        }
        List<TExamData> tExamDataListDbByIdsTo2 = new ArrayList<>();
        if (!ids.isEmpty()) {
            tExamDataListDbByIdsTo2 = tExamDataService.selectTExamDataByIds(ids);
        }

        //
        if (Integer.valueOf(index) == 1) {
            //查询当前登陆人当天得考试信息
            tExamDataListDb = tExamDataService.selectTExamDataList(tExamDataParam);
            tExamDataListDb.removeAll(tExamDataListDbByIdsTo2);
            tExamDataListDb.removeAll(tExamDataListDbByIdsTo3);
        } else if (Integer.valueOf(index) == 2) {
            tExamDataListDb = tExamDataListDbByIdsTo2;
        }
        return getDataTable(tExamDataListDb);
    }

    //    @PreAuthorize("@ss.hasPermi('')") todo 权限未定
    @Log(title = "监考老师当天监考信息")
    @GetMapping("/currexam")
    public AjaxResult getCurrExam() {
        //获取当前登陆人信息
        SysUser user = SecurityUtils.getLoginUser().getUser();
        JSONObject jsonObject = JSONObject.parseObject(user.getOther());
        String name = jsonObject.getString("name");
//        String  name = "刘益波";
        TExamData tExamDataParam = new TExamData();
        tExamDataParam.setTeacher(name);

        //查询当天考试信息
        tExamDataParam.setExamdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        List<TExamData> tExamDataListDB = tExamDataService.selectTExamDataList(tExamDataParam);
        if (tExamDataListDB.isEmpty()) {
            return AjaxResult.success();
        }
        String[] split = null;
        Date nextDate = null;


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        HashMap<String, Object> resultMap = new HashMap<>();
        for (TExamData tExamData : tExamDataListDB) {
            String examtime = tExamData.getExamtime();
            if (StringUtils.isBlank(examtime)) {
                split = null;
                continue;
            } else {
                split = examtime.split("~");
            }
            if (split != null) {
                try {
                    nextDate = simpleDateFormat.parse(tExamData.getExamdate() + "-" + split[0]);
                    resultMap.put("nextDate", simpleDateFormat.format(nextDate));
                    //计算时差
                    long timeDifference = nextDate.getTime() - new Date().getTime();
                    if (timeDifference > 0 && (!resultMap.containsKey("minTimeDifference") ||
                            timeDifference < Long.valueOf(resultMap.get("minTimeDifference").toString()))) {
                        resultMap.put("minTimeDifference", timeDifference);
                        resultMap.put("data", tExamData);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        //计算半小时的毫秒值
        Long halfHour = Long.valueOf(30 * 60 * 1000);
        if (Long.valueOf(resultMap.get("minTimeDifference").toString()) > halfHour) {
            resultMap.put("minTimeDifference", Long.valueOf(resultMap.get("minTimeDifference").toString()) - halfHour);
            resultMap.put("isGet", false);
        } else {
            resultMap.put("isGet", true);
        }
        //计算1个小时的毫秒值
        Long anHour = Long.valueOf(60 * 60 * 1000);
        //计算可领取试卷时间
        return AjaxResult.success(resultMap);
    }


    /**
     * 监考老师数据导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importdata")
    public AjaxResult importDataTeacher(MultipartFile file) {
        ExcelUtil<TeacherInfo> excelUtil = new ExcelUtil<TeacherInfo>(TeacherInfo.class);
        String message = "";
        try (InputStream inputStream = file.getInputStream()) {
            List<TeacherInfo> teacherInfos = excelUtil.importExcel(inputStream);
            message = teacherInfoService.importDate(teacherInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }

}
