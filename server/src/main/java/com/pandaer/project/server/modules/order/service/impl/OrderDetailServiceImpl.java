package com.pandaer.project.server.modules.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.server.modules.order.entity.Order;
import com.pandaer.project.server.modules.order.entity.OrderDetail;
import com.pandaer.project.server.modules.order.mapper.OrderDetailMapper;
import com.pandaer.project.server.modules.order.service.OrderDetailService;
import com.pandaer.project.server.modules.order.service.OrderService;
import com.pandaer.project.server.modules.order.vo.OrderDetailVO;
import com.pandaer.project.server.modules.product.service.ProductService;
import com.pandaer.project.server.modules.product.vo.ProductVO;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author pandaer
* @description 针对表【order_details】的数据库操作Service实现
* @createDate 2024-05-18 19:54:41
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

    @Lazy
    @Resource
    private OrderService orderService;

    @Resource
    private ProductService productService;

    /**
     * 1. 校验订单ID是否存在
     * 2. 查询OrderDetail数据
     * 3. 封装成VO对象
     * @param orderId
     * @return
     */
    @Override
    public List<OrderDetailVO> getOrderDetailById(Long orderId) {
        //校验参数
        if (ObjUtil.isNull(orderId)) {
            throw new BusinessException(500,"订单ID为空");
        }
        Order entity = orderService.getById(orderId);
        if (ObjUtil.isNull(entity)) {
            throw new BusinessException(500,"订单不存在");
        }

        //构造查询条件
        LambdaQueryWrapper<OrderDetail> query = Wrappers.lambdaQuery();
        query.eq(OrderDetail::getOrderId,orderId);
        List<OrderDetail> list = list(query);

        //封装返回对象
        List<OrderDetailVO> voList = list.stream().map(item -> BeanUtil.toBean(item, OrderDetailVO.class)).toList();
        Map<Long, Long> detailToProductMap = list.stream().collect(Collectors.toMap(OrderDetail::getDetailId, OrderDetail::getProductId));
        Map<Long,ProductVO> productVOMap = productService.getProductVOMap(detailToProductMap);
        if (productVOMap!= null && !productVOMap.isEmpty()){
            voList.forEach((item) -> {
                item.setProduct(productVOMap.get(item.getDetailId()));
            });
        }
        return voList;
    }


    /**
     * 返回全部订单明细
     * @return
     */
    @Override
    public List<OrderDetail> allDetail() {
        return list();
    }
}




