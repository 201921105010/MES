<template>
  <div class="order-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>生产订单管理</span>
          <el-button type="primary" @click="handleCreate">新建订单</el-button>
        </div>
      </template>
      
      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="productCode" label="产品编码" width="150" />
        <el-table-column prop="orderQty" label="订单数量" width="120" />
        <el-table-column prop="dueTime" label="交期" width="180" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" type="success" @click="handleExplodeBOM(scope.row)">展开BOM</el-button>
            <el-button size="small" type="primary" @click="handleSchedule(scope.row)">排程</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const tableData = ref([
  {
    orderNo: 'MO202603190001',
    productCode: 'P-10001',
    orderQty: 100,
    dueTime: '2026-03-25 18:00:00',
    status: 'NEW'
  },
  {
    orderNo: 'MO202603190002',
    productCode: 'P-10002',
    orderQty: 50,
    dueTime: '2026-03-26 12:00:00',
    status: 'PLANNED'
  }
])

const getStatusType = (status: string) => {
  const map: Record<string, string> = {
    'NEW': 'info',
    'PLANNED': 'primary',
    'IN_PROGRESS': 'warning',
    'COMPLETED': 'success'
  }
  return map[status] || 'info'
}

const handleCreate = () => {
  // 模拟创建
}

const handleExplodeBOM = (row: any) => {
  // 模拟BOM展开
  console.log('Explode BOM for:', row.orderNo)
}

const handleSchedule = (row: any) => {
  // 模拟排程
  console.log('Schedule for:', row.orderNo)
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
