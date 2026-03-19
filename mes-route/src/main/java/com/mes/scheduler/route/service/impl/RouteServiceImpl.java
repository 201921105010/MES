package com.mes.scheduler.route.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mes.scheduler.route.entity.RouteHeader;
import com.mes.scheduler.route.mapper.RouteHeaderMapper;
import com.mes.scheduler.route.service.RouteService;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl extends ServiceImpl<RouteHeaderMapper, RouteHeader> implements RouteService {
}
