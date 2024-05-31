package com.rzyk.departmentHead.controller;

import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.rzyk.TeacherInfo.domain.TeacherInfo;
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
import com.rzyk.departmentHead.domain.DepartmentHead;
import com.rzyk.departmentHead.service.IDepartmentHeadService;
import com.rzyk.common.utils.poi.ExcelUtil;
import com.rzyk.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 监考老师信息Controller
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/departmentHead/departmentHead")

public class DepartmentHeadController extends BaseController
{
    @Autowired
    private IDepartmentHeadService departmentHeadService;

    /**
     * 查询监考老师信息列表
     */
    @PreAuthorize("@ss.hasPermi('departmentHead:departmentHead:list')")
    @GetMapping("/list")

    public TableDataInfo list(DepartmentHead departmentHead)
    {
        startPage();
        List<DepartmentHead> list = departmentHeadService.selectDepartmentHeadList(departmentHead);
        return getDataTable(list);
    }

    /**
     * 导出监考老师信息列表
     */
    @PreAuthorize("@ss.hasPermi('departmentHead:departmentHead:export')")
    @Log(title = "监考老师信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DepartmentHead departmentHead)
    {
        List<DepartmentHead> list = departmentHeadService.selectDepartmentHeadList(departmentHead);
        ExcelUtil<DepartmentHead> util = new ExcelUtil<DepartmentHead>(DepartmentHead.class);
        util.exportExcel(response, list, "监考老师信息数据");
    }

    /**
     * 获取监考老师信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('departmentHead:departmentHead:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(departmentHeadService.selectDepartmentHeadById(id));
    }

    /**
     * 新增监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('departmentHead:departmentHead:add')")
    @Log(title = "监考老师信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DepartmentHead departmentHead)
    {
        return toAjax(departmentHeadService.insertDepartmentHead(departmentHead));
    }

    /**
     * 修改监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('departmentHead:departmentHead:edit')")
    @Log(title = "监考老师信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DepartmentHead departmentHead)
    {
        return toAjax(departmentHeadService.updateDepartmentHead(departmentHead));
    }

    /**
     * 删除监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('departmentHead:departmentHead:remove')")
    @Log(title = "监考老师信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(departmentHeadService.deleteDepartmentHeadByIds(ids));
    }
    /**
     * 监考老师数据导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importdata")
    public AjaxResult importDataTeacher(MultipartFile file) {
        ExcelUtil<DepartmentHead> excelUtil = new ExcelUtil<DepartmentHead>(DepartmentHead.class);
        String message = "";
        try (InputStream inputStream = file.getInputStream()) {
            List<DepartmentHead> teacherInfos = excelUtil.importExcel(inputStream);
            message = departmentHeadService.importDate(teacherInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }
}
