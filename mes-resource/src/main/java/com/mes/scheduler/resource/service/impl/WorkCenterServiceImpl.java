package com.mes.scheduler.resource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mes.scheduler.resource.entity.WorkCenter;
import com.mes.scheduler.resource.mapper.WorkCenterMapper;
import com.mes.scheduler.resource.service.WorkCenterService;
import org.springframework.stereotype.Service;

@Service
public class WorkCenterServiceImpl extends ServiceImpl<WorkCenterMapper, WorkCenter> implements WorkCenterService {
}
