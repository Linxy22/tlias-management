<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import * as echarts from "echarts";
import {
  getEmpJobDataApi,
  getEmpGenderDataApi,
  getEmpDeptDataApi,
} from "@/api/report";

const jobChartRef = ref(null);
const genderChartRef = ref(null);
const deptChartRef = ref(null);

let jobChart = null;
let genderChart = null;
let deptChart = null;

const handleResize = () => {
  jobChart?.resize();
  genderChart?.resize();
  deptChart?.resize();
};

const initJobChart = async () => {
  const result = await getEmpJobDataApi();
  const { jobList, dataList } = result.data;

  jobChart = echarts.init(jobChartRef.value);
  jobChart.setOption({
    title: { text: "岗位分布", left: "center" },
    tooltip: { trigger: "axis" },
    xAxis: { type: "category", data: jobList, axisLabel: { rotate: 30 } },
    yAxis: { type: "value", name: "人数" },
    series: [
      {
        type: "bar",
        data: dataList,
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: "#5470c6" },
            { offset: 1, color: "#91cc75" },
          ]),
        },
        barWidth: "40%",
      },
    ],
    grid: { left: "3%", right: "4%", bottom: "10%", containLabel: true },
  });
};

const initGenderChart = async () => {
  const result = await getEmpGenderDataApi();
  const genderData = result.data;

  genderChart = echarts.init(genderChartRef.value);
  genderChart.setOption({
    title: { text: "性别占比", left: "center" },
    tooltip: { trigger: "item", formatter: "{b}: {c}人 ({d}%)" },
    legend: { bottom: "5%", left: "center" },
    series: [
      {
        type: "pie",
        radius: ["35%", "65%"],
        center: ["50%", "50%"],
        avoidLabelOverlap: true,
        itemStyle: { borderRadius: 8, borderColor: "#fff", borderWidth: 2 },
        label: { show: true, formatter: "{b}: {c}人 ({d}%)" },
        emphasis: {
          label: { show: true, fontSize: 16, fontWeight: "bold" },
        },
        data: genderData,
      },
    ],
  });
};

const initDeptChart = async () => {
  const result = await getEmpDeptDataApi();
  const deptData = result.data;
  const names = deptData.map((item) => item.name);
  const counts = deptData.map((item) => item.count);

  deptChart = echarts.init(deptChartRef.value);
  deptChart.setOption({
    title: { text: "部门人数", left: "center" },
    tooltip: { trigger: "axis" },
    xAxis: { type: "category", data: names, axisLabel: { rotate: 30 } },
    yAxis: { type: "value", name: "人数" },
    series: [
      {
        type: "bar",
        data: counts,
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: "#ee6666" },
            { offset: 1, color: "#fac858" },
          ]),
        },
        barWidth: "40%",
      },
    ],
    grid: { left: "3%", right: "4%", bottom: "10%", containLabel: true },
  });
};

onMounted(() => {
  initJobChart();
  initGenderChart();
  initDeptChart();
  window.addEventListener("resize", handleResize);
});

onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
  jobChart?.dispose();
  genderChart?.dispose();
  deptChart?.dispose();
});
</script>

<template>
  <h1>员工信息统计</h1>
  <el-row :gutter="20" style="margin-top: 20px">
    <el-col :span="24">
      <div ref="jobChartRef" style="width: 100%; height: 400px"></div>
    </el-col>
  </el-row>
  <el-row :gutter="20" style="margin-top: 20px">
    <el-col :span="12">
      <div ref="genderChartRef" style="width: 100%; height: 400px"></div>
    </el-col>
    <el-col :span="12">
      <div ref="deptChartRef" style="width: 100%; height: 400px"></div>
    </el-col>
  </el-row>
</template>

<style scoped></style>
