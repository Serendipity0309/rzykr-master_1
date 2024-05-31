import request from '@/utils/request'
// 查询考试安排数据列表
export function listData(query) {
  return request({
    url: '/system/notice/updateNotice',
    method: 'get',
    params: query
  })
}
// 查询考试安排数据详细
export function getData() {
  return request({
    url: '/system/notice/lists',
    method: 'get',
  })
}
// 修改考试安排数据  /system/data/field
export function updateData(data) {
  return request({
    url: '/system/notice/update',
    method: 'put',
    data: data
  })
}
// 修改考试安排数据 
export function selectTableField() {
  return request({
    url: '/system/data/field',
    method: 'get',
  })
}