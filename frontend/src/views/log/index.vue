<script setup>
import { ref, watch, onMounted } from 'vue'
import { queryPageApi } from '@/api/log'

const logList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const background = ref(true)
const total = ref(0)

const searchForm = ref({ date: [], begin: '', end: '' })

const search = async () => {
  const result = await queryPageApi(
    searchForm.value.begin, searchForm.value.end,
    currentPage.value, pageSize.value
  )
  if (result.code) {
    logList.value = result.data.rows
    total.value = result.data.total
  }
}

watch(() => searchForm.value.date, (newVal) => {
  if (newVal && newVal.length === 2) {
    searchForm.value.begin = newVal[0]
    searchForm.value.end = newVal[1]
  } else {
    searchForm.value.begin = ''
    searchForm.value.end = ''
  }
})

onMounted(() => {
  search()
})

const handleSizeChange = () => { search() }
const handleCurrentChange = () => { search() }
</script>

<template>
  <h1>日志管理</h1>

  <div class="container">
    <el-form :inline="true" :model="searchForm">
      <el-form-item label="操作时间">
        <el-date-picker
          v-model="searchForm.date"
          type="daterange"
          range-separator="到"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="searchForm = {date: [], begin: '', end: ''}; search()">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="container">
    <el-table :data="logList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="70" align="center" />
      <el-table-column prop="operator" label="操作人" width="120" align="center" />
      <el-table-column prop="operateTime" label="操作时间" width="180" align="center" />
      <el-table-column prop="className" label="类名" min-width="250" />
      <el-table-column prop="methodName" label="方法名" width="180" align="center" />
      <el-table-column prop="costTime" label="耗时(ms)" width="100" align="center" />
      <el-table-column prop="operateType" label="操作类型" width="120" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.operateType == 'insert'" type="success">新增</el-tag>
          <el-tag v-else-if="scope.row.operateType == 'update'" type="warning">修改</el-tag>
          <el-tag v-else-if="scope.row.operateType == 'delete'" type="danger">删除</el-tag>
          <el-tag v-else type="info">{{ scope.row.operateType || '查询' }}</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div class="container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[10, 20, 30, 50]"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<style scoped>
.container {
  margin: 10px 0px;
}
</style>
