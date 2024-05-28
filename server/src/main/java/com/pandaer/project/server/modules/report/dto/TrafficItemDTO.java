package com.pandaer.project.server.modules.report.dto;

import lombok.Data;

/**
 * 流量报表 每一项的数据
 */

/**
 * todo 后期优化
 */
@Data
public class TrafficItemDTO {
    /**
     * 日期
     */
    private String date;

    /**
     * 这一天订单的总数
     */
    private Integer sum;
}
