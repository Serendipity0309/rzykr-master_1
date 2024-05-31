import request from '@/utils/request'

// 查询督导列表
export function listSupervisor(query) {
	return request({
		url: '/system/supervisor/list',
		method: 'get',
		params: query
	})
}

// 查询督导详细
export function getSupervisor(id) {
	return request({
		url: '/system/supervisor/' + id,
		method: 'get'
	})
}
// 查询督导详细
export function getSupervisorListByUser(query) {
	return request({
		url: '/system/supervisor/SupervisorListByUser',
		method: 'get',
		params: query
	})
}

// 新增督导
export function addSupervisor(data) {
	return request({
		url: '/system/supervisor',
		method: 'post',
		data: data
	})
}

// 修改督导
export function updateSupervisor(data) {
	return request({
		url: '/system/supervisor',
		method: 'put',
		data: data
	})
}

// 删除督导
export function delSupervisor(id) {
	return request({
		url: '/system/supervisor/' + id,
		method: 'delete'
	})
}


// 获取老师当天监考信息
export function getCurrDayData(index) {
	return request({
		url: `/system/info/currdayInfo/${index}`,
		method: 'get'
	})
}
// 获取老师当天监考信息
export function getCurrExam() {
	return request({
		url: '/system/info/currexam',
		method: 'get'
	})
}
// 根据用户查询督导信息
export function selectSupervisorByUser(query) {
	return request({
		url: '/system/supervisor/selectSupervisorByUser',
		method: 'get',
		params: query
	})
}

// 根据用户查询督导信息
export function handAllow(data, isAllow) {
	return request({
		url: `/system/supervisor/handAllow/${isAllow}`,
		method: 'post',
		data: data
	})
}
// 根据用户查询督导信息
export function handShowDataById(id) {
	return request({
		url: `/system/supervisor/showData?id=` + id,
		method: 'get',
	})
}
