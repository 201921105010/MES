package com.mes.scheduler.workorder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mes.scheduler.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("wo_header")
public class WorkOrder extends BaseEntity {

    private String woNo;
    private String orderNo;
    private String parentWoNo;
    private String materialCode;
    private BigDecimal woQty;
    private String bomVersion;
    private String routeVersion;
    private LocalDateTime planStartTime;
    private LocalDateTime planEndTime;
    private Integer priority;
    private String status;

    public String getWoNo() {
        return woNo;
    }

    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getParentWoNo() {
        return parentWoNo;
    }

    public void setParentWoNo(String parentWoNo) {
        this.parentWoNo = parentWoNo;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public BigDecimal getWoQty() {
        return woQty;
    }

    public void setWoQty(BigDecimal woQty) {
        this.woQty = woQty;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getRouteVersion() {
        return routeVersion;
    }

    public void setRouteVersion(String routeVersion) {
        this.routeVersion = routeVersion;
    }

    public LocalDateTime getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(LocalDateTime planStartTime) {
        this.planStartTime = planStartTime;
    }

    public LocalDateTime getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(LocalDateTime planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
