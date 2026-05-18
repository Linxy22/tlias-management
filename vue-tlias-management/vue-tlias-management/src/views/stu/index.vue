<script setup>
import { ref, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  queryPageApi,
  addApi,
  updateApi,
  deleteApi,
  getByIdApi,
} from "@/api/stu";
import { queryAllApi as queryAllClazz } from "@/api/clazz";
import request from "@/utils/request";

const role = ref("");
const stuList = ref([]);
const clazzOptions = ref([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const searchName = ref("");
const searchGender = ref("");
const searchDateRange = ref([]);

// 弹窗相关
const dialogVisible = ref(false);
const dialogTitle = ref("新增学员");
const stuForm = ref({
  id: null,
  name: "",
  gender: 1,
  phone: "",
  degree: "",
  clazzId: null,
  entryDate: "",
  graduationDate: "",
  idCard: "",
});

// 查询学员列表
const queryPage = async () => {
  const params = { page: currentPage.value, pageSize: pageSize.value };
  if (searchName.value) params.name = searchName.value;
  if (searchGender.value !== "") params.gender = searchGender.value;
  if (searchDateRange.value && searchDateRange.value.length === 2) {
    params.begin = searchDateRange.value[0];
    params.end = searchDateRange.value[1];
  }
  const result = await queryPageApi(params);
  if (result.code === 1) {
    stuList.value = result.data.rows;
    total.value = result.data.total;
  }
};

// 加载班级下拉
const loadClazzOptions = async () => {
  const result = await queryAllClazz();
  if (result.code === 1) {
    clazzOptions.value = result.data.rows;
  }
};

const handleSearch = () => {
  currentPage.value = 1;
  queryPage();
};
const handleReset = () => {
  searchName.value = "";
  searchGender.value = "";
  searchDateRange.value = [];
  handleSearch();
};
const handlePageChange = (page) => {
  currentPage.value = page;
  queryPage();
};

const handleAdd = () => {
  dialogTitle.value = "新增学员";
  stuForm.value = {
    id: null,
    name: "",
    gender: 1,
    phone: "",
    degree: "",
    clazzId: null,
    entryDate: "",
    graduationDate: "",
    idCard: "",
    image: "",
  };
  dialogVisible.value = true;
};

// 头像上传成功回调
const handleAvatarSuccess = (response) => {
  if (response.code === 1) {
    stuForm.value.image = response.data;
    ElMessage.success("上传成功");
  } else {
    ElMessage.error("上传失败");
  }
};

const handleEdit = async (id) => {
  dialogTitle.value = "编辑学员";
  const result = await getByIdApi(id);
  if (result.code === 1) {
    stuForm.value = { ...result.data };
    dialogVisible.value = true;
  }
};

const handleSubmit = async () => {
  let result = stuForm.value.id
    ? await updateApi(stuForm.value)
    : await addApi(stuForm.value);
  if (result.code === 1) {
    ElMessage.success("操作成功");
    dialogVisible.value = false;
    queryPage();
  } else {
    ElMessage.error(result.msg || "操作失败");
  }
};

const handleDelete = (id) => {
  ElMessageBox.confirm("确定要删除该学员吗？", "提示", {
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
  loadClazzOptions();
  role.value = localStorage.getItem("role") || "";
});
</script>

<template>
  <h1>学员管理</h1>

  <!-- 搜索栏 -->
  <el-form :inline="true" style="margin-top: 20px">
    <el-form-item label="姓名">
      <el-input v-model="searchName" placeholder="请输入姓名" clearable />
    </el-form-item>
    <el-form-item label="性别">
      <el-select
        v-model="searchGender"
        placeholder="全部"
        clearable
        style="width: 100px"
      >
        <el-option label="男" :value="1" />
        <el-option label="女" :value="2" />
      </el-select>
    </el-form-item>
    <el-form-item label="入学日期">
      <el-date-picker
        v-model="searchDateRange"
        type="daterange"
        value-format="YYYY-MM-DD"
        start-placeholder="开始"
        end-placeholder="结束"
      />
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
    >+ 新增学员</el-button
  >

  <!-- 表格 -->
  <el-table :data="stuList" border style="width: 100%">
    <el-table-column type="index" label="序号" width="60" align="center" />
    <el-table-column label="头像" width="80" align="center">
      <template #default="scope">
        <el-image v-if="scope.row.image" :src="scope.row.image" style="width: 40px; height: 40px; border-radius: 50%;" fit="cover">
          <template #error><div style="width:40px;height:40px;border-radius:50%;background:#eee;display:flex;align-items:center;justify-content:center;font-size:12px;">无</div></template>
        </el-image>
        <span v-else>-</span>
      </template>
    </el-table-column>
    <el-table-column prop="name" label="姓名" width="100" align="center" />
    <el-table-column label="性别" width="70" align="center">
      <template #default="scope">{{
        scope.row.gender === 1 ? "男" : "女"
      }}</template>
    </el-table-column>
    <el-table-column prop="phone" label="手机号" width="140" align="center" />
    <el-table-column prop="degree" label="学历" width="80" align="center" />
    <el-table-column prop="clazzName" label="班级" width="160" align="center" />
    <el-table-column
      prop="entryDate"
      label="入学日期"
      width="120"
      align="center"
    />
    <el-table-column
      prop="graduationDate"
      label="毕业日期"
      width="120"
      align="center"
    />
    <el-table-column
      prop="updateTime"
      label="最后修改时间"
      width="180"
      align="center"
    />
    <el-table-column
      label="操作"
      width="150"
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
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
    <el-form :model="stuForm" label-width="100px">
      <el-form-item label="姓名">
        <el-input v-model="stuForm.name" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="stuForm.gender">
          <el-radio :value="1">男</el-radio>
          <el-radio :value="2">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="stuForm.phone" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="学历">
        <el-input v-model="stuForm.degree" placeholder="请输入学历" />
      </el-form-item>
      <el-form-item label="班级">
        <el-select
          v-model="stuForm.clazzId"
          placeholder="请选择班级"
          style="width: 100%"
        >
          <el-option
            v-for="c in clazzOptions"
            :key="c.id"
            :label="c.name"
            :value="c.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="入学日期">
        <el-date-picker
          v-model="stuForm.entryDate"
          type="date"
          value-format="YYYY-MM-DD"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="毕业日期">
        <el-date-picker
          v-model="stuForm.graduationDate"
          type="date"
          value-format="YYYY-MM-DD"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="头像">
        <el-upload
          action="/api/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
        >
          <el-image
            v-if="stuForm.image"
            :src="stuForm.image"
            style="width: 100px; height: 100px; border-radius: 8px"
          />
          <el-button v-else type="primary" size="small">上传照片</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input v-model="stuForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </template>
  </el-dialog>
</template>

<style scoped></style>
