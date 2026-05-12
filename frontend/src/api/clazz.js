import request from '@/utils/request'

export const queryPageApi = (name, page, pageSize) =>
  request.get('/clazzs', { params: { name, page, pageSize } })

export const addApi = (data) => request.post('/clazzs', data)

export const queryInfoApi = (id) => request.get(`/clazzs/${id}`)

export const updateApi = (data) => request.put('/clazzs', data)

export const deleteApi = (ids) => request.delete('/clazzs', { params: { ids } })
