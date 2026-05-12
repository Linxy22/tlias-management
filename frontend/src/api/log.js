import request from '@/utils/request'

export const queryPageApi = (begin, end, page, pageSize) =>
  request.get('/logs', { params: { begin, end, page, pageSize } })
