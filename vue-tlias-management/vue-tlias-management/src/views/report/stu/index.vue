<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import * as echarts from "echarts";
import { getStuClazzDataApi } from "@/api/report";

const chartRef = ref(null);
let chart = null;

const handleResize = () => {
  chart?.resize();
};

const initChart = async () => {
  const result = await getStuClazzDataApi();
  const data = result.data;
  const names = data.map((item) => item.name);
  const counts = data.map((item) => item.count);

  chart = echarts.init(chartRef.value);
  chart.setOption({
    title: { text: "各班级学员人数统计", left: "center" },
    tooltip: { trigger: "axis" },
    xAxis: { type: "category", data: names, axisLabel: { rotate: 30 } },
    yAxis: { type: "value", name: "人数" },
    series: [
      {
        type: "bar",
        data: counts,
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: "#91cc75" },
            { offset: 1, color: "#5470c6" },
          ]),
        },
        barWidth: "40%",
      },
    ],
    grid: { left: "3%", right: "4%", bottom: "15%", containLabel: true },
  });
};

onMounted(() => {
  initChart();
  window.addEventListener("resize", handleResize);
});

onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
  chart?.dispose();
});
</script>

<template>
  <h1>学员信息统计</h1>
  <div
    ref="chartRef"
    style="width: 100%; height: 500px; margin-top: 20px"
  ></div>
</template>

<style scoped></style>
