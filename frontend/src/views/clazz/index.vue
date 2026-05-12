<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryPageApi, addApi, queryInfoApi, updateApi, deleteApi } from '@/api/clazz'

const clazzList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const background = ref(true)
const total = ref(0)
const searchName = ref('')

const search = async () => {
  const result = await queryPageApi(searchName.value, currentPage.value, pageSize.value)
  if (result.code) {
    clazzList.value = result.data.rows
    total.value = result.data.total
  }
}

onMounted(() => {
  search()
})

const handleSizeChange = () => { search() }
const handleCurrentChange = () => { search() }

const dialogVisible = ref(false)
const dialogTitle = ref('新增班级')
const clazzForm = ref({ name: '', beginDate: '', endDate: '' })
const clazzFormRef = ref(null)

const rules = {
  name: [
    { required: true, message: '请输入班级名称', trigger: 'blur' },
    { min: 2, max: 30, message: '班级名称长度在2到30个字符之间', trigger: 'blur' }
  ],
  beginDate: [{ required: true, message: '请选择开班日期', trigger: 'change' }],
  endDate: [{ required: true, message: '请选择结课日期', trigger: 'change' }]
}

const add = () => {
  dialogTitle.value = '新增班级'
  dialogVisible.value = true
  clazzForm.value = { name: '', beginDate: '', endDate: '' }
}

const edit = async (id) => {
  const result = await queryInfoApi(id)
  if (result.code) {
    dialogTitle.value = '修改班级'
    dialogVisible.value = true
    clazzForm.value = result.data
  }
}

const save = async () => {
  if (!clazzFormRef.value) return
  await clazzFormRef.value.validate(async (valid) => {
    if (!valid) return
    let result
    if (clazzForm.value.id) {
      result = await updateApi(clazzForm.value)
    } else {
      result = await addApi(clazzForm.value)
    }
    if (result.code) {
      ElMessage.success('操作成功')
      dialogVisible.value = false
      search()
    } else {
      ElMessage.error(result.msg)
    }
  })
}

const deleteById = (id) => {
  ElMessageBox.confirm('此操作将永久删除该班级, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    const result = await deleteApi(id)
    if (result.code) {
      ElMessage.success('删除成功')
      search()
    }
  })
}

const selectedIds = ref([])
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

const deleteByIds = () => {
  ElMessageBox.confirm('您确认删除这些班级吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    if (selectedIds.value.length === 0) {
      ElMessage.info('您没有选择任何要删除的数据')
      return
    }
    const result = await deleteApi(selectedIds.value)
    if (result.code) {
      ElMessage.success('删除成功')
      search()
    }
  })
}
</script>

<template>
  <h1>班级管理</h1>

  <div class="container">
    <el-form :inline="true">
      <el-form-item label="班级名称">
        <el-input v-model="searchName" placeholder="请输入班级名称" clearable @keyup.enter="search"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="searchName = ''; search()">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="container">
    <el-button type="primary" @click="add">+ 新增班级</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <div class="container">
    <el-table :data="clazzList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="80" align="center" />
      <el-table-column prop="name" label="班级名称" align="center" />
      <el-table-column prop="beginDate" label="开班日期" width="180" align="center" />
      <el-table-column prop="endDate" label="结课日期" width="180" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div class="container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 30, 50]"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>

  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
    <el-form :model="clazzForm" :rules="rules" ref="clazzFormRef" label-width="100px">
      <el-form-item label="班级名称" prop="name">
        <el-input v-model="clazzForm.name" placeholder="请输入班级名称"></el-input>
      </el-form-item>
      <el-form-item label="开班日期" prop="beginDate">
        <el-date-picker v-model="clazzForm.beginDate" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 100%;"></el-date-picker>
      </el-form-item>
      <el-form-item label="结课日期" prop="endDate">
        <el-date-picker v-model="clazzForm.endDate" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 100%;"></el-date-picker>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.container {
  margin: 10px 0px;
}
</style>
