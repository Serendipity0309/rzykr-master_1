import request from "@/utils/request/index.js"

export default {
	// 密码登录
	passwordLogin(params) {
		return request("/platform/metadata/logon", params)
	},

	// 短信登录
	noteLogin(params) {
		return request("/platform/metadata/login", params)
	},

	// 获取验证码：用于短信登陆、密码找回、用户注销
	getAuthCode(params) {
		return request("/platform/metadata/sms", params)
	},

	// 获取注册账号验证码
	getRegistAuthCode(params) {
		return request("/platform/metadata/sms1", params)
	},

	// 登录者信息
	getMyInfo(params) {
		return request("/platform/metadata/bindinfo", params)
	},




	
}
