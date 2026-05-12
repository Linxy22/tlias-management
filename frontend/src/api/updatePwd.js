import request from '@/utils/request'

export const updatePwdApi = (data) => request.patch('/updatePwd', data)
