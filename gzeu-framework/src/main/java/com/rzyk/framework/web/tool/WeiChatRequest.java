package com.rzyk.framework.web.tool;

import com.alibaba.fastjson2.JSONObject;
import com.rzyk.common.core.domain.AjaxResult;
import com.rzyk.common.utils.StringUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Set;

@Data

public class WeiChatRequest {
    @Value("${wechat.baseUrl}")
    private String baseUrl;
    private String XML_HTTP_REQUEST="XMLHttpRequest";
    private String APPLICATION_JSON_UTF8="application/json;charset=UTF-8";
    private String token;
    @Value("${wechat.corpid}")
    private String corpid;
    @Value("${wechat.secret}")
    private String corpsecret;
  /*  @Resource
    private RestTemplateBuilder restTemplateBuilder;*/
    /**
     * 发送get请求
     *
     * @param url    接口地址
     * @param result 返回值
     * @param method http请求方法
     * @param <T>
     * @return
     */
    protected <T> RequestBody<T> request(String url, JSONObject data, RequestBody<T> result, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-requested-with", XML_HTTP_REQUEST);
        headers.set("Content-Type", APPLICATION_JSON_UTF8);
        if (StringUtils.isNotEmpty(token)) {
            headers.set("Authorization", token);
        }
        HttpEntity<Object> request;
        String requestUrl = url;
        if (method == HttpMethod.GET) {
            request = new HttpEntity<>(headers);
            if (data != null && data instanceof JSONObject && !((JSONObject) data).isEmpty()) {
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestUrl);
                Set<Map.Entry<String, Object>> entrys = ((JSONObject) data).entrySet();
                for (Map.Entry<String, Object> entry : entrys) {
                    Object value = entry.getValue();
                    if (value != null) {
                        builder.queryParam(entry.getKey(), entry.getValue());
                    }
                }
                requestUrl = builder.build().toString();
            }
        } else {
            request = new HttpEntity<>(data, headers);
        }
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate_build = restTemplateBuilder.build();
        System.out.println("requestUrl"+requestUrl);
        ResponseEntity<String> response = restTemplate_build.exchange(requestUrl, method, request, String.class);

        result = JSONObject.parseObject(response.getBody(), result.getClass());
        return result;
    }
    /**
     请求说明
     Https请求方式: GET
     https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=id&corpsecret=secrect
     参数说明
     corpid	企业Id
     corpsecret	管理组的凭证密钥
     */
    public RequestBody<JSONObject> getAccessToken(){
            String url="";
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("corpid",corpid);

        jsonObject.put("corpsecret",corpsecret);
        RequestBody<JSONObject> request = request(url, jsonObject, new RequestBody<JSONObject>(), HttpMethod.GET);
    return request;
    }

    /**
     * 请求方式：POST（HTTPS）
     * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/user/list_id?access_token=ACCESS_TOKEN
     * @return
     */
    public AjaxResult getUserId(){
        RequestBody<JSONObject> accessToken = getAccessToken();
        String url= "cgi-bin/user/list_id?access_token="+accessToken.getAccess_token();
        JSONObject jsonObject = new JSONObject();
        RequestBody<JSONObject> request = request(url, jsonObject, new RequestBody<JSONObject>(), HttpMethod.POST);
        return AjaxResult.success(request);
    }
    /**
     * 请求方式：POST（HTTPS）
     * 请求地址：
     * @return
     */
    public AjaxResult getUserInfo(){

        return AjaxResult.success("systemdebuger");
    }
}
