package com.pandaer.project.server.modules.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.order.po.AddOrderPO;
import com.pandaer.project.server.modules.order.po.PageQueryOrderPO;
import com.pandaer.project.server.modules.order.vo.OrderDetailVO;
import com.pandaer.project.server.modules.order.vo.OrderVO;

import java.util.List;

/**
 * 订单基本服务 统一的入口
*/
public interface OrderService extends IService<Order> {

    /**
     * 带条件的分页查询订单数据
     * @param po 带条件的参数实体
     * @return
     */
    IPage<OrderVO> pageQueryOrder(PageQueryOrderPO po);

    /**
     * 根据ID 返回详细信息
     * @param orderId
     * @return
     */
    List<OrderDetailVO> getOrderDetailById(Long orderId);

    /**
     * 根据ID修改订单的状态
     * @param orderId
     * @param status
     */
    void updateOrderStatus(Long orderId,Integer status);


    /**
     * 增加订单
     * @param po
     * @return
     */
    String addOrder(AddOrderPO po);

    List<OrderVO> getOrderByStatus(Integer statusCode);

    /**
     * 根据订单的ID返回订单基本信息
     */

    OrderVO getOrderById(Long orderId);

    /**
     * 订单的状态流转
     * @param orderStatusCode
     */
    void flowOrderStatus(Long orderId,Integer orderStatusCode);

    /**
     * 申请退款
     */
    void requestRefund(Long orderId);

}
