package com.rzyk.utils;

import com.alibaba.fastjson2.JSONObject;
import com.rzyk.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class Request<T>{
    @Autowired
    private RestTemplate restTemplate;
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : messageConverters) {
            if(httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName("UTF-8"));
                break;
            }
        }
        return restTemplate;
    }

    /**
     *
     * @param url
     * @param token
     * @param httpMethod
     * @param obj
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T  request(String url,String token,HttpMethod httpMethod,JSONObject obj, Class<T> tClass)  {

        HttpHeaders httpHeaders = new HttpHeaders();

        if (StringUtils.isEmpty(token)){
            MediaType type = MediaType.parseMediaType("application/json");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(type);
            headers.add("Accept", MediaType.ALL_VALUE);
            httpHeaders.add("Authorization", token);
        }
        if (httpMethod.equals(HttpMethod.GET)||httpMethod.equals(HttpMethod.DELETE)){
            int i=0;
            for (Map.Entry<String, Object> par : obj.entrySet()) {
                if (i==0){
                   url= url.concat("?");
                }else {
                    url=url.concat("&");
                }
                i++;
                url=url.concat(par.getKey()).concat("=").concat(par.getValue().toString());
            }
        }
        HttpEntity<String> entity = new HttpEntity<>(obj.toString(),httpHeaders);
        log.info("entity:{}",entity);
        ResponseEntity<T> forEntity = restTemplate.exchange(url, httpMethod,entity,tClass);
        log.info("body:{}",forEntity.getBody());
        return forEntity.getBody();
    }

    /**
     *
     * @param url
     * @param token
     * @param obj
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T  get(String url,String token,JSONObject obj, Class<T> tClass) {
    return request(url,token,HttpMethod.GET,obj,tClass);
    }

    /**
     *
     * @param url
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T  get(String url,String token, Class<T> tClass) {
        return request(url,token,HttpMethod.GET,null,tClass);
    }

    /**
     *
     * @param url
     * @param token
     * @param obj
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T  post(String url,String token,JSONObject obj, Class<T> tClass) {
        return request(url,token,HttpMethod.POST,obj,tClass);
    }
    @Bean
    public Request<T> getRequest(){
        return new Request<>();
    }
    }
