package com.mes.scheduler.workorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mes.scheduler.workorder.entity.WorkOrder;
import com.mes.scheduler.workorder.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/workorders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @GetMapping
    public List<WorkOrder> list(@RequestParam(required = false) String orderNo,
                                @RequestParam(required = false) String status) {
        QueryWrapper<WorkOrder> queryWrapper = new QueryWrapper<>();
        if (orderNo != null && !orderNo.isBlank()) {
            queryWrapper.eq("order_no", orderNo);
        }
        if (status != null && !status.isBlank()) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("id");
        return workOrderService.list(queryWrapper);
    }

    @GetMapping("/{id}")
    public WorkOrder getById(@PathVariable Long id) {
        return workOrderService.getById(id);
    }

    @PostMapping
    public WorkOrder create(@RequestBody WorkOrder workOrder) {
        if (workOrder.getWoNo() == null || workOrder.getWoNo().isBlank()) {
            workOrder.setWoNo("WO" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        }
        if (workOrder.getStatus() == null || workOrder.getStatus().isBlank()) {
            workOrder.setStatus("NEW");
        }
        if (workOrder.getPriority() == null) {
            workOrder.setPriority(5);
        }
        workOrderService.save(workOrder);
        return workOrder;
    }

    @PutMapping("/{id}")
    public WorkOrder update(@PathVariable Long id, @RequestBody WorkOrder workOrder) {
        workOrder.setId(id);
        workOrderService.updateById(workOrder);
        return workOrderService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return workOrderService.removeById(id);
    }
}
