package com.pandaer.project.server.modules.order.po;

import lombok.Data;

import java.util.Date;

@Data
public class OrderQueryPO {
    private Long orderId;
    private Long userId;
    private String address;
    private Integer orderStatus;
    private Date startTime;
    private Date endTime;
}