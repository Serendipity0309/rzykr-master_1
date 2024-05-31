package com.rzyk.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson2.JSONObject;
import com.rzyk.TeacherInfo.domain.TeacherInfo;
import com.rzyk.TeacherInfo.service.ITeacherInfoService;
import com.rzyk.common.exception.ServiceException;
import com.rzyk.common.utils.StringUtils;
import com.rzyk.framework.web.service.SysLoginServiceByCode;
import com.rzyk.utils.WeChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rzyk.common.constant.Constants;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.core.domain.entity.SysMenu;
import com.rzyk.common.core.domain.entity.SysUser;
import com.rzyk.common.core.domain.model.LoginBody;
import com.rzyk.common.utils.SecurityUtils;
import com.rzyk.framework.web.service.SysLoginService;
import com.rzyk.framework.web.service.SysPermissionService;
import com.rzyk.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@CrossOrigin
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysLoginServiceByCode sysLoginServiceByCode;
    @Autowired
    private ITeacherInfoService teacherInfoService;

    @Autowired
    private WeChatUtils weChatUtils;
    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        String token = null;
        if (StringUtils.isBlank(loginBody.getUsername())) {
            JSONObject userInfo = weChatUtils.getUserInfo(loginBody.getPassword());
            loginBody.setPassword(userInfo.getString("UserId"));
          /*  //优先查询督导

            Superintendent superintendent = new Superintendent();
            superintendent.setUserId(getUserIdByWechat(userInfo.getString("UserId")));
            List<Superintendent> superintendents = superintendentService.selectSuperintendentList(superintendent);
            if (!superintendents.isEmpty())

            {
                Superintendent superintendentOB = superintendents.get(0);
                String json = JSONObject.toJSONString(superintendentOB);

                token = sysLoginServiceByCode.login(superintendentOB.getName(), loginBody.getPassword(), JSONObject.parseObject(json));

                return    ajax.put(Constants.TOKEN, token);

            }
            //其次查询系主任
            DepartmentHead departmentHead = new DepartmentHead();
            departmentHead.setUserId(getUserIdByWechat(loginBody.getPassword()));
            List<DepartmentHead> departmentHeads = departmentHeadService.selectDepartmentHeadList(departmentHead);
                if (!departmentHeads.isEmpty())

            {
                DepartmentHead departmentHeadOB = departmentHeads.get(0);
                String json = JSONObject.toJSONString(departmentHeadOB);
                token = sysLoginServiceByCode.login(departmentHeadOB.getName(), loginBody.getPassword(), JSONObject.parseObject(json));
                return  ajax.put(Constants.TOKEN, token);
            }*/

            //最后查询教师
            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setUserId(getUserIdByWechat(loginBody.getPassword()));
            List<TeacherInfo> teacherInfos = teacherInfoService.selectTeacherInfoList(teacherInfo);
            if (!teacherInfos.isEmpty()) {
                TeacherInfo teacherInfoOB = teacherInfos.get(0);
                String json = JSONObject.toJSONString(teacherInfoOB);
                token = sysLoginServiceByCode.login(teacherInfoOB.getName(), loginBody.getPassword(), JSONObject.parseObject(json));
                return  ajax.put(Constants.TOKEN, token);
            }

            throw new ServiceException("未查到你的信息,请联系管理员");
             } else {
            // 生成令牌
            token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                    loginBody.getUuid());
        }
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
  /*  @PostMapping("/login")
    public AjaxResult login_code(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }*/

    /**
     * 获取微信UserId
     */
    public String getUserIdByWechat(String code) {
        //获取accostoken
        //todo 微信环境尚未搭建
        return code;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
