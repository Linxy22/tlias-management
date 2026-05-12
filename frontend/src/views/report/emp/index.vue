<script setup>
import { ref, onMounted } from 'vue'
import { getEmpReportApi } from '@/api/report'

const empReport = ref([])

onMounted(async () => {
  const result = await getEmpReportApi()
  if (result.code) {
    empReport.value = result.data
  }
})
</script>

<template>
  <h1>员工信息统计</h1>

  <el-row :gutter="20">
    <el-col :span="8">
      <el-card shadow="hover">
        <template #header>
          <span>总览</span>
        </template>
        <div class="stat-item">
          <span class="stat-label">员工总数</span>
          <span class="stat-value">{{ empReport.reduce((sum, item) => sum + item.count, 0) }}</span>
        </div>
      </el-card>
    </el-col>
    <el-col :span="16">
      <el-card shadow="hover">
        <template #header>
          <span>各部门员工分布</span>
        </template>
        <el-table :data="empReport" border stripe>
          <el-table-column prop="name" label="部门名称" align="center" />
          <el-table-column prop="count" label="员工人数" align="center" />
          <el-table-column label="占比" align="center">
            <template #default="scope">
              <div class="bar-wrapper">
                <div
                  class="bar"
                  :style="{
                    width: (empReport.reduce((s, i) => s + i.count, 0) ? (scope.row.count / empReport.reduce((s, i) => s + i.count, 0) * 100) + '%' : '0%')
                  }"
                ></div>
                <span class="bar-text">
                  {{ empReport.reduce((s, i) => s + i.count, 0) ? (scope.row.count / empReport.reduce((s, i) => s + i.count, 0) * 100).toFixed(1) : 0 }}%
                </span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>
.stat-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
}
.stat-label {
  font-size: 16px;
  color: #666;
}
.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #409eff;
}
.bar-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
}
.bar {
  height: 20px;
  background: linear-gradient(90deg, #409eff, #79bbff);
  border-radius: 4px;
  min-width: 4px;
  transition: width 0.3s;
}
.bar-text {
  font-size: 13px;
  color: #666;
  white-space: nowrap;
}
</style>
