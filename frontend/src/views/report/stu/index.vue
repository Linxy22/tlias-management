<script setup>
import { ref, onMounted } from 'vue'
import { getStuReportApi } from '@/api/report'

const stuReport = ref([])

onMounted(async () => {
  const result = await getStuReportApi()
  if (result.code) {
    stuReport.value = result.data
  }
})
</script>

<template>
  <h1>学员信息统计</h1>

  <el-row :gutter="20">
    <el-col :span="8">
      <el-card shadow="hover">
        <template #header>
          <span>总览</span>
        </template>
        <div class="stat-item">
          <span class="stat-label">学员总数</span>
          <span class="stat-value">{{ stuReport.reduce((sum, item) => sum + item.count, 0) }}</span>
        </div>
      </el-card>
    </el-col>
    <el-col :span="16">
      <el-card shadow="hover">
        <template #header>
          <span>各班级学员分布</span>
        </template>
        <el-table :data="stuReport" border stripe>
          <el-table-column prop="name" label="班级名称" align="center" />
          <el-table-column prop="count" label="学员人数" align="center" />
          <el-table-column label="占比" align="center">
            <template #default="scope">
              <div class="bar-wrapper">
                <div
                  class="bar"
                  :style="{
                    width: (stuReport.reduce((s, i) => s + i.count, 0) ? (scope.row.count / stuReport.reduce((s, i) => s + i.count, 0) * 100) + '%' : '0%')
                  }"
                ></div>
                <span class="bar-text">
                  {{ stuReport.reduce((s, i) => s + i.count, 0) ? (scope.row.count / stuReport.reduce((s, i) => s + i.count, 0) * 100).toFixed(1) : 0 }}%
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
  color: #67c23a;
}
.bar-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
}
.bar {
  height: 20px;
  background: linear-gradient(90deg, #67c23a, #95d475);
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
