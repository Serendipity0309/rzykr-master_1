import request from '@/utils/request'

// 查询监考老师信息列表
export function listSuperintendent(query) {
	return request({
		url: '/system/superintendent/list',
		method: 'get',
		params: query
	})
}

// 查询监考老师信息详细
export function getSuperintendent(id) {
	return request({
		url: '/system/superintendent/' + id,
		method: 'get'
	})
}

// 新增监考老师信息
export function addSuperintendent(data) {
	return request({
		url: '/system/superintendent',
		method: 'post',
		data: data
	})
}

// 修改监考老师信息
export function updateSuperintendent(data) {
	return request({
		url: '/system/superintendent',
		method: 'put',
		data: data
	})
}

// 删除监考老师信息
export function delSuperintendent(id) {
	return request({
		url: '/system/superintendent/' + id,
		method: 'delete'
	})
}
