package com.pandaer.project.server.modules.order.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum  OrderEnum {

    WAITING_FOR_SHIPMENT(0, "待发货"),
    SHIPPING(1, "运输中"),
    TRANSACTION_COMPLETED(2, "交易完成"),
    RETURN_REQUEST(3, "申请退货"),
    RETURNED(4, "已退货"),
    CANCELLED(5, "已取消");

    private final Integer code;
    private final String desc;

    OrderEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public OrderEnum next() {
        switch (this) {
            case WAITING_FOR_SHIPMENT -> {
                return SHIPPING;
            }
            case SHIPPING -> {
                return TRANSACTION_COMPLETED;
            }
            case RETURN_REQUEST -> {
                return RETURNED;
            }
        }
        return null;
    }

    public static OrderEnum getByCode(Integer code) {
        return Arrays.stream(values()).
                filter(item -> ObjUtil.equal(item.getCode(),code)).findFirst().orElse(null);
    }

}
