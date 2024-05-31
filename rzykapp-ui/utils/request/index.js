import config from "./config.js"
// import logout from "@/utils/logout.js"
// const baseUrl = config.baseUrlhttp://192.168.244.102:8081/
const baseUrl = 'http://192.168.2.164:8080'

const defauls = {
	method: "POST",
	loading: true
}

// 全局请求封装
export default (path, data = {}, config = defauls) => {
	console.log('%c请求拦截：', ' background:orange', data);

	const token = uni.getStorageSync("token");
	const Authorization = token ? `Bearer ${uni.getStorageSync("token")}` : "";

	if (config.loading) {
		uni.showLoading({
			title: "加载中",
			mask: true
		});
	};

	return new Promise(async (resolve, reject) => {
		await uni.request({
			header: {
				Authorization,
				'content-type': 'application/json;charset:utf-8'
			},
			url: baseUrl + path,
			method: config.method,
			data,
			success(response) {
				// console.log('%c响应拦截：', ' background:green', response);
				if (response.data.code === 3001) {
					// logout()
				}
				if (response.data.code === 200) {

					uni.setStorageSync("token", response.data.token)
					alert("认证完成")
				}
				if (response.data.code !== 20) {
					uni.showToast({
						icon: "none",
						duration: 4000,
						title: response.data.msg
					});
				}

				resolve(response.data);
			},
			fail(err) {
				uni.showToast({
					icon: "none",
					title: '服务响应失败'
				});
				console.error(err);
				reject(err);
			},
			complete() {
				uni.hideLoading();
			}
		});
	});
};