package com.mes.scheduler.workorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mes.scheduler.workorder.entity.WorkOrder;
import com.mes.scheduler.workorder.mapper.WorkOrderMapper;
import com.mes.scheduler.workorder.service.WorkOrderService;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {
}
