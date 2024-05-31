package com.rzyk.framework.web.service;

import com.alibaba.fastjson2.JSONObject;
import com.rzyk.common.constant.CacheConstants;
import com.rzyk.common.constant.Constants;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.core.domain.entity.SysUser;
import com.rzyk.common.core.domain.model.LoginUser;
import com.rzyk.common.core.redis.RedisCache;
import com.rzyk.common.exception.ServiceException;
import com.rzyk.common.exception.user.CaptchaException;
import com.rzyk.common.exception.user.CaptchaExpireException;
import com.rzyk.common.exception.user.UserPasswordNotMatchException;
import com.rzyk.common.utils.DateUtils;
import com.rzyk.common.utils.MessageUtils;
import com.rzyk.common.utils.ServletUtils;
import com.rzyk.common.utils.StringUtils;
import com.rzyk.common.utils.ip.IpUtils;
import com.rzyk.framework.manager.AsyncManager;
import com.rzyk.framework.manager.factory.AsyncFactory;
import com.rzyk.framework.security.context.AuthenticationContextHolder;
import com.rzyk.framework.web.tool.WeiChatRequest;
import com.rzyk.system.service.ISysConfigService;
import com.rzyk.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginServiceByCode
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;


    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password, JSONObject json)

        //todo 去企业微信获取token
        //todo 通过token和code 去企业微信获取用户信息
    {
        WeiChatRequest weiChatRequest = new WeiChatRequest();
        AjaxResult userInfo = weiChatRequest.getUserInfo();
        //0是教师,1是系主任,2是督导
        if (json.getString("position").equals("0")) {
            username="ccj";
            password="123456";
        }else if (json.getString("position").equals("1")){
            username="xzr";
            password="123456";
        }else if (json.getString("position").equals("2")){
            username="dd";
            password="123456";
        }

        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        finally
        {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        loginUser.getUser().setOther(json.toString());
        recordLoginInfo(loginUser.getUserId());

        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
