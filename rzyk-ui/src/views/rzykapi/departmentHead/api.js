import request from '@/utils/request'

// 查询监考老师信息列表
export function listDepartmentHead(query) {
	return request({
		url: '/departmentHead/departmentHead/list',
		method: 'get',
		params: query
	})
}

// 查询监考老师信息详细
export function getDepartmentHead(id) {
	return request({
		url: '/departmentHead/departmentHead/' + id,
		method: 'get'
	})
}

// 新增监考老师信息
export function addDepartmentHead(data) {
	return request({
		url: '/departmentHead/departmentHead',
		method: 'post',
		data: data
	})
}

// 修改监考老师信息
export function updateDepartmentHead(data) {
	return request({
		url: '/departmentHead/departmentHead',
		method: 'put',
		data: data
	})
}

// 删除监考老师信息
export function delDepartmentHead(id) {
	return request({
		url: '/departmentHead/departmentHead/' + id,
		method: 'delete'
	})
}
