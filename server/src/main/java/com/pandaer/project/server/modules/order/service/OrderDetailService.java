package com.pandaer.project.server.modules.order.service;

import com.pandaer.project.server.modules.order.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.order.vo.OrderDetailVO;

import java.util.List;

/**
* @author pandaer
* @description 针对表【order_details】的数据库操作Service
* @createDate 2024-05-18 19:54:41
*/
public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 根据订单ID返回商品信息
     * @param orderId
     * @return
     */
    List<OrderDetailVO> getOrderDetailById(Long orderId);

    List<OrderDetail> allDetail();

}
