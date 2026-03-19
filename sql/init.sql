-- MES 自动工单与智能排程系统 - MySQL 数据库初始化脚本

-- 5.1 物料主表 md_material
CREATE TABLE md_material (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    material_code VARCHAR(50) NOT NULL UNIQUE,
    material_name VARCHAR(200) NOT NULL,
    material_type VARCHAR(20) NOT NULL,
    unit VARCHAR(20),
    make_buy_type VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料主表';

-- 5.2 BOM 主表 bom_header
CREATE TABLE bom_header (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bom_code VARCHAR(50) NOT NULL UNIQUE,
    product_code VARCHAR(50) NOT NULL,
    version_no VARCHAR(20) NOT NULL,
    effective_from DATETIME,
    effective_to DATETIME,
    status VARCHAR(20) DEFAULT 'DRAFT',
    remark VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BOM主表';

-- 5.3 BOM 明细表 bom_item
CREATE TABLE bom_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bom_id BIGINT NOT NULL,
    parent_material_code VARCHAR(50) NOT NULL,
    child_material_code VARCHAR(50) NOT NULL,
    qty DECIMAL(18,6) NOT NULL,
    scrap_rate DECIMAL(10,4) DEFAULT 0,
    level_no INT,
    seq_no INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BOM明细表';

-- 5.4 工艺路线主表 route_header
CREATE TABLE route_header (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_code VARCHAR(50) NOT NULL UNIQUE,
    material_code VARCHAR(50) NOT NULL,
    version_no VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',
    effective_from DATETIME,
    effective_to DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工艺路线主表';

-- 5.5 工艺路线明细表 route_operation
CREATE TABLE route_operation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_id BIGINT NOT NULL,
    op_no INT NOT NULL,
    op_name VARCHAR(100) NOT NULL,
    work_center_code VARCHAR(50) NOT NULL,
    std_hours DECIMAL(18,4) NOT NULL,
    setup_hours DECIMAL(18,4) DEFAULT 0,
    equipment_type VARCHAR(50),
    labor_qty INT DEFAULT 1,
    predecessor_op_no INT,
    is_outsource BOOLEAN DEFAULT FALSE,
    is_qc BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工艺路线明细表';

-- 5.6 工作中心表 res_work_center
CREATE TABLE res_work_center (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_center_code VARCHAR(50) NOT NULL UNIQUE,
    work_center_name VARCHAR(100) NOT NULL,
    capacity_per_day DECIMAL(18,4),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作中心表';

-- 5.7 设备表 res_equipment
CREATE TABLE res_equipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_code VARCHAR(50) NOT NULL UNIQUE,
    equipment_name VARCHAR(100) NOT NULL,
    work_center_code VARCHAR(50) NOT NULL,
    equipment_type VARCHAR(50),
    status VARCHAR(20) DEFAULT 'IDLE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备表';

-- 5.8 班次表 res_shift_calendar
CREATE TABLE res_shift_calendar (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shift_code VARCHAR(50) NOT NULL,
    shift_name VARCHAR(50) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    work_center_code VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班次表';

-- 5.9 订单表 prd_order
CREATE TABLE prd_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    product_code VARCHAR(50) NOT NULL,
    order_qty DECIMAL(18,6) NOT NULL,
    due_time DATETIME NOT NULL,
    priority INT DEFAULT 5,
    is_urgent BOOLEAN DEFAULT FALSE,
    status VARCHAR(20) DEFAULT 'NEW',
    source_type VARCHAR(20) DEFAULT 'MANUAL',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 5.10 工单主表 wo_header
CREATE TABLE wo_header (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wo_no VARCHAR(50) NOT NULL UNIQUE,
    order_no VARCHAR(50) NOT NULL,
    parent_wo_no VARCHAR(50),
    material_code VARCHAR(50) NOT NULL,
    wo_qty DECIMAL(18,6) NOT NULL,
    bom_version VARCHAR(20),
    route_version VARCHAR(20),
    plan_start_time DATETIME,
    plan_end_time DATETIME,
    priority INT DEFAULT 5,
    status VARCHAR(20) DEFAULT 'NEW',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单主表';

-- 5.11 工单工序表 wo_operation
CREATE TABLE wo_operation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wo_no VARCHAR(50) NOT NULL,
    op_no INT NOT NULL,
    op_name VARCHAR(100) NOT NULL,
    work_center_code VARCHAR(50) NOT NULL,
    equipment_code VARCHAR(50),
    labor_qty INT DEFAULT 1,
    std_hours DECIMAL(18,4) NOT NULL,
    predecessor_op_no INT,
    material_ready_time DATETIME,
    plan_start_time DATETIME,
    plan_end_time DATETIME,
    actual_start_time DATETIME,
    actual_end_time DATETIME,
    status VARCHAR(20) DEFAULT 'PENDING',
    is_locked BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单工序表';

-- 5.12 工单物料需求表 wo_material
CREATE TABLE wo_material (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wo_no VARCHAR(50) NOT NULL,
    material_code VARCHAR(50) NOT NULL,
    required_qty DECIMAL(18,6) NOT NULL,
    issued_qty DECIMAL(18,6) DEFAULT 0,
    shortage_qty DECIMAL(18,6) DEFAULT 0,
    ready_time DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单物料需求表';

-- 5.13 排程结果表 sch_task
CREATE TABLE sch_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    schedule_no VARCHAR(50) NOT NULL,
    wo_no VARCHAR(50) NOT NULL,
    op_no INT NOT NULL,
    work_center_code VARCHAR(50) NOT NULL,
    equipment_code VARCHAR(50),
    shift_code VARCHAR(50),
    plan_start_time DATETIME NOT NULL,
    plan_end_time DATETIME NOT NULL,
    priority_score DECIMAL(18,4),
    schedule_status VARCHAR(20) DEFAULT 'NORMAL',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='排程结果表';

-- 5.14 日志表 sys_audit_log
CREATE TABLE sys_audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_name VARCHAR(50) NOT NULL,
    biz_type VARCHAR(50),
    biz_id VARCHAR(50),
    operation VARCHAR(50) NOT NULL,
    before_data TEXT,
    after_data TEXT,
    operator VARCHAR(50),
    operated_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日志表';
