<template>
  <div class="order-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>生产订单管理</span>
          <el-button type="primary" @click="handleCreate">新建订单</el-button>
        </div>
      </template>
      
      <el-table :data="tableData" :loading="loading" style="width: 100%" border>
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
        <el-table-column label="操作" width="360">
          <template #default="scope">
            <el-button size="small" type="success" @click="handleExplodeBOM(scope.row)">展开BOM</el-button>
            <el-button size="small" type="primary" @click="handleSchedule(scope.row)">排程</el-button>
            <el-button size="small" type="warning" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { createOrder, deleteOrder, listOrders, updateOrder } from '../../api/order'
import { explodeBom } from '../../api/bom'
import { createWorkOrder } from '../../api/workorder'
import { createScheduleTask } from '../../api/schedule'
import type { OrderHeader } from '../../types/order'

const tableData = ref<OrderHeader[]>([])
const loading = ref(false)

const loadOrders = async () => {
  loading.value = true
  try {
    tableData.value = await listOrders()
  } catch {
    ElMessage.error('订单加载失败')
  } finally {
    loading.value = false
  }
}

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
  createOrder({
    productCode: 'P-10001',
    orderQty: 10,
    dueTime: new Date(Date.now() + 3 * 24 * 60 * 60 * 1000).toISOString(),
    status: 'NEW',
    sourceType: 'MANUAL'
  })
    .then(async () => {
      ElMessage.success('订单已创建')
      await loadOrders()
    })
    .catch(() => {
      ElMessage.error('创建订单失败')
    })
}

const handleExplodeBOM = async (row: OrderHeader) => {
  try {
    const result = await explodeBom(row.productCode, Number(row.orderQty))
    ElMessage.success(`BOM 展开完成，需求项 ${result.length} 条`)
  } catch {
    ElMessage.error('BOM 展开失败')
  }
}

const handleSchedule = async (row: OrderHeader) => {
  try {
    const wo = await createWorkOrder({
      orderNo: row.orderNo,
      materialCode: row.productCode,
      woQty: Number(row.orderQty),
      status: 'NEW'
    })
    await createScheduleTask({
      scheduleNo: `SCH${Date.now()}`,
      woNo: wo.woNo,
      opNo: 10,
      workCenterCode: 'WC-01',
      planStartTime: new Date().toISOString(),
      planEndTime: new Date(Date.now() + 2 * 60 * 60 * 1000).toISOString(),
      scheduleStatus: 'NORMAL'
    })
    ElMessage.success('已生成工单并完成排程')
    await loadOrders()
  } catch {
    ElMessage.error('排程失败')
  }
}

const handleEdit = async (row: OrderHeader) => {
  if (row.id === undefined) {
    ElMessage.error('订单缺少主键，无法编辑')
    return
  }
  try {
    const { value } = await ElMessageBox.prompt('请输入新的订单数量', '编辑订单', {
      inputValue: String(row.orderQty),
      confirmButtonText: '保存',
      cancelButtonText: '取消',
      inputPattern: /^(\d+)(\.\d+)?$/,
      inputErrorMessage: '请输入有效数字'
    })
    await updateOrder(row.id, { ...row, orderQty: Number(value) })
    ElMessage.success('订单已更新')
    await loadOrders()
  } catch {
  }
}

const handleDelete = async (row: OrderHeader) => {
  if (row.id === undefined) {
    ElMessage.error('订单缺少主键，无法删除')
    return
  }
  try {
    await ElMessageBox.confirm(`确认删除订单 ${row.orderNo} 吗？`, '删除确认', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteOrder(row.id)
    ElMessage.success('订单已删除')
    await loadOrders()
  } catch {
  }
}

onMounted(async () => {
  await loadOrders()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
