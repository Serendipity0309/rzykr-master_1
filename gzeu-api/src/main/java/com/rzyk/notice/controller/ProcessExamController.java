package com.rzyk.notice.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.rzyk.notice.domain.ProcessExam;
import com.rzyk.notice.service.IProcessExamService;
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
import com.rzyk.common.annotation.Log;
import com.rzyk.common.core.controller.BaseController;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.enums.BusinessType;
import com.rzyk.common.utils.poi.ExcelUtil;
import com.rzyk.common.core.page.TableDataInfo;

/**
 * 流程实例关联试卷信息Controller
 * 
 * @author ccj
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/system/exam")
public class ProcessExamController extends BaseController
{
    @Autowired
    private IProcessExamService processExamService;

    /**
     * 查询流程实例关联试卷信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:exam:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessExam processExam)
    {
        startPage();
        List<ProcessExam> list = processExamService.selectProcessExamList(processExam);
        return getDataTable(list);
    }

    /**
     * 导出流程实例关联试卷信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:exam:export')")
    @Log(title = "流程实例关联试卷信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessExam processExam)
    {
        List<ProcessExam> list = processExamService.selectProcessExamList(processExam);
        ExcelUtil<ProcessExam> util = new ExcelUtil<ProcessExam>(ProcessExam.class);
        util.exportExcel(response, list, "流程实例关联试卷信息数据");
    }

    /**
     * 获取流程实例关联试卷信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:exam:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(processExamService.selectProcessExamById(id));
    }

    /**
     * 新增流程实例关联试卷信息
     */
    @PreAuthorize("@ss.hasPermi('system:exam:add')")
    @Log(title = "流程实例关联试卷信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessExam processExam)
    {
        return toAjax(processExamService.insertProcessExam(processExam));
    }

    /**
     * 修改流程实例关联试卷信息
     */
    @PreAuthorize("@ss.hasPermi('system:exam:edit')")
    @Log(title = "流程实例关联试卷信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessExam processExam)
    {
        return toAjax(processExamService.updateProcessExam(processExam));
    }

    /**
     * 删除流程实例关联试卷信息
     */
    @PreAuthorize("@ss.hasPermi('system:exam:remove')")
    @Log(title = "流程实例关联试卷信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(processExamService.deleteProcessExamByIds(ids));
    }
}
