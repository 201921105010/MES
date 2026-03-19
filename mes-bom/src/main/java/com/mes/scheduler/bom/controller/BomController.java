package com.mes.scheduler.bom.controller;

import com.mes.scheduler.bom.service.BomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bom")
public class BomController {

    @Autowired
    private BomService bomService;

    @PostMapping("/explode")
    public List<Map<String, Object>> explodeBom(@RequestBody Map<String, Object> request) {
        String productCode = (String) request.get("productCode");
        BigDecimal qty = new BigDecimal(request.get("qty").toString());
        return bomService.explodeBom(productCode, qty);
    }
}
