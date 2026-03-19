import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/order/list'
  },
  {
    path: '/order/list',
    name: 'OrderList',
    component: () => import('../views/order/OrderList.vue')
  },
  {
    path: '/schedule/gantt',
    name: 'ScheduleGantt',
    component: () => import('../views/schedule/GanttChart.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
