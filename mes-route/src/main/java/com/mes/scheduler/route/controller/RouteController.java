package com.mes.scheduler.route.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mes.scheduler.route.entity.RouteHeader;
import com.mes.scheduler.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<RouteHeader> list(@RequestParam(required = false) String materialCode,
                                  @RequestParam(required = false) String status) {
        QueryWrapper<RouteHeader> queryWrapper = new QueryWrapper<>();
        if (materialCode != null && !materialCode.isBlank()) {
            queryWrapper.eq("material_code", materialCode);
        }
        if (status != null && !status.isBlank()) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("id");
        return routeService.list(queryWrapper);
    }

    @GetMapping("/{id}")
    public RouteHeader getById(@PathVariable Long id) {
        return routeService.getById(id);
    }

    @PostMapping
    public RouteHeader create(@RequestBody RouteHeader routeHeader) {
        routeService.save(routeHeader);
        return routeHeader;
    }

    @PutMapping("/{id}")
    public RouteHeader update(@PathVariable Long id, @RequestBody RouteHeader routeHeader) {
        routeHeader.setId(id);
        routeService.updateById(routeHeader);
        return routeService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return routeService.removeById(id);
    }
}
