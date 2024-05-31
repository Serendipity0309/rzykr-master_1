package com.rzyk.examLog.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.rzyk.examLog.domain.ExamLog;
import com.rzyk.examLog.service.IExamLogService;
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
 * 试卷操作日志Controller
 * 
 * @author ruoyi
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/system/log")
public class ExamLogController extends BaseController
{
    @Autowired
    private IExamLogService examLogService;

    /**
     * 查询试卷操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExamLog examLog)
    {
        startPage();
        List<ExamLog> list = examLogService.selectExamLogList(examLog);
        return getDataTable(list);
    }

    /**
     * 导出试卷操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:export')")
    @Log(title = "试卷操作日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExamLog examLog)
    {
        List<ExamLog> list = examLogService.selectExamLogList(examLog);
        ExcelUtil<ExamLog> util = new ExcelUtil<ExamLog>(ExamLog.class);
        util.exportExcel(response, list, "试卷操作日志数据");
    }

    /**
     * 获取试卷操作日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(examLogService.selectExamLogById(id));
    }

    /**
     * 新增试卷操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:add')")
    @Log(title = "试卷操作日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamLog examLog)
    {
        return toAjax(examLogService.insertExamLog(examLog));
    }

    /**
     * 修改试卷操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:edit')")
    @Log(title = "试卷操作日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamLog examLog)
    {
        return toAjax(examLogService.updateExamLog(examLog));
    }

    /**
     * 删除试卷操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "试卷操作日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(examLogService.deleteExamLogByIds(ids));
    }
    /**
     * 大屏统计
     */
    @PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "试卷操作日志", businessType = BusinessType.DELETE)
    @GetMapping("/count")
    public AjaxResult countAll()
    {
        return AjaxResult.success(examLogService.countAll());
    }
}
