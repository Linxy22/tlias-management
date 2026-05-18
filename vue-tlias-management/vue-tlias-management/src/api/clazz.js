import request from "@/utils/request";

// 分页查询班级
export const queryPageApi = (params) => request.get("/clazzs", { params });

// 查询所有班级（不分页，供下拉框使用）
export const queryAllApi = () =>
  request.get("/clazzs", { params: { page: 1, pageSize: 1000 } });

// 新增班级
export const addApi = (data) => request.post("/clazzs", data);

// 修改班级
export const updateApi = (data) => request.put("/clazzs", data);

// 删除班级
export const deleteApi = (ids) =>
  request.delete("/clazzs", { params: { ids } });

// 根据ID查询班级
export const getByIdApi = (id) => request.get(`/clazzs/${id}`);
