package com.pandaer.project.server.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.server.modules.order.entity.OrderStatusHistory;
import com.pandaer.project.server.modules.order.mapper.OrderStatusHistoryMapper;
import com.pandaer.project.server.modules.order.service.OrderStatusHistoryService;
import org.springframework.stereotype.Service;

/**
* @author pandaer
* @description 针对表【order_status_history】的数据库操作Service实现
* @createDate 2024-05-18 19:54:41
*/
@Service
public class OrderStatusHistoryServiceImpl extends ServiceImpl<OrderStatusHistoryMapper, OrderStatusHistory>
    implements OrderStatusHistoryService {

}




