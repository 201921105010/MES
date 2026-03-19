package com.mes.scheduler.bom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mes.scheduler.common.entity.BaseEntity;
import java.math.BigDecimal;

@TableName("bom_item")
public class BomItem extends BaseEntity {

    private Long bomId;
    private String parentMaterialCode;
    private String childMaterialCode;
    private BigDecimal qty;
    private BigDecimal scrapRate;
    private Integer levelNo;
    private Integer seqNo;

    // Getters and Setters
    public Long getBomId() { return bomId; }
    public void setBomId(Long bomId) { this.bomId = bomId; }

    public String getParentMaterialCode() { return parentMaterialCode; }
    public void setParentMaterialCode(String parentMaterialCode) { this.parentMaterialCode = parentMaterialCode; }

    public String getChildMaterialCode() { return childMaterialCode; }
    public void setChildMaterialCode(String childMaterialCode) { this.childMaterialCode = childMaterialCode; }

    public BigDecimal getQty() { return qty; }
    public void setQty(BigDecimal qty) { this.qty = qty; }

    public BigDecimal getScrapRate() { return scrapRate; }
    public void setScrapRate(BigDecimal scrapRate) { this.scrapRate = scrapRate; }

    public Integer getLevelNo() { return levelNo; }
    public void setLevelNo(Integer levelNo) { this.levelNo = levelNo; }

    public Integer getSeqNo() { return seqNo; }
    public void setSeqNo(Integer seqNo) { this.seqNo = seqNo; }
}
