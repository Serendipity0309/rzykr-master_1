package com.rzyk.TExamData.controller;
import com.rzyk.common.annotation.Log;
import com.rzyk.common.core.controller.BaseController;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.core.page.TableDataInfo;
import com.rzyk.common.enums.BusinessType;
import com.rzyk.common.utils.poi.ExcelUtil;
import com.rzyk.TExamData.domain.TExamData;
import com.rzyk.TExamData.service.ITExamDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 考试安排数据Controller
 * 
 * @author ccj
 * @date 2022-11-07
 */
@RestController
@RequestMapping("/system/data")
public class TExamDataController extends BaseController
{
    @Autowired
    private ITExamDataService tExamDataService;

    /**
     * 查询考试安排数据列表
     */
//    @PreAuthorize("@ss.hasPermi('system:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(TExamData tExamData)
    {
        startPage();
        List<TExamData> list = tExamDataService.selectTExamDataList(tExamData);
        return getDataTable(list);
    }

    @GetMapping("/getDatalist")
    public TableDataInfo getData(String date,String keyword)
    {
        System.out.println();
        List<TExamData> list =tExamDataService.selectTExamDataListByKeyword(date,keyword);
        return getDataTable(list);

    }
    /**
     * 导出考试安排数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:data:export')")
    @Log(title = "考试安排数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TExamData tExamData)
    {
        List<TExamData> list = tExamDataService.selectTExamDataList(tExamData);
        ExcelUtil<TExamData> util = new ExcelUtil<TExamData>(TExamData.class);
        util.exportExcel(response, list, "考试安排数据数据");
    }

    /**
     * 获取考试安排数据详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:data:query')")
    @GetMapping( "/getData")
    public AjaxResult getInfo( Long id)
    {
        return AjaxResult.success(tExamDataService.selectTExamDataById(id));
    }

    /**
     * 新增考试安排数据
     */
    @PreAuthorize("@ss.hasPermi('system:data:add')")
    @Log(title = "考试安排数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TExamData tExamData)
    {
        return toAjax(tExamDataService.insertTExamData(tExamData));
    }

    /**
     * 修改考试安排数据
     */
    @PreAuthorize("@ss.hasPermi('system:data:edit')")
    @Log(title = "考试安排数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TExamData tExamData)
    {
        return toAjax(tExamDataService.updateTExamData(tExamData));
    }
    /*@PreAuthorize("@ss.hasPermi('system:data:importdate')")
    @Log(title = "考试安排数据", businessType = BusinessType.UPDATE)*/
    @PostMapping ("/importdate/file")
    public AjaxResult
    importDate( Long brach, String semester_sel,
                                 MultipartFile file ){
         ExcelUtil<TExamData> tExamDataExcelUtil = new ExcelUtil<TExamData>(TExamData.class);
        String message ="";
        try( InputStream inputStream = file.getInputStream()) {
            List<TExamData> tExamData = tExamDataExcelUtil.importExcel(inputStream);
              message = tExamDataService.importDate(tExamData,brach,semester_sel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success(message);
    }
    /**
     * 删除考试安排数据
     */
    @PreAuthorize("@ss.hasPermi('system:data:remove')")
    @Log(title = "考试安排数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tExamDataService.deleteTExamDataByIds(ids));
    }
    /**
     * 查询表字段信息
     */
    @GetMapping("/field")
    public TableDataInfo getTableField(){
        List<Map<String,String>> list=tExamDataService.selectTableField();
        List<List<Map<String, String>>> lists = new ArrayList<>();
        for (int i = 0; i <=list.size()/10; i++) {
           if (i== list.size()/10){
               lists.add(list.subList(i*10,i*10+list.size()%10));
           }else {
               lists.add(list.subList(i*10,i*10+10)) ;
           }
        }
        return getDataTable(lists);
    }
    /**
     * 查询所有老师
     */
    @GetMapping("/allteacher")
    public TableDataInfo getAllteacher(){
        List<Map<String ,String>> teachers=tExamDataService.getAllteacher();
        return getDataTable(teachers);
    }
    /**
     * 查询同一时间考试的教室
     */
    @GetMapping("/syncPlace/{id}")
    public TableDataInfo getSyncPlace(@PathVariable String id){
    List<Map<String, String>>  places=tExamDataService.getSyncPlace(id);
        return getDataTable(places);
    }
    /**
     * 修改考场
     */
    @PostMapping("/syncPlace/exchangeExamPlace/{oringeId}/{exchangeId}")
    public AjaxResult exchangeExamPlace(@PathVariable String oringeId,@PathVariable String exchangeId){
        tExamDataService.exchangeExamPlace(oringeId,exchangeId);
        return AjaxResult.success();
    }
}
