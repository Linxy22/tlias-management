<script setup>
import { ref, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  queryPageApi,
  addApi,
  updateApi,
  deleteApi,
  getByIdApi,
} from "@/api/clazz";

const role = ref("");
const clazzList = ref([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const searchName = ref("");

// 搜索条件
const queryParams = ref({ page: 1, pageSize: 10, name: "" });

// 弹窗相关
const dialogVisible = ref(false);
const dialogTitle = ref("新增班级");
const clazzForm = ref({ id: null, name: "", beginDate: "", endDate: "" });

// 查询班级列表
const queryPage = async () => {
  queryParams.value.page = currentPage.value;
  queryParams.value.pageSize = pageSize.value;
  queryParams.value.name = searchName.value;
  const result = await queryPageApi(queryParams.value);
  if (result.code === 1) {
    clazzList.value = result.data.rows;
    total.value = result.data.total;
  }
};

// 搜索
const handleSearch = () => {
  currentPage.value = 1;
  queryPage();
};

// 重置
const handleReset = () => {
  searchName.value = "";
  handleSearch();
};

// 翻页
const handlePageChange = (page) => {
  currentPage.value = page;
  queryPage();
};

// 新增
const handleAdd = () => {
  dialogTitle.value = "新增班级";
  clazzForm.value = { id: null, name: "", beginDate: "", endDate: "" };
  dialogVisible.value = true;
};

// 编辑
const handleEdit = async (id) => {
  dialogTitle.value = "编辑班级";
  const result = await getByIdApi(id);
  if (result.code === 1) {
    clazzForm.value = { ...result.data };
    dialogVisible.value = true;
  }
};

// 提交表单
const handleSubmit = async () => {
  let result;
  if (clazzForm.value.id) {
    result = await updateApi(clazzForm.value);
  } else {
    result = await addApi(clazzForm.value);
  }
  if (result.code === 1) {
    ElMessage.success("操作成功");
    dialogVisible.value = false;
    queryPage();
  } else {
    ElMessage.error(result.msg || "操作失败");
  }
};

// 删除
const handleDelete = (id) => {
  ElMessageBox.confirm("确定要删除该班级吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      const result = await deleteApi(id);
      if (result.code === 1) {
        ElMessage.success("删除成功");
        queryPage();
      } else {
        ElMessage.error(result.msg || "删除失败");
      }
    })
    .catch(() => {});
};

onMounted(() => {
  queryPage();
  role.value = localStorage.getItem("role") || "";
});
</script>

<template>
  <h1>班级管理</h1>

  <!-- 搜索栏 -->
  <el-form :inline="true" style="margin-top: 20px">
    <el-form-item label="班级名称">
      <el-input v-model="searchName" placeholder="请输入班级名称" clearable />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSearch">查询</el-button>
      <el-button @click="handleReset">重置</el-button>
    </el-form-item>
  </el-form>

  <!-- 新增按钮 -->
  <el-button
    type="primary"
    style="margin-bottom: 15px"
    @click="handleAdd"
    v-if="role === '1'"
    >+ 新增班级</el-button
  >

  <!-- 表格 -->
  <el-table :data="clazzList" border style="width: 100%">
    <el-table-column type="index" label="序号" width="80" align="center" />
    <el-table-column prop="name" label="班级名称" align="center" />
    <el-table-column
      prop="beginDate"
      label="开始日期"
      width="180"
      align="center"
    />
    <el-table-column
      prop="endDate"
      label="结束日期"
      width="180"
      align="center"
    />
    <el-table-column
      prop="updateTime"
      label="最后修改时间"
      width="220"
      align="center"
    />
    <el-table-column
      label="操作"
      width="200"
      align="center"
      v-if="role === '1'"
    >
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row.id)"
          >修改</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.row.id)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>

  <!-- 分页 -->
  <el-pagination
    style="margin-top: 20px; justify-content: flex-end"
    v-model:current-page="currentPage"
    :page-size="pageSize"
    :total="total"
    layout="total, prev, pager, next"
    @current-change="handlePageChange"
  />

  <!-- 新增/编辑弹窗 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
    <el-form :model="clazzForm" label-width="100px">
      <el-form-item label="班级名称">
        <el-input v-model="clazzForm.name" placeholder="请输入班级名称" />
      </el-form-item>
      <el-form-item label="开始日期">
        <el-date-picker
          v-model="clazzForm.beginDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择开始日期"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="结束日期">
        <el-date-picker
          v-model="clazzForm.endDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择结束日期"
          style="width: 100%"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </template>
  </el-dialog>
</template>

<style scoped></style>
