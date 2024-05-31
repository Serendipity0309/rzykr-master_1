import request from '@/utils/request'

// 查询分院管理列表
export function listSchoolDivision(query) {
	return request({
		url: '/schoolDivision/schoolDivision/list',
		method: 'get',
		params: query
	})
}

// 查询分院管理详细
export function getSchoolDivision(id) {
	return request({
		url: '/schoolDivision/schoolDivision/' + id,
		method: 'get'
	})
}

// 新增分院管理
export function addSchoolDivision(data) {
	return request({
		url: '/schoolDivision/schoolDivision',
		method: 'post',
		data: data
	})
}

// 修改分院管理
export function updateSchoolDivision(data) {
	return request({
		url: '/schoolDivision/schoolDivision',
		method: 'put',
		data: data
	})
}

// 删除分院管理
export function delSchoolDivision(id) {
	return request({
		url: '/schoolDivision/schoolDivision/' + id,
		method: 'delete'
	})
}


// 删除分院管理
export function getSchoolDivisionLogo(id) {
	return request({
		url: '/schoolDivision/schoolDivision/getSchoolDivisionLogo/' + id,
		method: 'get'
	})
}
