package com.mes.scheduler.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mes.scheduler.common.entity.BaseEntity;

@TableName("res_equipment")
public class Equipment extends BaseEntity {

    private String equipmentCode;
    private String equipmentName;
    private String workCenterCode;
    private String equipmentType;
    private String status;

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getWorkCenterCode() {
        return workCenterCode;
    }

    public void setWorkCenterCode(String workCenterCode) {
        this.workCenterCode = workCenterCode;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
