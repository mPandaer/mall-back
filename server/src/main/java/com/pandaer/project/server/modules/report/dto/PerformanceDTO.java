package com.pandaer.project.server.modules.report.dto;

import lombok.Data;

/**
 * 业绩报表数据项
 */
@Data
public class PerformanceDTO {
    /**
     * 商品名
     */
    private String type;

    /**
     * 在订单中,这样商品的数量
     */
    private Integer value;
}
