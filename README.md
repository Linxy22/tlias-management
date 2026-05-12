# Tlias 智能学习辅助系统

全栈企业级员工与学员管理平台，包含前端 Vue 3 管理界面与后端 Spring Boot RESTful API 服务。

## 技术栈

### 前端
- Vue 3 (Composition API + `<script setup>`)
- Vue Router 4（含导航守卫鉴权）
- Element Plus 2.x（含中文国际化）
- Axios（请求/响应拦截器 + JWT Token 自动携带）
- Vite 3.x

### 后端
- Spring Boot 3.5
- MyBatis + PageHelper 分页
- MySQL
- JWT 认证（TokenInterceptor）
- BCrypt 密码加密
- 阿里云 OSS 文件上传
- Maven

## 项目结构

```
tlias-management/
├── frontend/          # Vue 3 前端
│   ├── src/
│   │   ├── api/       # API 接口层
│   │   ├── views/     # 页面组件
│   │   ├── router/    # 路由配置 + 导航守卫
│   │   └── utils/     # Axios 封装
│   └── package.json
├── backend/           # Spring Boot 后端
│   ├── src/main/java/com/lxy/
│   │   ├── controller/  # REST 控制器
│   │   ├── service/     # 业务逻辑
│   │   ├── mapper/      # MyBatis Mapper
│   │   ├── pojo/        # 实体类
│   │   ├── config/      # 配置类
│   │   ├── interceptor/ # 拦截器
│   │   ├── exception/   # 全局异常处理
│   │   └── utils/       # 工具类（JWT、OSS）
│   └── pom.xml
└── README.md
```

## 功能模块

| 模块 | 前端 | 后端 |
|------|:--:|:--:|
| 登录/鉴权 | ✅ | ✅ |
| 首页 | ✅ | - |
| 部门管理（CRUD） | ✅ | ✅ |
| 员工管理（CRUD + 工作经历 + 头像上传） | ✅ | ✅ |
| 班级管理（CRUD） | ✅ | ✅ |
| 学员管理（CRUD + 头像上传） | ✅ | ✅ |
| 日志管理（日期检索） | ✅ | ✅ |
| 员工信息统计 | ✅ | ✅ |
| 学员信息统计 | ✅ | ✅ |
| 修改密码 | ✅ | ✅ |

## 快速开始

### 前端
```bash
cd frontend
npm install
npm run dev
```

### 后端
```bash
cd backend
mvn spring-boot:run
```

## 数据库表结构

需要创建以下数据库表：
- `dept` — 部门表
- `emp` — 员工表
- `emp_expr` — 员工工作经历表
- `emp_log` — 操作日志表
- `clazz` — 班级表
- `student` — 学员表

## 安全特性

- JWT Token 认证 + 拦截器校验
- BCrypt 密码加密存储
- API 响应不返回密码字段
- 敏感配置通过环境变量注入
- 前端路由导航守卫
- 全局异常处理
