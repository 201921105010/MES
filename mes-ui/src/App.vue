<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <el-menu
        router
        default-active="/order/list"
        class="el-menu-vertical"
        background-color="#304156"
        text-color="#fff"
        active-text-color="#409EFF"
      >
        <div class="logo">MES 排程系统</div>
        <el-menu-item index="/order/list">
          <el-icon><document /></el-icon>
          <span>订单管理</span>
        </el-menu-item>
        <el-menu-item index="/schedule/gantt">
          <el-icon><calendar /></el-icon>
          <span>排程甘特图</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header>
        <div class="header-content">智能排程控制台</div>
      </el-header>
      
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { Document, Calendar } from '@element-plus/icons-vue'
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.el-menu-vertical {
  height: 100%;
  border-right: none;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  background-color: #2b3643;
}
.el-header {
  background-color: #fff;
  color: #333;
  line-height: 60px;
  border-bottom: 1px solid #dcdfe6;
  font-size: 18px;
}
.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
