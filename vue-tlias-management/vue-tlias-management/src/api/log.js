import request from "@/utils/request";

// 分页查询日志
export const queryPageApi = (params) => request.get("/logs", { params });
