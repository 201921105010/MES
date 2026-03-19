# 基于 BOM 与工艺路线的 MES 自动工单与智能排程系统

本项目基于设计方案开发，提供完整的智能排程系统骨架，包含后端微服务架构以及前端控制台。

## 项目结构

```
mes-scheduler/
├── mes-application     # Spring Boot 主启动工程 (端口 8080)
├── mes-common          # 基础 Entity、工具类、全局异常处理
├── mes-api             # 接口定义层
├── mes-order           # 订单管理模块
├── mes-bom             # BOM 解析与需求计算模块 (已包含递归展开实现)
├── mes-route           # 工艺路线模块
├── mes-workorder       # 工单生成模块
├── mes-schedule        # 排程引擎模块
├── mes-resource        # 工作中心/设备资源模块
├── mes-integration     # ERP/WMS 外部集成模块
├── mes-audit           # 日志与审计模块
├── sql/                # 数据库初始化脚本 (MySQL)
└── mes-ui/             # Vue 3 + TypeScript + Element Plus 前端工程
```

## 技术栈

- **前端**：Vue 3 + TypeScript + Vite + Element Plus + ECharts (甘特图)
- **后端**：Java 21 + Spring Boot 3 + MyBatis-Plus
- **数据库**：MySQL 8.0+
- **缓存**：Redis
- **其他中间件**：RabbitMQ (待接入)、XXL-JOB (待接入)

## 快速开始

### 1. 数据库准备

1. 安装并启动 MySQL。
2. 创建数据库 `mes_db`。
3. 执行 `mes-scheduler/sql/init.sql` 脚本，初始化所有数据表结构。

### 2. 后端启动

1. 确保已安装 Java 21 和 Maven。
2. 进入 `mes-scheduler` 根目录：
   ```bash
   mvn clean install
   ```
3. 运行主启动类 `com.mes.scheduler.MesSchedulerApplication`。
   或者使用 Maven 运行：
   ```bash
   cd mes-application
   mvn spring-boot:run
   ```

### 3. 前端启动

1. 确保已安装 Node.js (建议 18+)。
2. 进入前端目录：
   ```bash
   cd mes-scheduler/mes-ui
   npm install
   npm run dev
   ```
3. 打开浏览器访问 `http://localhost:5173/` 即可看到订单管理与排程甘特图界面。

## 已实现的核心功能预览

1. **BOM 递归展开算法**：
   在 `BomServiceImpl.java` 中，实现了按损耗率和用量自动递归计算子物料需求的算法逻辑。
2. **MySQL 全套建表脚本**：
   包含了从物料、BOM、工艺路线、工单到排程结果的 14 张核心业务表的 DDL 语句。
3. **前端排程甘特图**：
   集成了 ECharts 自定义渲染 (Custom Series) 实现的工作中心排程甘特图视图。
