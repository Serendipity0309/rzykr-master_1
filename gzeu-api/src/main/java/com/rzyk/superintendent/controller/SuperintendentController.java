package com.rzyk.superintendent.controller;

import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.superintendent.domain.Superintendent;
import com.rzyk.superintendent.service.ISuperintendentService;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 监考老师信息Controller
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/system/superintendent")
public class SuperintendentController extends BaseController
{
    @Autowired
    private ISuperintendentService superintendentService;

    /**
     * 查询监考老师信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:superintendent:list')")
    @GetMapping("/list")
    public TableDataInfo list(Superintendent superintendent)
    {
        startPage();
        List<Superintendent> list = superintendentService.selectSuperintendentList(superintendent);
        return getDataTable(list);
    }

    /**
     * 导出监考老师信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:superintendent:export')")
    @Log(title = "监考老师信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Superintendent superintendent)
    {
        List<Superintendent> list = superintendentService.selectSuperintendentList(superintendent);
        ExcelUtil<Superintendent> util = new ExcelUtil<Superintendent>(Superintendent.class);
        util.exportExcel(response, list, "监考老师信息数据");
    }

    /**
     * 获取监考老师信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:superintendent:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(superintendentService.selectSuperintendentById(id));
    }

    /**
     * 新增监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('system:superintendent:add')")
    @Log(title = "监考老师信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Superintendent superintendent)
    {
        return toAjax(superintendentService.insertSuperintendent(superintendent));
    }

    /**
     * 修改监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('system:superintendent:edit')")
    @Log(title = "监考老师信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Superintendent superintendent)
    {
        return toAjax(superintendentService.updateSuperintendent(superintendent));
    }

    /**
     * 删除监考老师信息
     */
    @PreAuthorize("@ss.hasPermi('system:superintendent:remove')")
    @Log(title = "监考老师信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(superintendentService.deleteSuperintendentByIds(ids));
    }
    /**
     * 监考老师数据导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importdata")
    public AjaxResult importDataTeacher(MultipartFile file) {
        ExcelUtil<Superintendent> excelUtil = new ExcelUtil<Superintendent>(Superintendent.class);
        String message = "";
        try (InputStream inputStream = file.getInputStream()) {
            List<Superintendent> teacherInfos = excelUtil.importExcel(inputStream);
            message = superintendentService.importDate(teacherInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }
}
