package com.rzyk.schoolDivision.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.rzyk.TExamData.domain.TExamData;
import com.rzyk.TExamData.service.ITExamDataService;
import org.aspectj.weaver.loadtime.Aj;
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
import com.rzyk.schoolDivision.domain.SchoolDivision;
import com.rzyk.schoolDivision.service.ISchoolDivisionService;
import com.rzyk.common.utils.poi.ExcelUtil;
import com.rzyk.common.core.page.TableDataInfo;

/**
 * 分院管理Controller
 *
 * @author ccj
 * @date 2023-03-15
 */
@RestController
@RequestMapping("/schoolDivision/schoolDivision")
public class SchoolDivisionController extends BaseController {
    @Autowired
    private ISchoolDivisionService schoolDivisionService;
    @Autowired
    private ITExamDataService itExamDataService;

    /**
     * 查询分院管理列表
     */
    @PreAuthorize("@ss.hasPermi('schoolDivision:schoolDivision:list')")
    @GetMapping("/list")
    public TableDataInfo list(SchoolDivision schoolDivision) {
        startPage();
        List<SchoolDivision> list = schoolDivisionService.selectSchoolDivisionList(schoolDivision);
        return getDataTable(list);
    }

    /**
     * 导出分院管理列表
     */
    @PreAuthorize("@ss.hasPermi('schoolDivision:schoolDivision:export')")
    @Log(title = "分院管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SchoolDivision schoolDivision) {
        List<SchoolDivision> list = schoolDivisionService.selectSchoolDivisionList(schoolDivision);
        ExcelUtil<SchoolDivision> util = new ExcelUtil<SchoolDivision>(SchoolDivision.class);
        util.exportExcel(response, list, "分院管理数据");
    }

    /**
     * 获取分院管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('schoolDivision:schoolDivision:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(schoolDivisionService.selectSchoolDivisionById(id));
    }

    /**
     * 新增分院管理
     */
    @PreAuthorize("@ss.hasPermi('schoolDivision:schoolDivision:add')")
    @Log(title = "分院管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SchoolDivision schoolDivision) {
        return toAjax(schoolDivisionService.insertSchoolDivision(schoolDivision));
    }

    /**
     * 修改分院管理
     */
    @PreAuthorize("@ss.hasPermi('schoolDivision:schoolDivision:edit')")
    @Log(title = "分院管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SchoolDivision schoolDivision) {
        return toAjax(schoolDivisionService.updateSchoolDivision(schoolDivision));
    }

    /**
     * 删除分院管理
     */
    @PreAuthorize("@ss.hasPermi('schoolDivision:schoolDivision:remove')")
    @Log(title = "分院管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(schoolDivisionService.deleteSchoolDivisionByIds(ids));
    }

    /**
     * 通过id获取图片路径
     */
    @GetMapping("/getSchoolDivisionLogo/{id}")
    public AjaxResult getSchoolDivisionLogo(@PathVariable Long id) {
        TExamData tExamData = itExamDataService.selectTExamDataById(id);
        String classessdept = tExamData.getClassessdept();
        SchoolDivision schoolDivision = new SchoolDivision();
        schoolDivision.setBranchName(classessdept);
        List<SchoolDivision> schoolDivisions = schoolDivisionService.selectSchoolDivisionList(schoolDivision);
        return  AjaxResult.success(schoolDivisions.get(0).getBranchIco());

    }
}
