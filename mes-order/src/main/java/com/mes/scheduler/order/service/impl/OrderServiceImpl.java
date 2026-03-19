package com.mes.scheduler.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mes.scheduler.order.entity.OrderHeader;
import com.mes.scheduler.order.mapper.OrderHeaderMapper;
import com.mes.scheduler.order.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderHeaderMapper, OrderHeader> implements OrderService {
}
