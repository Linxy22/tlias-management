<script setup>
import { ref, watch, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryPageApi, addApi, queryInfoApi, updateApi, deleteApi } from '@/api/stu'
import { queryPageApi as queryAllClazzApi } from '@/api/clazz'

const stuList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const background = ref(true)
const total = ref(0)
const classes = ref([])
const token = ref('')

const searchStu = ref({ name: '', gender: '', date: [], begin: '', end: '' })

const search = async () => {
  const result = await queryPageApi(
    searchStu.value.name, searchStu.value.gender,
    searchStu.value.begin, searchStu.value.end,
    currentPage.value, pageSize.value
  )
  if (result.code) {
    stuList.value = result.data.rows
    total.value = result.data.total
  }
}

watch(() => searchStu.value.date, (newVal) => {
  if (newVal && newVal.length === 2) {
    searchStu.value.begin = newVal[0]
    searchStu.value.end = newVal[1]
  } else {
    searchStu.value.begin = ''
    searchStu.value.end = ''
  }
})

onMounted(() => {
  search()
  queryAllClasses()
  const loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if (loginUser && loginUser.token) {
    token.value = loginUser.token
  }
})

const queryAllClasses = async () => {
  const result = await queryAllClazzApi('', 1, 1000)
  if (result.code) {
    classes.value = result.data.rows || result.data
  }
}

const handleSizeChange = () => { search() }
const handleCurrentChange = () => { search() }

const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
const degrees = ref(['高中', '大专', '本科', '硕士', '博士'])

const dialogVisible = ref(false)
const dialogTitle = ref('新增学员')
const stuForm = ref({
  name: '', gender: '', phone: '', degree: '',
  clazzId: '', entryDate: '', graduationDate: '', idCard: '', image: ''
})
const stuFormRef = ref(null)

const rules = {
  name: [
    { required: true, message: '请输入学员姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ],
  clazzId: [{ required: true, message: '请选择所属班级', trigger: 'change' }],
  entryDate: [{ required: true, message: '请选择入学日期', trigger: 'change' }],
  degree: [{ required: true, message: '请选择学历', trigger: 'change' }]
}

const add = () => {
  dialogTitle.value = '新增学员'
  dialogVisible.value = true
  stuForm.value = {
    name: '', gender: '', phone: '', degree: '',
    clazzId: '', entryDate: '', graduationDate: '', idCard: '', image: ''
  }
  if (stuFormRef.value) stuFormRef.value.resetFields()
}

const edit = async (id) => {
  const result = await queryInfoApi(id)
  if (result.code) {
    dialogTitle.value = '修改学员'
    dialogVisible.value = true
    stuForm.value = result.data
  }
}

const save = async () => {
  if (!stuFormRef.value) return
  await stuFormRef.value.validate(async (valid) => {
    if (!valid) return
    let result
    if (stuForm.value.id) {
      result = await updateApi(stuForm.value)
    } else {
      result = await addApi(stuForm.value)
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
  ElMessageBox.confirm('您确认删除该学员吗?', '提示', {
    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
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
  ElMessageBox.confirm('您确认删除这些学员吗?', '提示', {
    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
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

const handleAvatarSuccess = (response) => {
  stuForm.value.image = response.data
}

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}
</script>

<template>
  <h1>学员管理</h1>

  <div class="container">
    <el-form :inline="true" :model="searchStu">
      <el-form-item label="姓名">
        <el-input v-model="searchStu.name" placeholder="请输入学员姓名" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchStu.gender" placeholder="请选择" clearable>
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="入学日期">
        <el-date-picker
          v-model="searchStu.date"
          type="daterange"
          range-separator="到"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="searchStu = {name: '', gender: '', date: [], begin: '', end: ''}; search()">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="container">
    <el-button type="primary" @click="add">+ 新增学员</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <div class="container">
    <el-table :data="stuList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="70" align="center" />
      <el-table-column prop="name" label="姓名" width="120" align="center" />
      <el-table-column label="性别" width="80" align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="140" align="center" />
      <el-table-column prop="degree" label="学历" width="100" align="center" />
      <el-table-column prop="clazzName" label="所属班级" align="center" />
      <el-table-column prop="entryDate" label="入学日期" width="140" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="180" align="center" />
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

  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
    <el-form :model="stuForm" :rules="rules" ref="stuFormRef" label-width="100px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="stuForm.name" placeholder="请输入学员姓名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="stuForm.gender" placeholder="请选择" style="width: 100%;">
              <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="stuForm.phone" placeholder="请输入手机号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="学历" prop="degree">
            <el-select v-model="stuForm.degree" placeholder="请选择学历" style="width: 100%;">
              <el-option v-for="d in degrees" :key="d" :label="d" :value="d"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属班级" prop="clazzId">
            <el-select v-model="stuForm.clazzId" placeholder="请选择班级" style="width: 100%;">
              <el-option v-for="c in classes" :key="c.id" :label="c.name" :value="c.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="身份证号">
            <el-input v-model="stuForm.idCard" placeholder="请输入身份证号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="入学日期" prop="entryDate">
            <el-date-picker v-model="stuForm.entryDate" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="毕业日期">
            <el-date-picker v-model="stuForm.graduationDate" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="头像">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :headers="{'token': token}"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="stuForm.image" :src="stuForm.image" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
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
.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  border: 1px dashed var(--el-border-color);
}
</style>
