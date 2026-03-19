-- MES 自动工单与智能排程系统 - PostgreSQL 数据库初始化脚本

-- 5.1 物料主表 md_material
CREATE TABLE md_material (
    id BIGSERIAL PRIMARY KEY,
    material_code VARCHAR(50) NOT NULL UNIQUE,
    material_name VARCHAR(200) NOT NULL,
    material_type VARCHAR(20) NOT NULL, -- 成品/半成品/原料/外协件
    unit VARCHAR(20),
    make_buy_type VARCHAR(20), -- 自制/外购/外协
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE md_material IS '物料主表';

-- 5.2 BOM 主表 bom_header
CREATE TABLE bom_header (
    id BIGSERIAL PRIMARY KEY,
    bom_code VARCHAR(50) NOT NULL UNIQUE,
    product_code VARCHAR(50) NOT NULL,
    version_no VARCHAR(20) NOT NULL,
    effective_from TIMESTAMP,
    effective_to TIMESTAMP,
    status VARCHAR(20) DEFAULT 'DRAFT', -- 草稿/生效/停用
    remark VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE bom_header IS 'BOM主表';

-- 5.3 BOM 明细表 bom_item
CREATE TABLE bom_item (
    id BIGSERIAL PRIMARY KEY,
    bom_id BIGINT NOT NULL,
    parent_material_code VARCHAR(50) NOT NULL,
    child_material_code VARCHAR(50) NOT NULL,
    qty NUMERIC(18,6) NOT NULL,
    scrap_rate NUMERIC(10,4) DEFAULT 0,
    level_no INT,
    seq_no INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE bom_item IS 'BOM明细表';

-- 5.4 工艺路线主表 route_header
CREATE TABLE route_header (
    id BIGSERIAL PRIMARY KEY,
    route_code VARCHAR(50) NOT NULL UNIQUE,
    material_code VARCHAR(50) NOT NULL,
    version_no VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',
    effective_from TIMESTAMP,
    effective_to TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE route_header IS '工艺路线主表';

-- 5.5 工艺路线明细表 route_operation
CREATE TABLE route_operation (
    id BIGSERIAL PRIMARY KEY,
    route_id BIGINT NOT NULL,
    op_no INT NOT NULL,
    op_name VARCHAR(100) NOT NULL,
    work_center_code VARCHAR(50) NOT NULL,
    std_hours NUMERIC(18,4) NOT NULL,
    setup_hours NUMERIC(18,4) DEFAULT 0,
    equipment_type VARCHAR(50),
    labor_qty INT DEFAULT 1,
    predecessor_op_no INT,
    is_outsource BOOLEAN DEFAULT FALSE,
    is_qc BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE route_operation IS '工艺路线明细表';

-- 5.6 工作中心表 res_work_center
CREATE TABLE res_work_center (
    id BIGSERIAL PRIMARY KEY,
    work_center_code VARCHAR(50) NOT NULL UNIQUE,
    work_center_name VARCHAR(100) NOT NULL,
    capacity_per_day NUMERIC(18,4),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE res_work_center IS '工作中心表';

-- 5.7 设备表 res_equipment
CREATE TABLE res_equipment (
    id BIGSERIAL PRIMARY KEY,
    equipment_code VARCHAR(50) NOT NULL UNIQUE,
    equipment_name VARCHAR(100) NOT NULL,
    work_center_code VARCHAR(50) NOT NULL,
    equipment_type VARCHAR(50),
    status VARCHAR(20) DEFAULT 'IDLE', -- 空闲/运行/保养/停机
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE res_equipment IS '设备表';

-- 5.8 班次表 res_shift_calendar
CREATE TABLE res_shift_calendar (
    id BIGSERIAL PRIMARY KEY,
    shift_code VARCHAR(50) NOT NULL,
    shift_name VARCHAR(50) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    work_center_code VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE res_shift_calendar IS '班次表';

-- 5.9 订单表 prd_order
CREATE TABLE prd_order (
    id BIGSERIAL PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    product_code VARCHAR(50) NOT NULL,
    order_qty NUMERIC(18,6) NOT NULL,
    due_time TIMESTAMP NOT NULL,
    priority INT DEFAULT 5,
    is_urgent BOOLEAN DEFAULT FALSE,
    status VARCHAR(20) DEFAULT 'NEW', -- NEW/PLANNED/IN_PROGRESS/COMPLETED/CLOSED
    source_type VARCHAR(20) DEFAULT 'MANUAL', -- ERP/MANUAL/API
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE prd_order IS '订单表';

-- 5.10 工单主表 wo_header
CREATE TABLE wo_header (
    id BIGSERIAL PRIMARY KEY,
    wo_no VARCHAR(50) NOT NULL UNIQUE,
    order_no VARCHAR(50) NOT NULL,
    parent_wo_no VARCHAR(50),
    material_code VARCHAR(50) NOT NULL,
    wo_qty NUMERIC(18,6) NOT NULL,
    bom_version VARCHAR(20),
    route_version VARCHAR(20),
    plan_start_time TIMESTAMP,
    plan_end_time TIMESTAMP,
    priority INT DEFAULT 5,
    status VARCHAR(20) DEFAULT 'NEW',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE wo_header IS '工单主表';

-- 5.11 工单工序表 wo_operation
CREATE TABLE wo_operation (
    id BIGSERIAL PRIMARY KEY,
    wo_no VARCHAR(50) NOT NULL,
    op_no INT NOT NULL,
    op_name VARCHAR(100) NOT NULL,
    work_center_code VARCHAR(50) NOT NULL,
    equipment_code VARCHAR(50),
    labor_qty INT DEFAULT 1,
    std_hours NUMERIC(18,4) NOT NULL,
    predecessor_op_no INT,
    material_ready_time TIMESTAMP,
    plan_start_time TIMESTAMP,
    plan_end_time TIMESTAMP,
    actual_start_time TIMESTAMP,
    actual_end_time TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING',
    is_locked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE wo_operation IS '工单工序表';

-- 5.12 工单物料需求表 wo_material
CREATE TABLE wo_material (
    id BIGSERIAL PRIMARY KEY,
    wo_no VARCHAR(50) NOT NULL,
    material_code VARCHAR(50) NOT NULL,
    required_qty NUMERIC(18,6) NOT NULL,
    issued_qty NUMERIC(18,6) DEFAULT 0,
    shortage_qty NUMERIC(18,6) DEFAULT 0,
    ready_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE wo_material IS '工单物料需求表';

-- 5.13 排程结果表 sch_task
CREATE TABLE sch_task (
    id BIGSERIAL PRIMARY KEY,
    schedule_no VARCHAR(50) NOT NULL,
    wo_no VARCHAR(50) NOT NULL,
    op_no INT NOT NULL,
    work_center_code VARCHAR(50) NOT NULL,
    equipment_code VARCHAR(50),
    shift_code VARCHAR(50),
    plan_start_time TIMESTAMP NOT NULL,
    plan_end_time TIMESTAMP NOT NULL,
    priority_score NUMERIC(18,4),
    schedule_status VARCHAR(20) DEFAULT 'NORMAL', -- 正常/插单/重排
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE sch_task IS '排程结果表';

-- 5.14 日志表 sys_audit_log
CREATE TABLE sys_audit_log (
    id BIGSERIAL PRIMARY KEY,
    module_name VARCHAR(50) NOT NULL,
    biz_type VARCHAR(50),
    biz_id VARCHAR(50),
    operation VARCHAR(50) NOT NULL,
    before_data TEXT,
    after_data TEXT,
    operator VARCHAR(50),
    operated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE sys_audit_log IS '日志表';
