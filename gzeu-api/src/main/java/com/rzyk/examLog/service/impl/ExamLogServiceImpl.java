package com.rzyk.examLog.service.impl;

import com.google.gson.internal.LinkedTreeMap;
import com.rzyk.TExamData.domain.TExamData;
import com.rzyk.TExamData.mapper.TExamDataMapper;
import com.rzyk.TExamData.service.ITExamDataService;
import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.TeacherInfo.service.ITeacherInfoService;
import com.rzyk.TeacherInfo.service.impl.TeacherInfoServiceImpl;
import com.rzyk.common.exception.ServiceException;
import com.rzyk.document.domain.Document;
import com.rzyk.document.service.IDocumentService;
import com.rzyk.examLog.domain.ExamLog;
import com.rzyk.examLog.mapper.ExamLogMapper;
import com.rzyk.examLog.service.IExamLogService;
import com.rzyk.utils.RzykUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Stream;

/**
 * 试卷操作日志Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@Service
public class ExamLogServiceImpl implements IExamLogService {
    @Resource
    private ExamLogMapper examLogMapper;
    @Autowired
    private ITExamDataService itExamDataService;
    @Autowired
    private ITeacherInfoService teacherInfoService;
    @Autowired
    private IDocumentService documentService;

    @Override
    public ExamLog selectExamLogByExamId(String id) {
        return examLogMapper.selectExamLogByExamId(id);
    }

    /**
     * 查询试卷操作日志
     *
     * @param id 试卷操作日志主键
     * @return 试卷操作日志
     */
    @Override
    public ExamLog selectExamLogById(Long id) {
        return examLogMapper.selectExamLogById(id);
    }

    /**
     * 查询试卷操作日志列表
     *
     * @param examLog 试卷操作日志
     * @return 试卷操作日志
     */
    @Override
    public List<ExamLog> selectExamLogList(ExamLog examLog) {
        return examLogMapper.selectExamLogList(examLog);
    }

    /**
     * 新增试卷操作日志
     *
     * @param examLog 试卷操作日志
     * @return 结果
     */
    @Override
    public int insertExamLog(ExamLog examLog) {
        return examLogMapper.insertExamLog(examLog);
    }

    @Override
    public void addReasonLog(Long id, String reason) {
        examLogMapper.addReasonLog(id, reason);
    }

    /**
     * 修改试卷操作日志
     *
     * @param examLog 试卷操作日志
     * @return 结果
     */
    @Override
    public int updateExamLog(ExamLog examLog) {
        return examLogMapper.updateExamLog(examLog);
    }

    /**
     * 批量删除试卷操作日志
     *
     * @param ids 需要删除的试卷操作日志主键
     * @return 结果
     */
    @Override
    public int deleteExamLogByIds(Long[] ids) {
        return examLogMapper.deleteExamLogByIds(ids);
    }

    /**
     * 删除试卷操作日志信息
     *
     * @param id 试卷操作日志主键
     * @return 结果
     */
    @Override
    public int deleteExamLogById(Long id) {
        return examLogMapper.deleteExamLogById(id);
    }

    /**
     * 判断主监考和副监考是否领卷
     *
     * @param id 主键
     * @return Boolean
     * @author ccj
     */
    public Boolean invigilatorGet(String id) {
        ExamLog examLog = this.selectExamLogByExamId(id);

        TExamData tExamData = itExamDataService.selectTExamDataById(Long.valueOf(id));
        //判断为null 则创建
        final String[] chiefinv_split = tExamData.getChiefinv().split("\\(");
        final TeacherInfo teacherInfo = new TeacherInfo().setName(chiefinv_split[0]);
        final String[] assistantinv_split = tExamData.getAssistantinv().split("\\(");
        if (ObjectUtils.isEmpty(examLog)) {
            ExamLog examLog_param = new ExamLog();
            //授课老师id
            examLog_param.setTeacherId(teacherInfoService.selectTeacherInfoList(new TeacherInfo().setName(tExamData.getTeacher())).get(0).getId());
            //exam_id 设置试卷id
            examLog_param.setExamId(id)
                    .setRejectStatus("wu")
                    //chiefinv_id 设置主监考id
                    .setChiefinvId(teacherInfoService.selectTeacherInfoList(teacherInfo).get(0).getId())
                    //assistantinv_id 设置副监考ID
                    .setAssistantinvId(teacherInfoService.selectTeacherInfoList(new TeacherInfo().setName(assistantinv_split[0])).get(0).getId());
            if (chiefinv_split[0].equals(RzykUtils.getLoginUser().getString("name"))) {
                //chiefinv_is_get '主监考是否领取(Y/N)'
                examLog_param.setChiefinvIsGet("Y");
                //主监考领卷时间
                examLog_param.setChiefinvInvigilateFetchTime(new Date());
            } else if (assistantinv_split[0].equals(RzykUtils.getLoginUser().getString("name"))) {
                //assistantinv_is_get '副监考是否领取(Y/N)'
                examLog_param.setAssistantinvIsGet("Y");
                //副监考领卷时间
                examLog_param.setAssistantinvInvigilateFetchTime(new Date());
            } else {
                throw new ServiceException("不是监考老师");
            }
            insertExamLog(examLog_param);
        } else {
            if (chiefinv_split[0].equals(RzykUtils.getLoginUser().getString("name"))) {
                //chiefinv_is_get '主监考是否领取(Y/N)'
                examLog.setChiefinvIsGet("Y");
                //invigilation 授课老师领卷时间
                examLog.setChiefinvInvigilateFetchTime(new Date());

            } else if (assistantinv_split[0].equals(RzykUtils.getLoginUser().getString("name"))) {
                //assistantinv_is_get '副监考是否领取(Y/N)'
                examLog.setAssistantinvIsGet("Y");
                //invigilation 授课老师领卷时间
                examLog.setAssistantinvInvigilateFetchTime(new Date());
            } else {
                throw new ServiceException("你不是该试卷监考老师");
            }
            updateExamLog(examLog);
            return false;
        }
        //last_submission_time 授课老师最后一次提交时间
        //submit_count 授课老师提交次数
        //reject_time 驳回时间
        //reject_count 驳回次数
        //invigilate_submit_time 监考老师交卷时间
        //invigilate_paper_status 监考老师领取试卷
        //exam_status 试卷状态
        return true;
    }

    /**
     * 判断主监考和副监考是否领卷
     *
     * @param id 主键
     * @return Boolean
     * @author ccj
     */
    public Boolean invigilatorSubmit(String id) {

        TExamData tExamDataDB = itExamDataService.selectTExamDataById(Long.valueOf(id));
        ExamLog examLog = this.selectExamLogByExamId(id);
        final String[] chiefinv_split = tExamDataDB.getChiefinv().split("\\(");

        final String[] assistantinv_split = tExamDataDB.getAssistantinv().split("\\(");

        if (chiefinv_split[0].equals(RzykUtils.getLoginUser().getString("name"))) {
            //chiefinv_is_get '主监考是否领取(Y/N)'
            examLog.setChiefinvIsSubmit("Y");
            //invigilation 授课老师领卷时间
            examLog.setInvigilateSubmitTime(new Date());

        } else if (assistantinv_split[0].equals(RzykUtils.getLoginUser().getString("name"))) {
            //assistantinv_is_get '副监考是否领取(Y/N)'
            examLog.setAssistantinvIsSubmit("Y");
            //invigilation 授课老师领卷时间
            examLog.setInvigilateSubmitTime(new Date());
        } else {
            throw new ServiceException("你不是该试卷监考老师");
        }
        updateExamLog(examLog);
        return true;
    }

    @Override
    public void teacherLeadsThePaper(Long id) {
        ExamLog examLog = this.selectExamLogByExamId(String.valueOf(id));
        examLog.setInvigilation(new Date());
        this.updateExamLog(examLog);
    }

    @Override
    public void teacherHandThePaper(Long id, String selectList) {
        ExamLog examLog = this.selectExamLogByExamId(String.valueOf(id));
        examLog.setLastSubmissionTime(new Date());
        examLog.setMaterialSubmission(selectList);
        examLog.setSubmitCount(examLog.getSubmitCount() + 1);
        examLog.setExamStatus("Y");
        examLog.setRejectStatus("2");
        this.updateExamLog(examLog);
    }

    @Override
    public Object countAll() {
        HashMap<String, Object> result = new HashMap<>();

        //创建封装数据的容器

        //查询监考场次前五名
        List<Map<String, String>> examLogListInvigilateAsc = examLogMapper.selectExamlogForCountInvigilateAscLimit(5);

        packegCountAllData("InvigilationRankingASC", examLogListInvigilateAsc, result);

        //查询监考场次后五名

        List<Map<String, String>> examLogListInvigilateDesc = examLogMapper.selectExamlogForCountInvigilateDescLimit(5);

        packegCountAllData("InvigilationRankingDESC", examLogListInvigilateDesc, result);
        //查询被驳回次数前五名
        List<Map<String, String>> examLogListRejecAsc = examLogMapper.selectExamlogRejectAscLimit(5);

        packegCountAllData("RejecCountASC", examLogListRejecAsc, result);

        //查询被驳回次数前五名
        List<Map<String, String>> examLogListRejecDesc = examLogMapper.selectExamlogRejectDescLimit(5);
        packegCountAllData("RejecCountDESC", examLogListRejecDesc, result);
        //查询提交材料被驳回最多次
        List<Map<String, String>> examLogListDocument = examLogMapper.selectExamLogListDocument(5);
        //驳回材料分析
        Map<String, Integer> tempResult = new LinkedHashMap<>();
        materialAnalysis(examLogListDocument,tempResult);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> datas = new ArrayList<>();
        tempResult.forEach((key, value) -> {
            names.add(key);
            datas.add(value.toString());
        });
        LinkedHashMap<String, Object> temReultMap = new LinkedHashMap<>();
        temReultMap.put("xLabel",names);
        temReultMap.put("data",datas);
        result.put("examLogListDocument", temReultMap);
        //统计考试总场数
        Integer countExam=examLogMapper.count();
        result.put("countExamlog",countExam);
        //统计参与老师数
        Integer countTeacher = examLogMapper.countTeacher();
        result.put("countTeacher",countTeacher);
        return result;
    }

    //材料分析
    private void materialAnalysis(List<Map<String, String>> examLogListDocument,Map<String, Integer> result) {
        TreeMap<String, Integer> data = new TreeMap<>();
        for (Map<String, String> item : examLogListDocument) {
            String document = item.get("document");
            String[] document_split = document.split(";");
            for (String sp_item : document_split) {
                if (data.containsKey(sp_item)) {
                    data.put(sp_item, data.get(sp_item) + 1);
                } else {
                    data.put(sp_item, 1);
                }
            }
        }
        Map<String, Integer> tempResult = new LinkedHashMap<>();

        //id替换成名称
        exchangeIdName(data,tempResult);
        //map排序

        mapSortByVaule(tempResult,result);

    }

    private void exchangeIdName(TreeMap<String, Integer> data,Map<String, Integer> result) {
        Set<String> keys = data.keySet();
        List<Document> documents = documentService.selectDocumentByIds(keys.toArray(new String[0]));
        documents.forEach(document -> {
            String id = document.getId().toString();
            if (data.containsKey(id)) {
                result.put(document.getDetail(), data.get(id));
            }
        });
    }

    //map排序
    private void mapSortByVaule( Map<String, Integer> TempResult ,Map<String, Integer> result ) {
        Stream<Map.Entry<String, Integer>> sorted = TempResult.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(15);
        sorted.forEach(item -> result.put(item.getKey(), item.getValue()));
    }

    /*
    childrenData: {
    InvigilationRankingASC:{xLabel: [name1,name2],data: [data1,data2]},
    InvigilationRankingDESC:{xLabel: [name1,name2],data: [data1,data2]}}
    * */
    private Map<String, List<String>> packegCountAllData(String name, List<Map<String, String>> data, HashMap<String, Object> resultMap) {
        //需要封装的数据结构
        Map<String, List<String>> result = new LinkedHashMap<>();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> datas = new ArrayList<>();
        for (Map<String, String> datum : data) {
            names.add(datum.get("name"));
            datas.add(datum.get("total"));
        }
        result.put("xLabel", names);
        result.put("data", datas);
        resultMap.put(name, result);
        return result;
    }
}
