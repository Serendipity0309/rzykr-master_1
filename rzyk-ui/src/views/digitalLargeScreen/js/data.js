import request from '@/utils/request'
// 查询考试安排数据列表
export function getcountdata(query) {
  return request({
    url: '/system/log/count',
    method: 'get',
    params: query,
  })
}