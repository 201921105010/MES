package com.mes.scheduler.resource.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mes.scheduler.resource.entity.WorkCenter;
import com.mes.scheduler.resource.service.WorkCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/work-centers")
public class WorkCenterController {

    @Autowired
    private WorkCenterService workCenterService;

    @GetMapping
    public List<WorkCenter> list(@RequestParam(required = false) String status) {
        QueryWrapper<WorkCenter> queryWrapper = new QueryWrapper<>();
        if (status != null && !status.isBlank()) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("id");
        return workCenterService.list(queryWrapper);
    }

    @GetMapping("/{id}")
    public WorkCenter getById(@PathVariable Long id) {
        return workCenterService.getById(id);
    }

    @PostMapping
    public WorkCenter create(@RequestBody WorkCenter workCenter) {
        if (workCenter.getStatus() == null || workCenter.getStatus().isBlank()) {
            workCenter.setStatus("ACTIVE");
        }
        workCenterService.save(workCenter);
        return workCenter;
    }

    @PutMapping("/{id}")
    public WorkCenter update(@PathVariable Long id, @RequestBody WorkCenter workCenter) {
        workCenter.setId(id);
        workCenterService.updateById(workCenter);
        return workCenterService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return workCenterService.removeById(id);
    }
}
