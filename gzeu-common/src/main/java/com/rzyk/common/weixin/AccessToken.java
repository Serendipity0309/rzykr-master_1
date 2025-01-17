package com.rzyk.common.weixin;

import lombok.Data;

/**
 * @desc  : 微信通用接口凭证 
 * 
 * @author: shirayner
 * @date  : 2017-8-20 下午9:35:11
 */
@Data
public class AccessToken {  
    // 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private int expiresIn;  
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
}  