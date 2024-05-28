package com.pandaer.project.server.modules.report.dto;

import lombok.Data;

/**
 * 营销报表 每一项的数据
 */

@Data
public class MarketItemDTO {
    /**
     * 商品的类型名
     */
    private String type;

    /**
     * 在订单中,这样类型的商品有多少
     */
    private Integer value;
}
