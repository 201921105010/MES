package com.mes.scheduler.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mes.scheduler.common.entity.BaseEntity;

import java.math.BigDecimal;

@TableName("res_work_center")
public class WorkCenter extends BaseEntity {

    private String workCenterCode;
    private String workCenterName;
    private BigDecimal capacityPerDay;
    private String status;

    public String getWorkCenterCode() {
        return workCenterCode;
    }

    public void setWorkCenterCode(String workCenterCode) {
        this.workCenterCode = workCenterCode;
    }

    public String getWorkCenterName() {
        return workCenterName;
    }

    public void setWorkCenterName(String workCenterName) {
        this.workCenterName = workCenterName;
    }

    public BigDecimal getCapacityPerDay() {
        return capacityPerDay;
    }

    public void setCapacityPerDay(BigDecimal capacityPerDay) {
        this.capacityPerDay = capacityPerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
