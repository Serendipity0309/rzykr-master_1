package com.rzyk.common.weixin;



import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;


/**
 * @desc  : 移动端网页授权Service
 * 
 * @author: linhong
 * @date  : 20201205 下午12:18:38
 */
public class MTAuthorizationService {
	@Value("${GET_USERINFO_URL}")
	public static  String GET_USERINFO_URL;
	@Value("${GET_USERDETAIL_URL}")
	public static  String GET_USERDETAIL_URL;

	@Value("${getUser_url}")
	private  static  String getUser_url;

	/** 1.根据code获取成员信息
	 * @desc ：GET请求、
	 * 成员信息包括
	 * UserId	成员UserID
	 * DeviceId	手机设备号(由企业微信在安装时随机生成，删除重装会改变，升级不受影响)
	 * user_ticket	成员票据，最大为512字节。scope为snsapi_userinfo或snsapi_privateinfo，且用户在应用可见范围之内时返回此参数。必须加agentid 参数
	 *              后续利用该参数可以获取用户信息或敏感信息。
	 * expires_in	user_token的有效时间（秒），随user_ticket一起返回
	 *
	 *
	 *
	 * 取一般信息：
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe7da22fd77ddf525&agentid=1000018&redirect_uri=http://baoxiu.gznc.edu.cn/MTAuthorization.jsp&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
	 * 含电话号码：
	 *
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe7da22fd77ddf525&agentid=1000018&redirect_uri=http://baoxiu.gznc.edu.cn/MTAuthorization.jsp&response_type=code&scope=snsapi_privateinfo&state=STATE#wechat_redirect
	 *  
	 * @param accessToken
	 * @param code void
	 */
	public JSONObject getUserInfo(String accessToken, String code) {
		JSONObject jsonObject =null;
		if(code!=null) {
			//1.获取请求的url
			String get_userInfo_url = GET_USERINFO_URL.replace("ACCESS_TOKEN", accessToken)
					.replace("CODE", code);

			//2.调用接口，发送请求，获取成员信息
			 jsonObject = WeiXinUtils.httpRequest(get_userInfo_url, "GET", null);
			System.out.println("jsonObject1:" + jsonObject.toString());

			//3.错误消息处理
			if (null != jsonObject) {
				if (0 != jsonObject.getInteger("errcode")) {
					//log.error("获取成员信息失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
					System.out.println("获取成员信息失败"+jsonObject.getInteger("errcode")+jsonObject.getString("errmsg"));
				}
			}
		}
		return jsonObject;
		
	}
	/** 2.使用userTicket获取成员详情
	 * @desc ：POST请求
	 *  
	 * @param accessToken
	 * @param userTicket
	 * @return JSONObject
	 */
	public JSONObject getUserDetail(String accessToken,UserTicket userTicket) {
		//1.获取请求地址
		String get_userDetail_url=GET_USERDETAIL_URL.replace("ACCESS_TOKEN", accessToken);

		//2.准备好请求包体
		Gson gson = new Gson();
		String jsonUserTicket =gson.toJson(userTicket);      //使用gson.toJson(user)即可将user对象顺序转成json
		System.out.println("jsonUserTicket:"+jsonUserTicket);

		//2.调用接口，发送请求，获取成员信息
		JSONObject jsonObject = WeiXinUtils.httpRequest(get_userDetail_url, "POST", jsonUserTicket);
		System.out.println("jsonObject2:"+jsonObject.toString());

		//3.错误消息处理
		if (null != jsonObject) {
			if (0 != jsonObject.getInteger("errcode")) {
				//log.error("获取成员信息失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				System.out.println("获取成员信息失败"+jsonObject.getInteger("errcode")+jsonObject.getString("errmsg"));

			}
		}

		return jsonObject;

	}
	public JSONObject getUserDetail2(String accessToken,String userId) {

		//1.获取请求的url
		getUser_url=getUser_url.replace("ACCESS_TOKEN", accessToken)
				.replace("USERID", userId);

		//2.调用接口，发送请求，获取成员
		JSONObject jsonObject = WeiXinUtils.httpRequest(getUser_url, "GET", null);
		System.out.println("jsonObject:"+jsonObject.toString());

		//3.错误消息处理
		if (null != jsonObject) {
			if (0 != jsonObject.getInteger("errcode")) {
				//log.error("获取成员失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				System.out.println("获取成员信息失败"+jsonObject.getInteger("errcode")+jsonObject.getString("errmsg"));

			}
		}
		return jsonObject;
	}
	
	
}
