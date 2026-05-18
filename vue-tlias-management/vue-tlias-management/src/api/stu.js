import request from "@/utils/request";

// 分页查询学员
export const queryPageApi = (params) => request.get("/students", { params });

// 新增学员
export const addApi = (data) => request.post("/students", data);

// 修改学员
export const updateApi = (data) => request.put("/students", data);

// 删除学员
export const deleteApi = (ids) =>
  request.delete("/students", { params: { ids } });

// 根据ID查询学员
export const getByIdApi = (id) => request.get(`/students/${id}`);
