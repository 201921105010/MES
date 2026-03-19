<template>
  <div class="gantt-chart">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>排程甘特图</span>
          <el-button-group>
            <el-button type="primary" size="small">按工作中心</el-button>
            <el-button size="small">按订单</el-button>
          </el-button-group>
        </div>
      </template>
      <div id="gantt" style="width: 100%; height: 500px;"></div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'

let chart: echarts.ECharts | null = null

onMounted(() => {
  chart = echarts.init(document.getElementById('gantt') as HTMLElement)
  
  // 模拟简单的 ECharts 甘特图配置
  const option = {
    tooltip: {
      formatter: function (params: any) {
        return params.marker + params.name + ': ' + params.value[1] + ' - ' + params.value[2]
      }
    },
    xAxis: {
      type: 'time',
      position: 'top'
    },
    yAxis: {
      type: 'category',
      data: ['CNC加工中心', '装配产线', '质检工位']
    },
    series: [
      {
        type: 'custom',
        renderItem: function (params: any, api: any) {
          const categoryIndex = api.value(0)
          const start = api.coord([api.value(1), categoryIndex])
          const end = api.coord([api.value(2), categoryIndex])
          const height = api.size([0, 1])[1] * 0.6
          
          return {
            type: 'rect',
            shape: echarts.graphic.clipRectByRect({
              x: start[0],
              y: start[1] - height / 2,
              width: end[0] - start[0],
              height: height
            }, {
              x: params.coordSys.x,
              y: params.coordSys.y,
              width: params.coordSys.width,
              height: params.coordSys.height
            }),
            style: api.style()
          }
        },
        encode: {
          x: [1, 2],
          y: 0
        },
        data: [
          {
            name: '工单 WO-001 (下料)',
            value: [0, new Date('2026-03-19T08:00:00').getTime(), new Date('2026-03-19T12:00:00').getTime()],
            itemStyle: { color: '#5470c6' }
          },
          {
            name: '工单 WO-001 (装配)',
            value: [1, new Date('2026-03-19T13:00:00').getTime(), new Date('2026-03-19T17:00:00').getTime()],
            itemStyle: { color: '#91cc75' }
          }
        ]
      }
    ]
  }
  
  chart.setOption(option)
})

onUnmounted(() => {
  if (chart) {
    chart.dispose()
  }
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
