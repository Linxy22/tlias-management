import request from '@/utils/request'

export const getEmpReportApi = () => request.get('/report/emp')

export const getStuReportApi = () => request.get('/report/stu')
