package com.rzyk.framework.web.tool;

import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.utils.StringUtils;
import lombok.Data;

@Data
public class RequestBody<T> {
    private int code;
    private String access_token;
    private T data;
    private String errmsg;
    private String errcode;
    public AjaxResult toAjaxResult(){
        AjaxResult result = new AjaxResult(code, errmsg,access_token);
        if (StringUtils.isNotBlank(errcode)){
            result.put("error",errcode);
        }
        return result;
    }
}
