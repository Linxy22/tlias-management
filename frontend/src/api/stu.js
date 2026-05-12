import request from '@/utils/request'

export const queryPageApi = (name, gender, begin, end, page, pageSize) =>
  request.get('/students', { params: { name, gender, begin, end, page, pageSize } })

export const addApi = (data) => request.post('/students', data)

export const queryInfoApi = (id) => request.get(`/students/${id}`)

export const updateApi = (data) => request.put('/students', data)

export const deleteApi = (ids) => request.delete('/students', { params: { ids } })
