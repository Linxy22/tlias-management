import request from '@/utils/request'

//查询全部部门
export const queryAllApi = () => request.get('/depts')

//添加部门
export const addDeptApi = (data) => request.post('/depts', data)

//根据id查询部门
export const queryInfoApi = (id) => request.get(`/depts/${id}`)

//修改
export const  updateDeptApi = (data) => request.put('/depts', data)

//删除
export const deleteDeptApi = (id) => request.delete(`/depts/${id}`)
