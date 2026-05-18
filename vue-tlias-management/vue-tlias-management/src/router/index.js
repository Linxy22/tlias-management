import { createRouter, createWebHistory } from "vue-router";
import { ElMessageBox } from "element-plus";

import IndexView from "@/views/index/index.vue";
import ClazzView from "@/views/clazz/index.vue";
import DeptView from "@/views/dept/index.vue";
import EmpView from "@/views/emp/index.vue";
import LogView from "@/views/log/index.vue";
import StuView from "@/views/stu/index.vue";
import EmpReportView from "@/views/report/emp/index.vue";
import StuReportView from "@/views/report/stu/index.vue";
import LayoutView from "@/views/layout/index.vue";
import LoginView from "@/views/login/index.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "",
      component: LayoutView,
      redirect: "/login", // 启动后先进入登录页
      children: [
        { path: "index", name: "index", component: IndexView },
        { path: "clazz", name: "clazz", component: ClazzView },
        { path: "stu", name: "stu", component: StuView },
        { path: "dept", name: "dept", component: DeptView },
        { path: "emp", name: "emp", component: EmpView },
        { path: "log", name: "log", component: LogView },
        { path: "report/emp", name: "empReport", component: EmpReportView },
        { path: "report/stu", name: "stuReport", component: StuReportView },
      ],
    },
    { path: "/login", name: "login", component: LoginView },
  ],
});

// 需要管理员权限的路由
const adminRoutes = ["/report/emp", "/report/stu", "/log"];

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");

  // 访问登录页直接放行
  if (to.path === "/login") {
    next();
    return;
  }

  // 未登录跳转到登录页
  if (!token) {
    next("/login");
    return;
  }

  // 普通用户访问管理员路由，弹出警告
  const role = localStorage.getItem("role");
  if (adminRoutes.includes(to.path) && role !== "1") {
    ElMessageBox.alert(
      "对不起，您没有管理员权限，无法看到数据统计内容",
      "权限不足",
      { confirmButtonText: "确定", type: "warning" }
    );
    next(false);
    return;
  }

  next();
});

export default router;
