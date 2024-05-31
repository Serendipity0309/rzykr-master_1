import request from '@/utils/request'
// 查询考试安排数据列表
export function listData(query) {
  return request({
    url: '/system/data/list',
    method: 'get',
    params: query,
  })
}
// 查询考试安排数据列表
export function importDate(data) {
  debugger
  return request({
    url: '/system/data/importdate',
    method: 'put',
    data: data
  })
}
// 查询考试安排数据详细
export function getData(id) {
  return request({
    url: '/system/data/getData?id=' + id,
    method: 'get',
  })
}
// 新增考试安排数据
export function addData(data) {
  return request({
    url: '/system/data',
    method: 'post',
    data: data
  })
}
// 修改考试安排数据
export function updateData(data) {
  return request({
    url: '/system/data',
    method: 'put',
    data: data
  })
}
// 删除考试安排数据
export function delData(id) {
  return request({
    url: '/system/data/' + id,
    method: 'delete'
  })
}
// 删除考试安排数据
export function getqrcode(id) {
  return request({
    url: '/system/notice/getImage/' + id,
    method: 'get'
  })
}
// 查询所有老师
export function getAllteacher() {
  return request({
    url: '/system/data/allteacher',
    method: 'get'
  })
}

// 查询所同时间而且容量大于本考场的考场
export function getSyncPlace(id) {
  return request({
    url: `/system/data/syncPlace/${id}`,
    method: 'get'
  })
}


// 查询所同时间而且容量大于本考场的考场
export function exchange(oringeId, exchangeId) {
  return request({
    url: `/system/data/syncPlace/exchangeExamPlace/${oringeId}/${exchangeId}`,
    method: 'post'
  })
}