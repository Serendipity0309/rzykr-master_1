	const DOMAIN_PREFIX = 'http://192.168.214.102:8080'
// #ifdef H5
	const WX_API_BASE =  DOMAIN_PREFIX  +'/api'
	const TERMINAL = 2
// #endif

// #ifdef APP-PLUS || APP-NVUE
	const TERMINAL = 3
// #endif

module.exports = {
   getMessage: WX_API_BASE + '/notice/getList',//消息列表查询      		
}
//页面通过h5打开时，这时候请求地址为：http://192.168.0.110:8080/api/notice/getList
