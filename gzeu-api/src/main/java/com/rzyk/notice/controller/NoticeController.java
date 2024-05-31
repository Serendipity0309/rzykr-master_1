package com.rzyk.notice.controller;

import javax.servlet.http.HttpServletResponse;
import com.rzyk.common.config.RuoYiConfig;
import com.rzyk.common.core.domain.entity.SysDept;
import com.rzyk.common.utils.SecurityUtils;
import com.rzyk.common.utils.ip.IpUtils;
import com.rzyk.common.utils.poi.ExcelUtil;
import com.rzyk.system.service.ISysDeptService;
import com.rzyk.TExamData.service.ITExamDataService;
import com.rzyk.notice.domain.Notice;
import com.rzyk.notice.service.INoticeService;
import com.rzyk.notice.utils.Qrcode;
import com.rzyk.utils.WeChatUtils;
import org.springframework.beans.factory.annotation.Value;
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

import java.io.File;
import java.net.*;
import java.util.*;

/**
 * 通知公告Controller
 *
 * @author ruoyi
 * @date 2022-11-09
 */
@RestController
@RequestMapping("/system/notice")
public class NoticeController extends BaseController {
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private ITExamDataService tExamDataService;
    @Autowired
    private WeChatUtils weChatUtils;

    @Value("${DNS}")
    private String dns;
    /**
     * 查询通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('code:code:data')")
    @GetMapping("/lists")
    public TableDataInfo list(Notice notice) {
        startPage();
         weChatUtils.getuserInfos();
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 导出通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:export')")
    @Log(title = "通知公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Notice notice) {
        List<Notice> list = noticeService.selectNoticeList(notice);
        ExcelUtil<Notice> util = new ExcelUtil<Notice>(Notice.class);
        util.exportExcel(response, list, "通知公告数据");
    }

    /**
     * 获取通知公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/getDetail/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Integer noticeId) {
        return AjaxResult.success(noticeService.selectNoticeByNoticeId(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Notice notice) {
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody String text) {
        Notice notice = new Notice();
        //公告标题
        notice.setNoticeTitle("考试标语")
                //公告类型（1考试标语）
                .setNoticeType("1");
        List<Notice> notices = noticeService.selectNoticeList(notice);
        //公告内容
        notice.setNoticeContent(text)
                //公告状态（0正常 1关闭）
                .setStatus("1");
        //公告ID
        //更新者
        notice.setUpdateBy(SecurityUtils.getUsername());
        //更新时间
        notice.setUpdateTime(new Date());
        //备注
        Long deptId = SecurityUtils.getDeptId();
        SysDept sysDept = sysDeptService.selectDeptById(deptId);
        notice.setRemark(sysDept.getRemark());
        //创建者
        notice.setCreateBy(SecurityUtils.getUsername());
        //创建时间
        notice.setCreateTime(new Date());
        noticeService.insertNotice(notice);
        return AjaxResult.success();
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{noticeIds}")
    public AjaxResult remove(@PathVariable Integer[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByNoticeIds(noticeIds));
    }

    /**
     * 删除通知公告
     */
    @RequestMapping("/getImage/{id}")
    public AjaxResult getImage(@PathVariable("id") Long id) {
        String profile = RuoYiConfig.getProfile();
        String filePath = profile + "image/" + id + ".png";
        String fileUrl = filePath.replace(profile, "profile/");


        File file = new File(profile + "image/");
        String ipAddr = IpUtils.getHostIp();
   /*     try {
            InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
     /*   try {
            Map<String, String> ipAddress = getIpAddress();
//            ipAddr = ipAddress.get("eth7");
//            ipAddr = ipAddress.get("wlan0");
        } catch (SocketException e) {
            e.printStackTrace();
        }*/
        String router="/app";
//        String router="/pages/index/index";
        String url=dns+router+"?id="+id;
        Qrcode.createCodeToFile(url, file, id + ".png");
//        String localAddr = request.getLocalAddr();
        return AjaxResult.success(fileUrl);
    }

    /**
     *
     * @return
     * @throws SocketException
     */
    private static Map<String, String> getIpAddress() throws SocketException {
        LinkedList<String> list = new LinkedList<>();
        Map<String, String> map = new HashMap<>();
        Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            NetworkInterface network = (NetworkInterface) enumeration.nextElement();
            String name = network.getName();
            Enumeration addresses = network.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = (InetAddress) addresses.nextElement();
                if (address != null && (address instanceof Inet4Address || address instanceof Inet6Address)) {
                    list.add(address.getHostAddress());
                    if (!map.containsKey(name)) {
                        map.put(name, address.getHostAddress());
                    }else {
                        map.put(name.concat("(1)"),address.getHostAddress());
                    }

                }
            }
        }
        return map;
    }

}
