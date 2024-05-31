package com.rzyk.document.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.rzyk.common.utils.poi.ExcelUtil;
import com.rzyk.document.domain.Document;
import com.rzyk.document.service.IDocumentService;
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
import com.rzyk.common.core.page.TableDataInfo;

/**
 * 材料Controller
 * 
 * @author ruoyi
 * @date 2023-06-18
 */
@RestController
@RequestMapping("/system/document")
public class DocumentController extends BaseController
{
    @Autowired
    private IDocumentService documentService;

    /**
     * 查询材料列表
     */
    @PreAuthorize("@ss.hasPermi('system:document:list')")
    @GetMapping("/list")
    public TableDataInfo list(Document document)
    {
        startPage();
        List<Document> list = documentService.selectDocumentList(document);
        return getDataTable(list);
    }

    /**
     * 导出材料列表
     */
    @PreAuthorize("@ss.hasPermi('system:document:export')")
    @Log(title = "材料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Document document)
    {
        List<Document> list = documentService.selectDocumentList(document);
        ExcelUtil<Document> util = new ExcelUtil<Document>(Document.class);
        util.exportExcel(response, list, "材料数据");
    }

    /**
     * 获取材料详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:document:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(documentService.selectDocumentById(id));
    }

    /**
     * 新增材料
     */
    @PreAuthorize("@ss.hasPermi('system:document:add')")
    @Log(title = "材料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Document document)
    {
        return toAjax(documentService.insertDocument(document));
    }

    /**
     * 修改材料
     */
    @PreAuthorize("@ss.hasPermi('system:document:edit')")
    @Log(title = "材料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Document document)
    {
        return toAjax(documentService.updateDocument(document));
    }

    /**
     * 删除材料
     */
    @PreAuthorize("@ss.hasPermi('system:document:remove')")
    @Log(title = "材料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(documentService.deleteDocumentByIds(ids));
    }
    /**
     *提交被驳回统计
     */
//    @PreAuthorize("@ss.hasPermi('system:document:remove')")
//    @Log(title = "材料", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(documentService.deleteDocumentByIds(ids));
//    }
}
