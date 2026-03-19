package com.mes.scheduler.bom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mes.scheduler.bom.entity.BomHeader;
import java.util.List;
import java.util.Map;

public interface BomService extends IService<BomHeader> {
    
    /**
     * 获取有效的BOM
     * @param productCode 产品编码
     * @return BOM 主表信息
     */
    BomHeader getEffectiveBom(String productCode);

    /**
     * 展开BOM
     * @param productCode 产品编码
     * @param qty 数量
     * @return 展开后的物料需求列表
     */
    List<Map<String, Object>> explodeBom(String productCode, java.math.BigDecimal qty);
}
