package com.rzyk.utils;

import com.alibaba.fastjson2.JSONObject;
import com.rzyk.common.core.redis.RedisCache;
import com.rzyk.common.utils.SecurityUtils;
import com.rzyk.common.utils.spring.SpringUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import me.chanjar.weixin.common.bean.WxAccessToken;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信工具类 详情查看企业微信官方api https://developer.work.weixin.qq.com/document/path/91039
 *
 * @author ccj
 */
@Component
public class WeChatUtils {
    @Value("getAccessToken")
    private static String getAccesstokenUrl;
    @Value("${weichat.GET_USERINFO_URL}")
    private  String getUserInfoUrl;

    @Autowired
    private Request request;
    /**
     * 第三方应用appid
     */
    @Value("${weichat.AgentId}")
    private String AgentId;
    /**
     * 第三方应用secret id
     */
    @Value("${weichat.Secret}")
    private String clientSecret;
    /**
     * 第三方应用CorpID
     */
    @Value("${weichat.CorpID}")
    private String corpID;

    @Autowired
    private RedisCache redisCache;

    /**
     请求方式：GET（HTTPS）
     请求URL：https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET
     * 获取accesstoken  如果redis里面没有则写入redis
     */
    public String getAccesstoken() {
//        Object accesstoken = redisCache.getCacheObject("accesstoken");
//        if (ObjectUtils.isNotEmpty(accesstoken)) {
//            return accesstoken.toString();
//        }

        JSONObject param = new JSONObject();

        param.put("corpid",corpID);
        param.put("corpsecret",clientSecret);
        Object res = request.get("https://qyapi.weixin.qq.com/cgi-bin/gettoken", null, param, JSONObject.class);
        JSONObject result = JSONObject.parseObject(res.toString());
        redisCache.setCacheObject("accesstoken", result.getString("access_token"), 120 * 1000, TimeUnit.SECONDS);
        return result.getString("access_token");
    }

    /**
     * 获取当前用户userId
     * 请求方式：GET（HTTPS）
     * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/service/auth/getuserinfo3rd?suite_access_token=SUITE_ACCESS_TOKEN&code=CODE
     * <p>
     * 参数说明：
     * <p>
     * 参数	必须	说明
     * suite_access_token	是	第三方应用的suite_access_token，参见“获取第三方应用凭证” 。不允许代开发自建应用调用。代开发自建应用获取用户身份参考“获取访问用户身份”
     * code	是	通过成员授权获取到的code，最大为512字节。每次成员授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     */
    public JSONObject getUserInfo(String code) {
        if (StringUtils.isEmpty(code)) {
            return JSONObject.parseObject(new UserInfo().setErrmsg("当前不是在企业微信环境下").setErrcode("500").toString());
        }
        JSONObject jsonObject = new JSONObject();
        String accesstoken = getAccesstoken();
        jsonObject.put("access_token", accesstoken);
        jsonObject.put("code", code);
        Object o = request.get(getUserInfoUrl, accesstoken, jsonObject, JSONObject.class);
        return JSONObject.parseObject(o.toString());

    }
    /**
     * 向指定userId的用户发送消息
     *请求方式：POST（HTTPS）
     * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN
     *
     * 参数说明：
     * 参数	是否必须	说明
     * access_token	是	调用接口凭证
     参数说明：

     参数	是否必须	说明
     touser	否	指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
     特殊情况：指定为"@all"，则向该企业应用的全部成员发送
     toparty	否	指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
     当touser为"@all"时忽略本参数
     totag	否	指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
     当touser为"@all"时忽略本参数
     msgtype	是	消息类型，此时固定为：text
     agentid	是	企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
     content	是	消息内容，最长不超过2048个字节，超过将截断（支持id转译）
     safe	否	表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
     enable_id_trans	否	表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略。
     enable_duplicate_check	否	表示是否开启重复消息检查，0表示否，1表示是，默认0
     duplicate_check_interval	否	表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
     */
//    public JSONObject sendMessage(){}
    /**
     * 请求方式：GET（HTTPS）
     * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/externalcontact/get_follow_user_list?access_token=ACCESS_TOKEN
     */
    public void getuserList(){
        String url="https://qyapi.weixin.qq.com/cgi-bin/externalcontact/get_follow_user_list";
        JSONObject par = new JSONObject();
        par.put("access_token",getAccesstoken());
        Object o = request.get(url, getAccesstoken(), par, JSONObject.class);
    }
 /**
 请求方式：GET（HTTPS）
 请求地址：https://qyapi.weixin.qq.com/cgi-bin/externalcontact/get?access_token=ACCESS_TOKEN&external_userid=EXTERNAL_USERID&cursor=CURSOR
 */
    public void getuserInfos(){
        String url="https://qyapi.weixin.qq.com/cgi-bin/externalcontact/get";
        JSONObject par = new JSONObject();
        par.put("access_token",getAccesstoken());
        par.put("userid","systemdebug");
        Object o = request.get(url, getAccesstoken(), par, JSONObject.class);
    }
    /**
     * 请求方式：POST（HTTPS）
     * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN
     * {
     *    "touser" : "UserID1|UserID2|UserID3",
     *    "toparty" : "PartyID1|PartyID2",
     *    "totag" : "TagID1 | TagID2",
     *    "msgtype" : "text",
     *    "agentid" : 1,
     *    "text" : {
     *        "content" : "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。"
     *    },
     *    "safe":0,
     *    "enable_id_trans": 0,
     *    "enable_duplicate_check": 0,
     *    "duplicate_check_interval": 1800
     * }
     */
    public void sendMessageByUserId(Map<String,Object> paramMap,String detail){
        String url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+getAccesstoken();
        HashMap<String, Object> text = new HashMap<>();
     byte[] bytes = detail.getBytes(StandardCharsets.UTF_8);
        try {
            detail = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        text.put("title","微考务提示");
        text.put("description",detail);
        text.put("url","http://kaowu.gznc.edu.cn/app"+"?id="+paramMap.get("id"));
        text.put("btntxt","前往查看细节");
        JSONObject param = new JSONObject();
        param.put("access_token",getAccesstoken());;
        //todo 先写死
        param.put("touser",paramMap.get("userId"));
//        param.put("toparty","systemdebuger");
//        param.put("totag","systemdebuger");
        param.put("msgtype","textcard");
        param.put("agentid","1000024");
        param.put("textcard",text);
        param.put("safe","0");
        param.put("enable_id_trans","0");
        param.put("enable_duplicate_check","0");
        param.put("duplicate_check_interval","1800");
        Object post = request.post(url, getAccesstoken(), param, JSONObject.class);
    }
}

@Data
@Accessors(chain = true)
class UserInfo {
    private String errcode;
    private String errmsg;
    private String corpid;
    private String userid;
    private String user_ticket;
    private String expires_in;
    private String open_userid;
}