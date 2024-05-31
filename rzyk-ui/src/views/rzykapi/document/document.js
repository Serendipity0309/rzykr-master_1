import request from '@/utils/request'

// 查询材料列表
export function listDocument(query) {
  return request({
    url: '/system/document/list',
    method: 'get',
    params: query
  })
}

// 查询材料详细
export function getDocument(id) {
  return request({
    url: '/system/document/' + id,
    method: 'get'
  })
}

// 新增材料
export function addDocument(data) {
  return request({
    url: '/system/document',
    method: 'post',
    data: data
  })
}

// 修改材料
export function updateDocument(data) {
  return request({
    url: '/system/document',
    method: 'put',
    data: data
  })
}

// 删除材料
export function delDocument(id) {
  return request({
    url: '/system/document/' + id,
    method: 'delete'
  })
}
