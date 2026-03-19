package com.mes.scheduler.bom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mes.scheduler.bom.entity.BomHeader;
import com.mes.scheduler.bom.entity.BomItem;
import com.mes.scheduler.bom.mapper.BomHeaderMapper;
import com.mes.scheduler.bom.mapper.BomItemMapper;
import com.mes.scheduler.bom.service.BomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BomServiceImpl extends ServiceImpl<BomHeaderMapper, BomHeader> implements BomService {

    @Autowired
    private BomItemMapper bomItemMapper;

    @Override
    public BomHeader getEffectiveBom(String productCode) {
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<BomHeader> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_code", productCode)
                .eq("status", "ACTIVE")
                .le("effective_from", now)
                .ge("effective_to", now)
                .orderByDesc("version_no")
                .last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> explodeBom(String productCode, BigDecimal qty) {
        BomHeader effectiveBom = getEffectiveBom(productCode);
        if (effectiveBom == null) {
            throw new RuntimeException("无有效的BOM版本: " + productCode);
        }

        List<Map<String, Object>> demands = new ArrayList<>();
        explodeRecursively(effectiveBom.getId(), qty, 1, demands);
        return demands;
    }

    private void explodeRecursively(Long bomId, BigDecimal parentQty, int level, List<Map<String, Object>> demands) {
        QueryWrapper<BomItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bom_id", bomId);
        List<BomItem> items = bomItemMapper.selectList(queryWrapper);

        for (BomItem item : items) {
            // 子件需求量 = 父件需求量 × 用量 × (1 + 损耗率)
            BigDecimal scrapMultiplier = BigDecimal.ONE.add(item.getScrapRate());
            BigDecimal requiredQty = parentQty.multiply(item.getQty()).multiply(scrapMultiplier);

            Map<String, Object> demand = new HashMap<>();
            demand.put("materialCode", item.getChildMaterialCode());
            demand.put("requiredQty", requiredQty);
            demand.put("level", level);
            demands.add(demand);

            // 递归查找子件是否有自己的BOM
            BomHeader childBom = getEffectiveBom(item.getChildMaterialCode());
            if (childBom != null) {
                explodeRecursively(childBom.getId(), requiredQty, level + 1, demands);
            }
        }
    }
}
