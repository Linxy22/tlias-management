import request from "@/utils/request";

// 员工登录
export const loginApi = (data) => request.post("/login", data);

// 分页查询员工
export const queryPageApi = (params) => request.get("/emps", { params });

// 新增员工
export const addApi = (data) => request.post("/emps", data);

// 修改员工
export const updateApi = (data) => request.put("/emps", data);

// 删除员工
export const deleteApi = (ids) => request.delete("/emps", { params: { ids } });

// 根据ID查询员工
export const getByIdApi = (id) => request.get(`/emps/${id}`);
