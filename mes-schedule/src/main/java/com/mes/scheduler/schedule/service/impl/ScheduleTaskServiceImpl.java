package com.mes.scheduler.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mes.scheduler.schedule.entity.ScheduleTask;
import com.mes.scheduler.schedule.mapper.ScheduleTaskMapper;
import com.mes.scheduler.schedule.service.ScheduleTaskService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskServiceImpl extends ServiceImpl<ScheduleTaskMapper, ScheduleTask> implements ScheduleTaskService {
}
