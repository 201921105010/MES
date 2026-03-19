package com.mes.scheduler.resource.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mes.scheduler.resource.entity.Equipment;
import com.mes.scheduler.resource.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<Equipment> list(@RequestParam(required = false) String workCenterCode,
                                @RequestParam(required = false) String status) {
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        if (workCenterCode != null && !workCenterCode.isBlank()) {
            queryWrapper.eq("work_center_code", workCenterCode);
        }
        if (status != null && !status.isBlank()) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("id");
        return equipmentService.list(queryWrapper);
    }

    @GetMapping("/{id}")
    public Equipment getById(@PathVariable Long id) {
        return equipmentService.getById(id);
    }

    @PostMapping
    public Equipment create(@RequestBody Equipment equipment) {
        if (equipment.getStatus() == null || equipment.getStatus().isBlank()) {
            equipment.setStatus("IDLE");
        }
        equipmentService.save(equipment);
        return equipment;
    }

    @PutMapping("/{id}")
    public Equipment update(@PathVariable Long id, @RequestBody Equipment equipment) {
        equipment.setId(id);
        equipmentService.updateById(equipment);
        return equipmentService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return equipmentService.removeById(id);
    }
}
