package com.rzyk.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.rzyk.TExamData.domain.TExamData;
import com.rzyk.TExamData.mapper.TExamDataMapper;
import com.rzyk.TExamData.service.ITExamDataService;
import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.TeacherInfo.service.ITeacherInfoService;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.exception.ServiceException;
import com.rzyk.common.utils.SecurityUtils;
import com.rzyk.common.utils.StringUtils;
import com.rzyk.departmentHead.domain.DepartmentHead;
import com.rzyk.departmentHead.service.IDepartmentHeadService;
import com.rzyk.document.domain.Document;
import com.rzyk.document.service.IDocumentService;
import com.rzyk.domain.ActivitiObVo;
import com.rzyk.examLog.domain.ExamLog;
import com.rzyk.examLog.service.IExamLogService;
import com.rzyk.notice.domain.Notice;
import com.rzyk.notice.domain.ProcessExam;
import com.rzyk.notice.service.INoticeService;
import com.rzyk.notice.service.IProcessExamService;
import com.rzyk.schoolDivision.domain.SchoolDivision;
import com.rzyk.schoolDivision.service.ISchoolDivisionService;
import com.rzyk.service.ActivitiService;
import com.rzyk.superintendent.domain.Superintendent;
import com.rzyk.superintendent.service.ISuperintendentService;
import com.rzyk.utils.RzykUtils;
import com.rzyk.utils.TeacherInfoUtils;
import com.rzyk.utils.WeChatUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Service
public class ActivitiServiceImpl implements ActivitiService {
    @Resource
    private TExamDataMapper tExamDataMapper;
    @Autowired
    private IProcessExamService processExamService;
    @Autowired
    private IDepartmentHeadService departmentHeadService;
    @Autowired
    private ITeacherInfoService teacherInfoService;

    @Autowired
    private ITExamDataService itExamDataService;

    @Autowired
    private TeacherInfoUtils teacherInfoUtils;
    @Autowired
    private ISuperintendentService superintendentService;

    @Autowired
    private WeChatUtils weChatUtils;

    @Autowired
    private ISchoolDivisionService schoolDivisionService;

    @Autowired
    private IExamLogService examLogService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private INoticeService noticeService;

    @Override
    public void updateTexamDataStatus(String id, Integer status) {
        tExamDataMapper.updateTExamData(new TExamData().setId(Long.valueOf(id)).setStatus(status));
    }



    /**
     * 流程部署
     *
     * @return
     */
    public AjaxResult processDeploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/examination-actV4.bpmn20.xml")
                .name("examination-actV4")
                .key("examination-actV4")
                .deploy();
        HashMap<String, Object> result = new HashMap<>();
        result.put("部署ID", deploy.getId());
        result.put("部署名称", deploy.getName());
        log.info("result===>{}", result);
        return AjaxResult.success(result);
    }

    /**
     * 通过在当前登录对象查询流程部署信息
     *
     * @param query 1为系、2督导
     * @return
     */
    public List<? extends Object> QueryProcessByLoginUser(Long id, String query) {
        JSONObject loginUser = RzykUtils.getLoginUser();
        String codeitemdesc = loginUser.getString("codeitemdescId");
        List<ProcessExam> processExams = processExamService.selectProcessExamList(
                new ProcessExam()
                        .setCodeitemdesc(Long.valueOf(codeitemdesc))
                        .setExamdataId(id));
        //无数据
        if (processExams.isEmpty()) {
            return processExams;
        }
        LinkedList<String> processInsIds = new LinkedList<>();
        for (ProcessExam processExam : processExams) {
            processInsIds.add(processExam.getProcessId());
        }
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery()
                .processInstanceIdIn(processInsIds)
                .list();
        LinkedList<Object> resultList = new LinkedList<>();
        for (Task task : list) {

            if (StringUtils.equalsAny(task.getTaskDefinitionKey(), "_2", "_3", "_4")) {
                continue;
            }
            if (query.equals("2") && task.getTaskDefinitionKey().equals("_5")) {
                continue;
            }
            if (query.equals("1") && task.getTaskDefinitionKey().equals("_6")) {
                continue;
            }
            List<ProcessExam> processExamDB = processExamService.selectProcessExamList(new ProcessExam().setProcessId(task.getProcessInstanceId()));
            HashMap<String, Object> resultMap = new HashMap<>();
            if (ObjectUtils.isEmpty(processExams) && processExams.isEmpty()) {
                continue;
            }
            //查询交卷人信息
            ProcessExam processExam = processExamDB.get(0);
            String userId = processExam.getUserId();
            Object userInfoById = teacherInfoUtils.getUserInfoById(userId);
            if (ObjectUtils.isEmpty(userInfoById)) {
                userInfoById = new TeacherInfo();
            }

            resultMap.put("teacherInfo", userInfoById);
            //查询试卷信息
            TExamData tExamData = itExamDataService.selectTExamDataById(processExam.getExamdataId());
            if (ObjectUtils.isEmpty(tExamData)) {
                tExamData = new TExamData();
            }
            resultMap.put("examData", tExamData);

            resultMap.put("processId", processExam.getProcessId());
            resultMap.put("processName", task.getName());
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 查询部署列表2
     *
     * @return
     */

    public ArrayList<Map> QueryProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        List<ProcessDefinition> examinations = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey("examination")
//                .list();
        List<Execution> examinations = runtimeService
                .createExecutionQuery()
                .processDefinitionKey("examination-actV4")
                .onlyProcessInstanceExecutions()
                .list();
        ArrayList<Map> resultList = new ArrayList<>();
        for (Execution deployment : examinations) {

            List<ProcessExam> processExams = processExamService.selectProcessExamList(new ProcessExam().setProcessId(deployment.getProcessInstanceId()));
            HashMap<String, Object> resultMap = new HashMap<>();
            if (ObjectUtils.isEmpty(processExams) && processExams.isEmpty()) {
                continue;
            }
            //查询交卷人信息
            ProcessExam processExam = processExams.get(0);
            String userId = processExam.getUserId();
            if (ObjectUtils.isEmpty(userId)) {
                continue;
            }
            TeacherInfo teacherInfo = teacherInfoService.selectTeacherInfoById(userId);
            if (ObjectUtils.isEmpty(teacherInfo)) {
                continue;
            }
            resultMap.put("teacherInfo", teacherInfo);
            //查询试卷信息
            TExamData tExamData = itExamDataService.selectTExamDataById(processExam.getExamdataId());
            resultMap.put("examData", tExamData);
            resultMap.put("processId", processExam.getProcessId());
            resultList.add(resultMap);
        }
        return resultList;
    }


    /**
     * 领卷
     * 流程id与试卷绑定并开启流程执行一次
     */
    public AjaxResult texamDataBindingId(String id) {
        log.info("{}调用了{}，参数：{}", new Date(), "texamDataBindingId", id);
        final Boolean handle = examLogService.invigilatorGet(id);
        AjaxResult.success();
        //1.创建对象封装对象
        ActivitiObVo activitiObVo = new ActivitiObVo();
        //2.设置考试信息ID
        activitiObVo.setTexamId(id);
        //3.获取用户ID
        String other = SecurityUtils.getLoginUser().getUser().getOther();
        JSONObject jsonObject = JSON.parseObject(other);
        String userID = jsonObject.getString("id");
        String codeitemdesc = jsonObject.getString("codeitemdescId");
        activitiObVo.setUserId(userID);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HashMap<String, Object> actVarMap = new HashMap<>();
        actVarMap.put("OB", activitiObVo);
        //通过试卷id获取流程实例id
        ProcessExam processExamVar = new ProcessExam();
        processExamVar.setExamdataId(Long.valueOf(id));
        List<ProcessExam> processExamList = processExamService.selectProcessExamList(processExamVar);
        if (!processExamList.isEmpty()) throw new ServiceException("流程已经开启过了");
        //开启流程
        ProcessInstance examination = runtimeService.startProcessInstanceByKey("examination-actV4", actVarMap);
        //修改考试信息状态
        Integer status = 1;
        processExamService.insertProcessExam(
                new ProcessExam()
                        .setProcessId(examination.getProcessInstanceId())
                        .setExamdataId(Long.valueOf(id))
                        .setUserId(userID)
                        .setCodeitemdesc(Long.valueOf(codeitemdesc)));
        this.updateTexamDataStatus(id, status);
        finishProcessIndividualTask(Long.valueOf(id), 1);

        return AjaxResult.success(examination.getProcessInstanceId());
    }


    /**
     * 流程的拾取
     */

    public AjaxResult processIndividualTask(String processId) {
        System.out.println(processId);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId(processId)
                .singleResult();
        //查询当前登陆人
        JSONObject user_other = JSONObject.parseObject(SecurityUtils.getLoginUser().getUser().getOther());
        String userId = user_other.getString("userId");
        taskService.claim(task.getId(), userId);
        return AjaxResult.success();
    }

    /**
     * 交卷
     */
    public AjaxResult handExamination(Long id, Integer isHand) {
        log.info("{}调用了{}，参数：{},{}", new Date(), "handExamination", id, isHand);
        examLogService.invigilatorSubmit(String.valueOf(id));
        //通过试卷id获取流程实例id
        ProcessExam processExamVar = new ProcessExam();
        processExamVar.setExamdataId(id);

        List<ProcessExam> processExamList = processExamService.selectProcessExamList(processExamVar);
        //修改试卷交卷状态

        Integer status = ObjectUtils.isEmpty(isHand) ? 2 : 1;

        itExamDataService.updateTExamData(new TExamData().setId(id).setStatus(status));
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> task = taskService.createTaskQuery()
                .processInstanceId(processExamList.get(0).getProcessId()).list();
        //去除重复的流程
        if (task.size() > 1) {

//                .singleResult();
        }
        //查询当前登陆人
        JSONObject user_other = JSONObject.parseObject(SecurityUtils.getLoginUser().getUser().getOther());
        String userId = user_other.getString("id");
        HashMap<String, Object> actVarMap = new HashMap<>();
        actVarMap.put("isPass", "1");
        taskService.setAssignee(task.get(0).getId(), userId);
        taskService.complete(task.get(0).getId(), actVarMap);
        return AjaxResult.success();
    }

    /**
     * 完成个人任务
     */
    public AjaxResult finishProcessIndividualTask(Long id, Integer isPass) {


        //通过试卷id获取流程实例id
        ProcessExam processExamVar = new ProcessExam();
        processExamVar.setExamdataId(id);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessExam> processExamList = processExamService.selectProcessExamList(processExamVar);
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId(processExamList.get(0).getProcessId())
                .singleResult();
        //查询当前登陆人
        JSONObject user_other = JSONObject.parseObject(SecurityUtils.getLoginUser().getUser().getOther());
        String userId = user_other.getString("id");

        HashMap<String, Object> actVarMap = new HashMap<>();
        actVarMap.put("isPass", isPass);
        taskService.setAssignee(task.getId(), userId);
        taskService.complete(task.getId(), actVarMap);

        if (isPass == 0 && task.getTaskDefinitionKey().equals("_4")) {
            //修改试卷状态
            itExamDataService.updateTExamData(new TExamData().setId(id).setStatus(1));
        } else if (isPass == 0 && task.getTaskDefinitionKey().equals("_5")) {
            //修改试卷状态
            itExamDataService.updateTExamData(new TExamData().setId(id).setStatus(3));
        } else {
            //修改试卷状态
            itExamDataService.updateTExamData(new TExamData().setId(id).setStatus(2));
        }
        ExamLog examLog = examLogService.selectExamLogByExamId(String.valueOf(id));
        examLog.setRejectStatus("0");
        if (isPass == 0) {

            examLog.setExamStatus("1");
            examLog.setRejectCount(examLog.getRejectCount() + 1);
            examLog.setRejectTime(new Date());

            this.sendMessageByUserId(Integer.valueOf(Math.toIntExact(id)),null);
        }
        examLogService.updateExamLog(examLog);
        return AjaxResult.success();
    }

    /**
     * 查询当前登陆人权限等级 0老师 1系主任 2 督导
     */
    public AjaxResult getPermission() {
        JSONObject jsonObject = JSONObject.parseObject(SecurityUtils.getLoginUser().getUser().getOther());
        return AjaxResult.success(null, jsonObject.getString("position"));
    }

    /**
     * 通过试卷id获取当前试卷审批信息
     *
     * @param id
     * @return
     */

    @GetMapping("/personalHistory")
    public List<? extends Object> personalHistory(Long id) {
        List<ProcessExam> processExams = processExamService.selectProcessExamList(new ProcessExam().setExamdataId(id));
        if (processExams.isEmpty()) {
            return processExams;
        }
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processExams.get(0).getProcessId())
                .finished()
                .orderByHistoricTaskInstanceStartTime()
                .asc()
                .list();
        Object userInfo = null;
        LinkedList<Object> resultList = new LinkedList<>();

        for (HistoricTaskInstance historicTaskInstance : list) {
            userInfo = teacherInfoUtils.getUserInfoById(historicTaskInstance.getAssignee());

            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("user", userInfo);
            resultMap.put("historicTaskInstance", historicTaskInstance);


            if (historicTaskInstance.getTaskDefinitionKey().equals("_6")) {
                resultMap.put("isFinished", 1);
            } else {
                resultMap.put("isFinished", 0);
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public AjaxResult getCurrTeacherInto() {
        JSONObject loginUser = RzykUtils.getLoginUser();
        Long codeitemdesc = loginUser.getLong("codeitemdescId");
        SchoolDivision schoolDivision = schoolDivisionService.selectSchoolDivisionById(codeitemdesc);
        HashMap<String, Object> par = new HashMap<>();
        par.put("name", loginUser.get("name"));
        par.put("schoolDivision", schoolDivision);
        par.putAll(loginUser);
        return AjaxResult.success(par);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public AjaxResult getpermission() {
        List<Map<String, Object>> permission = teacherInfoService.selectTeacherPermission();
        return AjaxResult.success(permission);
    }

    /**
     * @param actId
     * @return
     */
    public AjaxResult getinfobymenu(String  actId) {
         /*
         invigilator_get_exam 监考老师领卷
         invigilator_hand_exam 监考老师交卷
         teacher_get_exam 授课老师领卷
         teacher_hand_exam  授课老师交卷
         supervisory_approval 督导审批
         */

        JSONObject loginUser = RzykUtils.getLoginUser();
        String id = loginUser.getString("id");
        ProcessEngine pr = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = pr.getHistoryService();
        HistoricActivityInstanceQuery actInsQuery = historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> list = actInsQuery
                .taskAssignee(id)
                .activityId(actId)
                .finished()
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();
        LinkedList<Object> resultList = new LinkedList<>();
        for (HistoricActivityInstance hisActIns : list) {
            HashMap<String, Object> result = new HashMap<>();
            ProcessExam processExamPar = new ProcessExam()
                    .setProcessId(hisActIns.getProcessInstanceId());
            if (ObjectUtils.isEmpty(processExamPar)) {
                throw new ServiceException("信息错误");
            }
            List<ProcessExam> processExamList = processExamService.selectProcessExamList(processExamPar);
            if (processExamList.isEmpty()) {
                throw new ServiceException("信息错误");
            }

            ProcessExam processExamDB = processExamList.get(0);
            result.put("processExamDB", processExamDB);

            //获取试卷信息
            TExamData tExamData = itExamDataService.selectTExamDataById(processExamDB.getExamdataId());
            if(ObjectUtils.isEmpty(tExamData)) tExamData=new TExamData();
            result.put("examData", tExamData);
            //获取学院信息
            SchoolDivision schoolDivision = schoolDivisionService.selectSchoolDivisionById(processExamDB.getCodeitemdesc());
           if(ObjectUtils.isEmpty(schoolDivision)) schoolDivision=new SchoolDivision();
            result.put("schoolDivision", schoolDivision);
            //封装本条实例数据
            result.put("hisActIns", hisActIns);
            resultList.add(result);

        }
        return AjaxResult.success(resultList);
    }

    /**
     * 查询通知公告列表
     */

    public AjaxResult list(Notice notice, String id) {

        List<Notice> list = noticeService.selectNoticeList(notice);
        TExamData tExamData = itExamDataService.selectTExamDataById(Long.valueOf(id));
        Notice noticeDB = list.get(0);
        Class<? extends TExamData> aClass = tExamData.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        String res = noticeDB.getNoticeContent();

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = "{{" + declaredField.getName().toLowerCase() + "}}";
            int i = res.toLowerCase().indexOf(name);
            if (i == -1) {
                continue;
            }
            log.info("{}:{}", name, i);
            try {
                res = res.toLowerCase().replace(name.toLowerCase(), (CharSequence) declaredField.get(tExamData));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        res.replace("&lt;", "<")
                .replace("&gt;", ">");
              /* .replace("<div>","<view>")
               .replace("</div>","</view>")
               .replace("<p>","<view>")
               .replace("</p>","</view>")
               .replace("<h2>","<view>")
               .replace("</h2>","</view>");*/
        log.info("{}:{}", "标语", res);
        return AjaxResult.success("",res);
    }

    /**
     * 封装试卷管理的数据
     *
     * @param id
     * @return
     */
    public AjaxResult getListForManage(Long id) {
        log.info("{}");
        //封装返回结果的容器
        Map<String, Object> map_param = new HashMap<>();
        //前端控制是否显示的容器
        HashMap<String, Object> viewMap = new HashMap<>();
        //获取当前登陆人
        final JSONObject loginUser = RzykUtils.getLoginUser();
        //当前试卷信息
        final TExamData tExamData = itExamDataService.selectTExamDataById(id);
        //装入试卷信息
        map_param.put("exam", tExamData);
        //通过试卷id查询试卷日志
        ExamLog examLog = examLogService.selectExamLogByExamId(String.valueOf(id));
        viewMap.put("isInvigilate", 0);
        //封装控制信息
        map_param.put("viewMap", viewMap);
        map_param.put("examLog", examLog);
        //创建统一返回结果
        AjaxResult success = AjaxResult.success(map_param);
        //判断当前登录人是否是主监考或者副监考 如果是isInvigilate 如果不是isInvigilate为0
        //todo 此处需要修改
        final String[] cheiefinv_spit = tExamData.getChiefinv().split("\\(");
        final String[] assistantinv_spit = tExamData.getAssistantinv().split("\\(");
        if (cheiefinv_spit[0].equals(loginUser.getString("name")) || assistantinv_spit[0].equals(loginUser.getString("name"))) {
//        if (StringUtils.contains(tExamData.getChiefinv()+";"+tExamData.getAssistantinv(),loginUser.getString("name"))) {
            viewMap.put("isInvigilate", 1);
     /*   }
        if (loginUser.getString("name").equals(tExamData.getChiefinv()) || loginUser.getString("name").equals(tExamData.getAssistantinv())) {
            viewMap.put("isInvigilate", 1);*/
        } else {
            viewMap.put("isInvigilate", 0);
            return success;
        }
        //判断试卷操作日志是否为存在
        if (ObjectUtils.isEmpty(examLog)) {
            viewMap.put("isGET", 1);
            viewMap.put("isSubmit", 0);
            log.info("manageList:{}", success);
            return success;
        }
        map_param.put("examLog", examLog);
        viewMap.put("isGET", 1);
        viewMap.put("isSubmit", 1);
        //判断如果是主监考
        if (examLog.getChiefinvId().equals(RzykUtils.getLoginUser().getString("id"))) {
            long time = (examLog.getChiefinvInvigilateFetchTime().getTime() + 3600 * 1000) - new Date().getTime();
            viewMap.put("timer", time / 1000);
            if (examLog.getChiefinvIsGet().equals("Y")) {
                viewMap.put("isGET", 0);
            }
            if (examLog.getChiefinvIsSubmit().equals("Y")) {
                viewMap.put("isSubmit", 0);
            }
        } else if (examLog.getAssistantinvId().equals(RzykUtils.getLoginUser().getString("id"))) {
            long time = (examLog.getAssistantinvInvigilateFetchTime().getTime() + 3600 * 1000) - new Date().getTime();
            viewMap.put("timer", time / 1000);
            if (examLog.getAssistantinvIsGet().equals("Y")) {
                viewMap.put("isGET", 0);
            }
            if (examLog.getAssistantinvIsSubmit().equals("Y")) {
                viewMap.put("isSubmit", 0);
            }
        }
        return success;
    }

    /**
     * 获取考试信息
     *
     * @return
     */
    public AjaxResult getExamDataGroupByDate() {
        Map<String, List<TExamData>> tExamDataList = itExamDataService.getExamDataGroupByDate();
        TreeMap<String, Object> resultMap = new TreeMap<>();
        Set<Map.Entry<String, List<TExamData>>> entries = tExamDataList.entrySet();
        ArrayList<Object> objects = new ArrayList<>();
        int i = 0;
        int key = 1;
        TreeMap<String, Object> spile_map = new TreeMap<>();
        for (Map.Entry<String, List<TExamData>> entry : entries) {
            spile_map.put(entry.getKey(), entry.getValue());
//                objects.add(spile_map);
            if (spile_map.size() % 4 == 0) {
                resultMap.put(key++ + "", spile_map);
                spile_map = new TreeMap<>();
            }
            if (spile_map.size() % 4 != 0 && i == entries.size() - 1) {
                resultMap.put(key++ + "", spile_map);
            }
            i += 1;
        }
        return AjaxResult.success(resultMap);
    }

    /**
     * 获取材料信息
     *
     * @param id
     * @return
     */
    public AjaxResult getDocumentList(Long id) {
        HashMap<String, Object> resultMap = new HashMap<>();
        TExamData tExamData = itExamDataService.selectTExamDataById(id);
        resultMap.put("examData", tExamData);
        if (tExamData.getTeacher().equals(RzykUtils.getLoginUser().getString("name"))) {
            resultMap.put("isShow", 1);
        } else {
            resultMap.put("isShow", 0);
            return AjaxResult.success(resultMap);
        }
        final List<Document> documents = documentService.selectDocumentList(null);
        ExamLog examLog = examLogService.selectExamLogByExamId(String.valueOf(id));
        resultMap.put("examLog", examLog);
        resultMap.put("documents", documents);
        //查看监考老师是否已经交卷
        Boolean isSubmit = examLog.getAssistantinvIsSubmit().equals("Y") || examLog.getChiefinvIsSubmit().equals("Y");
        resultMap.put("invigilationIsSubmit", isSubmit == true ? 1 : 0);
        if (ObjectUtils.isNotEmpty(examLog.getInvigilation())) {
            resultMap.put("invigilationIsSubmit", 2);
        }
        if (examLog.getRejectStatus() != null && examLog.getRejectStatus().equals("1")) {
            resultMap.put("invigilationIsSubmit", 2);
        } else if (examLog.getRejectStatus() != null && examLog.getRejectStatus().equals("2")) {
            resultMap.put("invigilationIsSubmit", 3);
        }
        String ids = examLog.getMaterialSubmission();
        if (StringUtils.isEmpty(ids)) {
            return AjaxResult.success(resultMap);
        }
        String[] id_split = ids.split(";");
        List<Document> Document = documentService.selectDocumentByIds(id_split);
        if (!Document.isEmpty() && examLog.getRejectStatus().equals("2")) {
            resultMap.put("isBtn", 1);
        }
        resultMap.put("dataList", Document);
        return AjaxResult.success(resultMap);
    }

    /**
     * 授课老师领卷
     */
    public AjaxResult teacherLeadsThePaper(Long id) {
        examLogService.teacherLeadsThePaper(id);

        //通过试卷id获取流程实例id
        ProcessExam processExamVar = new ProcessExam();
        processExamVar.setExamdataId(id);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessExam> processExamList = processExamService.selectProcessExamList(processExamVar);
        TaskService taskService = processEngine.getTaskService();
        List<Task> task = taskService.createTaskQuery()
                .processInstanceId(processExamList.get(0).getProcessId())
                .list();
        //查询当前登陆人
        JSONObject user_other = JSONObject.parseObject(SecurityUtils.getLoginUser().getUser().getOther());
        String userId = user_other.getString("id");
        HashMap<String, Object> actVarMap = new HashMap<>();
        taskService.setAssignee(task.get(0).getId(), userId);
        taskService.complete(task.get(0).getId(), actVarMap);
        return AjaxResult.success();
    }

    /**
     * 授课老师交卷
     */
    public AjaxResult teacherHandThePaper(String id, String selectList) {
        id = id.replaceAll("[^0-9]", "");
        examLogService.teacherHandThePaper(Long.valueOf(id), selectList);
        //通过试卷id获取流程实例id
        ProcessExam processExamVar = new ProcessExam();
        processExamVar.setExamdataId(Long.valueOf(id));
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessExam> processExamList = processExamService.selectProcessExamList(processExamVar);
        TaskService taskService = processEngine.getTaskService();
        List<Task> task = taskService.createTaskQuery()
                .processInstanceId(processExamList.get(0).getProcessId())
                .list();
        //查询当前登陆人
        JSONObject user_other = JSONObject.parseObject(SecurityUtils.getLoginUser().getUser().getOther());
        String userId = user_other.getString("id");
        HashMap<String, Object> actVarMap = new HashMap<>();
        taskService.setAssignee(task.get(0).getId(), userId);
        taskService.complete(task.get(0).getId(), actVarMap);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult supervisorReject(String reason, String id) {
        id = id.replaceAll("[^0-9]", "");
        //通过试卷id获取流程实例id
        ProcessExam processExamVar = new ProcessExam();
        processExamVar.setExamdataId(Long.valueOf(id));
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessExam> processExamList = processExamService.selectProcessExamList(processExamVar);
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId(processExamList.get(0).getProcessId())
                .singleResult();
        //查询当前登陆人
        JSONObject user_other = JSONObject.parseObject(SecurityUtils.getLoginUser().getUser().getOther());
        String userId = user_other.getString("id");

        HashMap<String, Object> actVarMap = new HashMap<>();
        actVarMap.put("isPass", "0");
        if (ObjectUtils.isEmpty(task)) throw new ServiceException("未开启流程或者流程已经结束");
        taskService.setAssignee(task.getId(), userId);
        taskService.complete(task.getId(), actVarMap);
        ExamLog examLog = examLogService.selectExamLogByExamId(id);
        examLog.setRejectStatus("1");
        examLog.setRejectCount(examLog.getRejectCount() + 1);
        examLog.setRejectTime(new Date());

        this.sendMessageByUserId(Integer.valueOf(id),reason);
        examLogService.updateExamLog(examLog);
        examLogService.addReasonLog(examLog.getId(),reason);
        return AjaxResult.success();
    }
    public void sendMessageByUserId(Integer id,String ids) {
        List<ProcessExam> processExams = processExamService.selectProcessExamList(new ProcessExam().setExamdataId(Long.valueOf(id)));
        HashMap<String, Object> param = new HashMap<>();
        ExamLog examLog = examLogService.selectExamLogByExamId(String.valueOf(id));
        TeacherInfo teacherInfo = teacherInfoService.selectTeacherInfoById(examLog.getTeacherId());
        String[] id_split = ids.split(";");
        List<Document> document = documentService.selectDocumentByIds(id_split);
        StringBuffer buffer = new StringBuffer();
        buffer.append(teacherInfo.getName()+"老师,"+" 你好！"+"<div>你提交的试卷被驳回</div>"+"<div>驳回人:"+RzykUtils.getLoginUser().getString("name")+"</div>");
        buffer.append("<div>驳回原因如下</div>");
        for (Document documentStr : document) {
            buffer.append("<div>").append(documentStr.getDetail()).append("</div>");
        }
        buffer.append("不符合要求！ 请尽快处理");
        String detail = buffer.toString();
        param.put("userId",teacherInfo.getUserId());
        param.put("id", id);
        log.info("用户数据：{}", param);
        weChatUtils.sendMessageByUserId(param,detail);
    }
}
