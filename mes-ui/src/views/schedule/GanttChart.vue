<template>
  <div class="gantt-chart">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>排程甘特图</span>
          <el-button-group>
            <el-button :type="groupBy === 'workCenter' ? 'primary' : 'default'" size="small" @click="changeGroup('workCenter')">按工作中心</el-button>
            <el-button :type="groupBy === 'workOrder' ? 'primary' : 'default'" size="small" @click="changeGroup('workOrder')">按工单</el-button>
          </el-button-group>
        </div>
      </template>
      <div id="gantt" style="width: 100%; height: 500px;"></div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue'
import * as echarts from 'echarts'
import { ElMessage } from 'element-plus'
import { listScheduleTasks } from '../../api/schedule'
import type { ScheduleTask } from '../../types/schedule'

let chart: echarts.ECharts | null = null
const groupBy = ref<'workCenter' | 'workOrder'>('workCenter')
const tasks = ref<ScheduleTask[]>([])

const renderChart = () => {
  if (!chart) {
    return
  }
  const categories = Array.from(
    new Set(
      tasks.value.map((task) => groupBy.value === 'workCenter' ? task.workCenterCode : task.woNo)
    )
  )
  const data = tasks.value.map((task) => {
    const categoryValue = groupBy.value === 'workCenter' ? task.workCenterCode : task.woNo
    const categoryIndex = categories.indexOf(categoryValue)
    return {
      name: `工单 ${task.woNo} / 工序 ${task.opNo}`,
      value: [
        categoryIndex,
        new Date(task.planStartTime).getTime(),
        new Date(task.planEndTime).getTime()
      ],
      itemStyle: { color: '#5470c6' }
    }
  })
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
      data: categories
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
        data: data
      }
    ]
  }
  chart.setOption(option)
}

const loadTasks = async () => {
  try {
    tasks.value = await listScheduleTasks()
    renderChart()
  } catch {
    ElMessage.error('排程数据加载失败')
  }
}

const changeGroup = (value: 'workCenter' | 'workOrder') => {
  groupBy.value = value
  renderChart()
}

onMounted(async () => {
  chart = echarts.init(document.getElementById('gantt') as HTMLElement)
  await loadTasks()
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
