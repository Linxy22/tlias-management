<script setup>
import { ref, onMounted } from "vue";
import { queryPageApi } from "@/api/log";

const logList = ref([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);

// 查询日志
const queryPage = async () => {
  const result = await queryPageApi({
    page: currentPage.value,
    pageSize: pageSize.value,
  });
  if (result.code === 1) {
    logList.value = result.data.rows;
    total.value = result.data.total;
  }
};

// 翻页
const handlePageChange = (page) => {
  currentPage.value = page;
  queryPage();
};

onMounted(() => {
  queryPage();
});
</script>

<template>
  <h1>日志信息统计</h1>

  <el-table :data="logList" border style="width: 100%; margin-top: 20px">
    <el-table-column type="index" label="序号" width="80" align="center" />
    <el-table-column
      prop="operateTime"
      label="操作时间"
      width="250"
      align="center"
    />
    <el-table-column prop="info" label="详细信息" align="left" />
  </el-table>

  <el-pagination
    style="margin-top: 20px; justify-content: flex-end"
    v-model:current-page="currentPage"
    :page-size="pageSize"
    :total="total"
    layout="total, prev, pager, next"
    @current-change="handlePageChange"
  />
</template>

<style scoped></style>
