package com.rzyk.service;

import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.notice.domain.Notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ActivitiService {
     void updateTexamDataStatus(String id,Integer status);

     public void sendMessageByUserId(Integer id,String detail);

     public AjaxResult processDeploy();

     List<? extends Object> QueryProcessByLoginUser(Long id, String query);

     ArrayList<Map> QueryProcess();

     AjaxResult processIndividualTask(String processId);

     AjaxResult texamDataBindingId(String id);

     AjaxResult handExamination(Long id, Integer isHand);

     AjaxResult finishProcessIndividualTask(Long id, Integer isPass);

     AjaxResult getPermission();

     List<? extends Object> personalHistory(Long id);

     AjaxResult getCurrTeacherInto();

     AjaxResult getpermission();

     AjaxResult getinfobymenu(String  index);

     AjaxResult list(Notice notice, String id);

     AjaxResult getListForManage(Long id);

     AjaxResult getExamDataGroupByDate();

     AjaxResult getDocumentList(Long id);

     AjaxResult teacherLeadsThePaper(Long id);

     AjaxResult teacherHandThePaper(String id, String selectList);

     AjaxResult supervisorReject(String reason, String id);
}
