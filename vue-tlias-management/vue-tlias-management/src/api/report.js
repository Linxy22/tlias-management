import request from "@/utils/request";

// 员工岗位分布数据
export const getEmpJobDataApi = () => request.get("/report/empJobData");

// 员工性别分布数据
export const getEmpGenderDataApi = () => request.get("/report/empGenderData");

// 各部门员工人数数据
export const getEmpDeptDataApi = () => request.get("/report/emp");

// 各班级学员人数数据
export const getStuClazzDataApi = () => request.get("/report/stu");
