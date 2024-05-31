package com.rzyk.Supervisor.controller;

import java.io.IOException;
import java.security.Security;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.rzyk.TExamData.domain.TExamData;
import com.rzyk.TExamData.service.ITExamDataService;
import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.TeacherInfo.service.ITeacherInfoService;
import com.rzyk.common.core.domain.entity.SysUser;
import com.rzyk.common.core.domain.model.LoginUser;
import com.rzyk.common.exception.ServiceException;
import com.rzyk.common.utils.ExceptionUtil;
import com.rzyk.common.utils.SecurityUtils;
import com.rzyk.common.utils.ServletUtils;
import com.rzyk.common.utils.StringUtils;
import com.rzyk.common.utils.poi.ExcelUtil;
import com.rzyk.Supervisor.domain.Supervisor;
import com.rzyk.Supervisor.service.ISupervisorService;
import com.rzyk.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rzyk.common.annotation.Log;
import com.rzyk.common.core.controller.BaseController;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.enums.BusinessType;
import com.rzyk.common.core.page.TableDataInfo;

/**
 * 督导Controller
 *
 * @author ccj
 * @date 2022-12-25
 */
@RestController
@RequestMapping("/system/supervisor")
public class SupervisorController extends BaseController {
    @Autowired
    private ISupervisorService supervisorService;

    @Autowired
    private ITeacherInfoService teacherInfoService;
    @Autowired
    private ITExamDataService tExamDataService;

    /**
     * 查询督导列表
     */
//    @PreAuthorize("@ss.hasPermi('system:supervisor:list')")
    @GetMapping("/list")
    public TableDataInfo list(Supervisor supervisor) {
        startPage();
        List<Supervisor> list = supervisorService.selectSupervisorList(supervisor);
        return getDataTable(list);
    }

    //    @PreAuthorize("@ss.hasPermi('system:supervisor:list')")
/*    @GetMapping("/SupervisorListByUser")
    public TableDataInfo getSupervisorListByUser(Supervisor supervisor) {
        startPage();
        supervisor.setStatus("0").setActiveIndex(2);
        String other = SecurityUtils.getLoginUser().getUser().getOther();
        JSONObject jsonObject = JSONObject.parseObject(other);
        jsonObject.getString()
        sysUserService.selectUserList()
        List<Supervisor> list = supervisorService.selectSupervisorList();
        return getDataTable(list);
    }*/

    /**
     * 导出督导列表
     */
    @PreAuthorize("@ss.hasPermi('system:supervisor:export')")
    @Log(title = "督导", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Supervisor supervisor) {
        List<Supervisor> list = supervisorService.selectSupervisorList(supervisor);
        ExcelUtil<Supervisor> util = new ExcelUtil<Supervisor>(Supervisor.class);
        util.exportExcel(response, list, "督导数据");
    }

    /**
     * 获取督导详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:supervisor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(supervisorService.selectSupervisorById(id));
    }

    /**
     *
     */

//    @PreAuthorize("@ss.hasPermi('system:supervisor:add')")
    @Log(title = "督导", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Supervisor supervisor) {
        String username = SecurityUtils.getUsername();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String other = loginUser.getUser().getOther();
        JSONObject userJson = JSONObject.parseObject(other);
        Long sysUserID = loginUser.getUser().getUserId();
        String role = "0";
        if (sysUserID == 100) {
            role = "1";
        } else if (sysUserID == 101) {
            role = "2";
        } else {
            role = "3";
        }
        if (supervisor.getActiveIndex() > 1) {

            List<Supervisor> supervisorList = supervisorService.selectSupervisorList(
                    new Supervisor()
                            .setStatus("0")
                            .setActiveIndex(supervisor.getActiveIndex() - 1)
                            .setPaperNumber(supervisor.getPaperNumber()));
            //修改
            if (!supervisorList.isEmpty()) {
                supervisorService.updateSupervisor(
                        supervisorList.get(0)
                                .setStatus("1")
                                .setUpdatedBy(userJson.getString("name"))
                                .setUpdatedTime(new Date())
                                .setCurrentSubmitterName(userJson.getString("name"))
                                .setSubmitterUserId(userJson.getString("id")));
            }

        }

        //添加
        supervisor.setWeichatId(username)
                .setCurrentSubmitterName(userJson.getString("name"))
                .setRole(role)
                .setWeichatId(userJson.getString("userId"))
                .setCreatedBy(userJson.getString("name"))
                .setUpdatedBy(userJson.getString("name"))
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setSubmitterUserId(userJson.getString("id"))
                .setStatus("0");

        return toAjax(supervisorService.insertSupervisor(supervisor));
    }

    /**
     * 修改督导
     */
    @PreAuthorize("@ss.hasPermi('system:supervisor:edit')")
    @Log(title = "督导", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Supervisor supervisor) {

        return toAjax(supervisorService.updateSupervisor(supervisor));
    }

    /**
     * 删除督导
     */
    @PreAuthorize("@ss.hasPermi('system:supervisor:remove')")
    @Log(title = "督导", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(supervisorService.deleteSupervisorByIds(ids));
    }

    /***
     * 根据用户查询督导信息
     */
//todo   @PreAuthorize()  权限未定义
//    @PreAuthorize("@ss.hasAnyRoles()")
    @Log(title = "查询督导信息", businessType = BusinessType.OTHER)
    @GetMapping("/selectSupervisorByUser")
    public TableDataInfo selectSupervisorByUser(Supervisor supervisor) {
        startPage();
        //如果存在id 返回该条id 相关的数据
        
        Long examId = supervisor.getId();
        if (examId !=null){
            List<Supervisor> supervisorListDB = supervisorService.selectSupervisorList(
                    new Supervisor().setPaperNumber(String.valueOf(examId)));
            //判断数据库是否存在该条数据
            if (supervisorListDB.isEmpty()){
                throw new ServiceException("试卷尚未领取!");
            }
            //已经做降序查询 ,第一条就是最大
            Supervisor supervisorDB = supervisorListDB.get(0);
            if (supervisorDB.getActiveIndex()<2){
                throw new ServiceException("试卷没到这一步,请查看试卷流转");
            }
            TExamData tExamData = tExamDataService.selectTExamDataById(examId);
            supervisorDB.setPaperNumber(tExamData.getCoursenumber());
            ArrayList<Supervisor> supervisors = new ArrayList<>();
            supervisors.add(supervisorDB);
            return getDataTable(supervisors);
        }
        String other = SecurityUtils.getLoginUser().getUser().getOther();
        JSONObject jsonObject = JSONObject.parseObject(other);

        Long id = jsonObject.getLong("id");

        List<Supervisor> supervisorDbList = supervisorService.selectSupervisorByUser(0l, 2l, id);
//        AjaxResult success = AjaxResult.success(supervisorDbList);
        return getDataTable(supervisorDbList);
    }

    /**
     * @param supervisor
     * @param isAllow
     * @return
     */

    @PostMapping("/handAllow/{isAllow}")
    public AjaxResult handAllow(@RequestBody Supervisor supervisor, @PathVariable("isAllow") String isAllow) {
        //
        String paperNumber = supervisor.getPaperNumber();
        Integer activeIndex = supervisor.getActiveIndex();
        Long id = supervisor.getId();
        if (isAllow.equals("0")) {
            List<Supervisor> supervisorListDB = supervisorService.selectSupervisorList(new Supervisor().setActiveIndex(activeIndex - 1).setStatus("1").setPaperNumber(paperNumber));
            //修改上一节点数据
            supervisorService.updateSupervisor(supervisorListDB.get(0).setStatus("0"));
            //删除本条数据
            supervisorService.deleteSupervisorById(id);
            //todo 通过企业微信向上一级提交人员发送提示驳回信息
            return AjaxResult.success();
        } else if (isAllow.equals("1")) {
            return add(supervisor.setActiveIndex(activeIndex + 1));
        }
        return AjaxResult.error("系统错误");
    }

    /**
     * 数据展示
     */
    @GetMapping("/showData")
    public AjaxResult getShowDataById() {
        String id = ServletUtils.getParameter("id");
        //通过id获取当前进度
        List<Supervisor> supervisorList = supervisorService.selectSupervisorList(new Supervisor().setPaperNumber(id));
        Integer actIndex = -1;
        //定义返回数据
        Map<String, Object> resultMap = new HashMap<>();
        if (!supervisorList.isEmpty()) {
            //获取最大actIndex
            for (Supervisor supervisor : supervisorList) {
                actIndex = actIndex > supervisor.getActiveIndex() ? actIndex : supervisor.getActiveIndex();
            }
            resultMap.put("isOper", actIndex + 1);
            resultMap.put("actIndex", actIndex + 1);
        } else {
            resultMap.put("isOper", 2);
            resultMap.put("actIndex", 1);
        }
        //通过id查询当前课程信息
        TExamData tExamData = tExamDataService.selectTExamDataById(Long.valueOf(id));
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(tExamData);
        resultMap.put("list", objects);
        return AjaxResult.success(resultMap);
    }
    /**
     * 通过id判断当前试卷进度
     */
//    public AjaxResult handExam
}
