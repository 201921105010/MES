package com.mes.scheduler.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mes.scheduler.schedule.entity.ScheduleTask;
import com.mes.scheduler.schedule.service.ScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules/tasks")
public class ScheduleTaskController {

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @GetMapping
    public List<ScheduleTask> list(@RequestParam(required = false) String woNo,
                                   @RequestParam(required = false) String workCenterCode) {
        QueryWrapper<ScheduleTask> queryWrapper = new QueryWrapper<>();
        if (woNo != null && !woNo.isBlank()) {
            queryWrapper.eq("wo_no", woNo);
        }
        if (workCenterCode != null && !workCenterCode.isBlank()) {
            queryWrapper.eq("work_center_code", workCenterCode);
        }
        queryWrapper.orderByAsc("plan_start_time");
        return scheduleTaskService.list(queryWrapper);
    }

    @GetMapping("/{id}")
    public ScheduleTask getById(@PathVariable Long id) {
        return scheduleTaskService.getById(id);
    }

    @PostMapping
    public ScheduleTask create(@RequestBody ScheduleTask scheduleTask) {
        if (scheduleTask.getScheduleStatus() == null || scheduleTask.getScheduleStatus().isBlank()) {
            scheduleTask.setScheduleStatus("NORMAL");
        }
        scheduleTaskService.save(scheduleTask);
        return scheduleTask;
    }

    @PutMapping("/{id}")
    public ScheduleTask update(@PathVariable Long id, @RequestBody ScheduleTask scheduleTask) {
        scheduleTask.setId(id);
        scheduleTaskService.updateById(scheduleTask);
        return scheduleTaskService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return scheduleTaskService.removeById(id);
    }
}
