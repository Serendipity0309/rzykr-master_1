// 需要改变环境注释这一个地方就行
const env = "development";  // 开发环境
// const env="production";  // 生产环境
// const env="test";  // 测试环境


const url = require(`@/env/${env}.json`);
export default {
	"ENV_NAME": env,
	...url,
	"IMG_URL": "https://edo-iot.oss-cn-wulanchabu.aliyuncs.com/" // 图片获取基地址
}

