package com.mes.scheduler.resource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mes.scheduler.resource.entity.Equipment;
import com.mes.scheduler.resource.mapper.EquipmentMapper;
import com.mes.scheduler.resource.service.EquipmentService;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {
}
