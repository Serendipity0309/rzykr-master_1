package com.rzyk.controller;

import com.rzyk.common.core.controller.BaseController;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.core.page.TableDataInfo;
import com.rzyk.notice.domain.Notice;
import com.rzyk.service.ActivitiService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Transactional
@RequestMapping("/act")
@RestController
@Slf4j
public class ActController extends BaseController {


    @Autowired
    private ActivitiService activitiService;


    @GetMapping("/testAct")
    public AjaxResult testMyAct() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("开始检测");
        System.out.println("结果===>" + processEngine);
//        System.out.println("结果===>"+processEngine.getRuntimeService());
        return AjaxResult.success(processEngine);
    }


    /**
     * 流程部署
     *
     * @return
     */
    @GetMapping("/processDeploy")
    public AjaxResult processDeploy() {
        return activitiService.processDeploy();
    }

    /**
     * 通过在当前登录对象查询流程部署信息
     *
     * @param query 1为系、2督导
     */
    @GetMapping("/QueryProcess")
    public TableDataInfo QueryProcessByLoginUser(Long id, String query) {
        return getDataTable(activitiService.QueryProcessByLoginUser(id, query));
    }

    /**
     * 查询部署列表2
     */
//    @GetMapping("/QueryProcess")
    public TableDataInfo QueryProcess() {
        return getDataTable(activitiService.QueryProcess());
    }


    /**
     * 领卷
     * 流程id与试卷绑定并开启流程执行一次
     */
    @GetMapping("/bingingandstart/{id}")
    public AjaxResult texamDataBindingId(@PathVariable String id) {
        return activitiService.texamDataBindingId(id);
    }

    /**
     * 流程的拾取
     */
    @GetMapping("/fetchTask")
    public AjaxResult processIndividualTask(String processId) {
        return activitiService.processIndividualTask(processId);
    }

    /**
     * 交卷
     */
    @GetMapping("/handExamination")
    public AjaxResult handExamination(Long id, Integer isHand) {
        return activitiService.handExamination(id, isHand);
    }

    /**
     * 完成个人任务
     */
    @GetMapping("/finishIndividualTask")
    public AjaxResult finishProcessIndividualTask(Long id, Integer isPass) {

        return activitiService.finishProcessIndividualTask(id, isPass);
    }

    /**
     * 查询当前登陆人权限等级 0老师 1系主任 2 督导
     */
    @GetMapping("/getPermissionLev")
    public AjaxResult getPermission() {
        return activitiService.getPermission();
    }

    /**
     * 通过试卷id获取当前试卷审批信息
     *
     * @param id
     * @return
     */

    @GetMapping("/personalHistory")
    public TableDataInfo personalHistory(Long id) {
        return getDataTable(activitiService.personalHistory(id));
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @GetMapping("/userinfo")
    public AjaxResult getCurrTeacherInto() {
        return activitiService.getCurrTeacherInto();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @GetMapping("/permission")
    public AjaxResult getpermission() {
        return activitiService.getpermission();
    }

    /**
     * @param index 0 是交卷记录 1是领卷记录 2是系主任审批记录 3是督导审批记录
     * @return
     */
    @GetMapping("/getinfobymenu")
    public AjaxResult getinfobymenu(String  index) {

        return activitiService.getinfobymenu(index);
    }

    /**
     * 查询通知公告列表
     */

    @GetMapping("/prompt/{id}")
    public AjaxResult list(Notice notice, @PathVariable String id) {
        startPage();
        return activitiService.list(notice, id);
    }

    /**
     * 封装试卷管理的数据
     *
     * @param id
     * @return
     */
    @GetMapping("/manageList/{id}")
    public AjaxResult getListForManage(@PathVariable Long id) {
        return activitiService.getListForManage(id);
    }

    /**
     * 获取考试信息
     *
     * @return
     */
    @GetMapping("/getExamDataGroupByDate")
    public AjaxResult getExamDataGroupByDate() {

        return activitiService.getExamDataGroupByDate();
    }

    /**
     * 获取材料信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getDocumentList")
    public AjaxResult getDocumentList(Long id) {
        return activitiService.getDocumentList(id);
    }

    /**
     * 授课老师领卷
     */
    @GetMapping("/teacherLeadsThePaper")
    public AjaxResult teacherLeadsThePaper(Long id) {
        return activitiService.teacherLeadsThePaper(id);
    }

    /**
     * 授课老师交卷
     */
    @GetMapping("/teacherHandThePaper")
    public AjaxResult teacherHandThePaper(String id, String selectList) {
        return activitiService.teacherHandThePaper(id, selectList);
    }
    /**
     * 督导驳回
     */
    @GetMapping("/supervisorReject")
    public AjaxResult supervisorReject(String reason,String id){
        return activitiService.supervisorReject(reason,id);
    }
}