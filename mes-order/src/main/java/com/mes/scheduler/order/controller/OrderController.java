package com.mes.scheduler.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mes.scheduler.order.entity.OrderHeader;
import com.mes.scheduler.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderHeader> list(@RequestParam(required = false) String status) {
        QueryWrapper<OrderHeader> queryWrapper = new QueryWrapper<>();
        if (status != null && !status.isBlank()) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("id");
        return orderService.list(queryWrapper);
    }

    @GetMapping("/page")
    public Page<OrderHeader> page(@RequestParam(defaultValue = "1") long current,
                                  @RequestParam(defaultValue = "10") long size,
                                  @RequestParam(required = false) String status) {
        QueryWrapper<OrderHeader> queryWrapper = new QueryWrapper<>();
        if (status != null && !status.isBlank()) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("id");
        return orderService.page(new Page<>(current, size), queryWrapper);
    }

    @GetMapping("/{id}")
    public OrderHeader getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    public OrderHeader create(@RequestBody OrderHeader order) {
        if (order.getOrderNo() == null || order.getOrderNo().isBlank()) {
            order.setOrderNo("MO" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        }
        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("NEW");
        }
        if (order.getSourceType() == null || order.getSourceType().isBlank()) {
            order.setSourceType("MANUAL");
        }
        if (order.getPriority() == null) {
            order.setPriority(5);
        }
        if (order.getIsUrgent() == null) {
            order.setIsUrgent(Boolean.FALSE);
        }
        orderService.save(order);
        return order;
    }

    @PutMapping("/{id}")
    public OrderHeader update(@PathVariable Long id, @RequestBody OrderHeader order) {
        order.setId(id);
        orderService.updateById(order);
        return orderService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return orderService.removeById(id);
    }
}
