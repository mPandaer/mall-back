package com.pandaer.project.server.modules.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.base.exception.util.ExceptionUtil;
import com.pandaer.project.base.utils.IdUtil;
import com.pandaer.project.server.modules.order.entity.Order;
import com.pandaer.project.server.modules.order.entity.OrderDetail;
import com.pandaer.project.server.modules.order.enums.OrderEnum;
import com.pandaer.project.server.modules.order.mapper.OrderMapper;
import com.pandaer.project.server.modules.order.po.AddOrderPO;
import com.pandaer.project.server.modules.order.po.PageQueryOrderPO;
import com.pandaer.project.server.modules.order.service.OrderDetailService;
import com.pandaer.project.server.modules.order.service.OrderService;
import com.pandaer.project.server.modules.order.vo.OrderDetailVO;
import com.pandaer.project.server.modules.order.vo.OrderVO;
import com.pandaer.project.server.modules.user.service.AddressService;
import com.pandaer.project.server.modules.user.service.UserService;
import com.pandaer.project.server.modules.user.vo.AddressVO;
import com.pandaer.project.server.modules.user.vo.UserVO;
import com.pandaer.project.server.util.LoginIdUtil;
import jakarta.annotation.Resource;
import org.mockito.internal.matchers.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

/**
* @author pandaer
* @description 针对表【orders】的数据库操作Service实现
* @createDate 2024-05-18 19:54:41
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {


    @Resource
    private UserService userService;

    @Resource
    private AddressService addressService;

    @Resource
    private OrderDetailService orderDetailService;


    /**
     * 分页查询
     * 1. 简单校验参数对象不能为空
     * 2. 通过page()方法进行查询
     * 3. 封装返回对象
     * @param po 带条件的参数实体
     * @return
     */
    @Override
    public IPage<OrderVO> pageQueryOrder(PageQueryOrderPO po) {
        //检查参数
        if (ObjUtil.isNull(po)) {
           ExceptionUtil.business(500,"分页查询参数为空");
        }

        //构建查询条件,并执行分页查询
        Page<Order> orderPage = new Page<>();
        orderPage.setCurrent(po.getCurrentPage());
        orderPage.setSize(po.getPageSize());
        LambdaQueryWrapper<Order> query = Wrappers.lambdaQuery();
        if (ObjUtil.isNotNull(po.getOrderStatus()) && OrderEnum.getByCode(po.getOrderStatus()) != null ) {
            query.eq(Order::getOrderStatus,po.getOrderStatus());
        }
        Page<Order> entityPage = page(orderPage, query);

        //封装返回对象
        Page<OrderVO> voPage = new Page<>();
        BeanUtil.copyProperties(entityPage,voPage,"records");
        List<OrderVO> voList = mappingOrder2VO(entityPage.getRecords());
        voPage.setRecords(voList);
        return voPage;
    }

    /**
     * 将实体对象转换为VO对象
     * @param records
     * @return
     */
    private List<OrderVO> mappingOrder2VO(List<Order> records) {
        return records.stream().map(item -> {
            // todo 待优化 请求数据库连接过多
            OrderVO orderVO = new OrderVO();
            BeanUtil.copyProperties(item,orderVO);
            UserVO userVO = userService.getUserBy(item.getUserId());
            orderVO.setUser(userVO);
            AddressVO addressVO = addressService.getAddressById(item.getAddressId());
            orderVO.setAddress(addressVO);
            return orderVO;
        }).toList();
    }


    /**
     * 根据订单ID 获取订单中的商品信息
     * @param orderId
     * @return
     */
    @Override
    public List<OrderDetailVO> getOrderDetailById(Long orderId) {
        return orderDetailService.getOrderDetailById(orderId);
    }


    /**
     * 根据订单ID 更新订单状态
     * @param orderId
     * @param status
     */
    @Override
    public void updateOrderStatus(Long orderId, Integer status) {
        if (ObjUtil.hasNull(orderId,status)) {
            ExceptionUtil.business(500,"更新订单状态参数为空");
        }
        if (ObjUtil.isNull(getById(orderId))) {
            ExceptionUtil.business(500,"订单不存在");
        }

        if(ObjUtil.isNull(OrderEnum.getByCode(status))) {
            ExceptionUtil.business(500,"订单状态异常");
        }

        LambdaUpdateWrapper<Order> update = Wrappers.lambdaUpdate();
        update.set(Order::getOrderStatus,status).eq(Order::getOrderId,orderId);
        if (!update(update)) {
            ExceptionUtil.business(500,"订单状态更新失败");
        }
    }


    /**
     * 下订单
     * 1. 登录用户与请求的中的userId是否一致
     * 2. 判断AddressId是否存在
     * 3. 转换OrderDetailPO对象
     * 4. 生成订单并保存
     * @param po
     * @return
     */
    @Transactional
    @Override
    public String addOrder(AddOrderPO po) {
        if (ObjectUtil.isNull(po)) {
            ExceptionUtil.business(500,"参数异常");
        }
        Long loginUserId = LoginIdUtil.getUserId();
        Long userId = po.getUserId();
        if (ObjectUtil.notEqual(userId,loginUserId)) {
            ExceptionUtil.business(500,"用户ID参数异常");
        }
        AddressVO entity = addressService.getAddressById(po.getAddressId());
        if (ObjectUtil.isNull(entity)) {
            ExceptionUtil.business(500,"地址不存在");
        }
        Long newOrderId = IdUtil.genNextId();
        List<OrderDetail> orderDetails = po.getDetails().stream().map(item -> BeanUtil.toBean(item, OrderDetail.class)).toList();
        orderDetails.forEach(item -> {
            item.setDetailId(IdUtil.genNextId());
            item.setOrderId(newOrderId);
        });

        Order orderEntity = BeanUtil.toBean(po, Order.class);
        orderEntity.setOrderId(newOrderId);
        //todo 因为没有做支付系统,所以直接将状态设置为待发货状态
        orderEntity.setOrderStatus(OrderEnum.WAITING_FOR_SHIPMENT.getCode());
        orderEntity.setCreateUser(loginUserId);
        orderEntity.setUpdateUser(loginUserId);
        if (!save(orderEntity)) {
            ExceptionUtil.business(500,"保存订单基本信息失败");
        }
        //保存orderDetails 实体
        if (!orderDetailService.saveBatch(orderDetails)) {
            ExceptionUtil.business(500,"保存订单明细失败");
        }

        return String.valueOf(newOrderId);

    }

    /**
     * 根据订单状态,查询订单
     * @return
     */
    @Override
    public List<OrderVO> getOrderByStatus(Integer statusCode) {
        OrderEnum orderStatus = OrderEnum.getByCode(statusCode);
        if (ObjectUtil.isNull(orderStatus)) {
            ExceptionUtil.business(500,"订单状态异常");
        }

        LambdaQueryWrapper<Order> query = Wrappers.lambdaQuery();
        query.eq(Order::getOrderStatus,orderStatus.getCode()).eq(Order::getIsDelete,0).eq(Order::getUserId,LoginIdUtil.getUserId());
        List<Order> list = list(query);
        return list.stream().map(item -> BeanUtil.toBean(item,OrderVO.class)).toList();
    }

    @Override
    public OrderVO getOrderById(Long orderId) {
        if (ObjectUtil.isNull(orderId)) {
            ExceptionUtil.business(500,"参数异常");
        }
        Order entity = getById(orderId); UserVO userVO = userService.getUserBy(entity.getUserId());
        OrderVO orderVO = BeanUtil.toBean(entity, OrderVO.class);
        orderVO.setUser(userVO);
        AddressVO addressVO = addressService.getAddressById(entity.getAddressId());
        orderVO.setAddress(addressVO);

        return orderVO;
    }

    /**
     * 订单状态流转
     * 1. 根据当前的code获取到状态
     * 2. 根据状态进行流转
     * 3. 如果状态为空,判断是否为 之前的状态是否为 交易完成 以及 已退货状态,如果不是抛出异常
     * @param orderStatusCode
     */
    @Override
    public void flowOrderStatus(Long orderId,Integer orderStatusCode) {
        OrderEnum orderStatus = OrderEnum.getByCode(orderStatusCode);
        if (ObjectUtil.isNull(orderStatus)) {
            ExceptionUtil.business(500,"订单状态异常");
        }
        OrderEnum nextOrderStatus = orderStatus.next();
        if (ObjectUtil.isNull(nextOrderStatus)) {
            if (orderStatus != OrderEnum.RETURNED && orderStatus != OrderEnum.TRANSACTION_COMPLETED) {
                ExceptionUtil.business(500,"状态流转失败");
            }
        }

        if (ObjectUtil.isNotNull(nextOrderStatus)) {
            LambdaUpdateWrapper<Order> update = Wrappers.lambdaUpdate();
            update.set(Order::getOrderStatus,nextOrderStatus.getCode()).eq(Order::getOrderId,orderId);
            if (!update(update)) {
                ExceptionUtil.business(500,"状态流转失败");
            }
        }


    }


    /**
     * 1. 判断订单是否存在
     * 2. 目前 只有待发货的订单可以申请退款
     * 3. 检查当前订单的状态 是否为待发货状态
     * 4. 校验成功后,修改订单状态为申请退款状态
     * @param orderId
     */
    @Override
    public void requestRefund(Long orderId) {
        Order entity = getById(orderId);
        Integer orderCode = entity.getOrderStatus();
        OrderEnum orderStatus = OrderEnum.getByCode(orderCode);
        if (orderStatus != OrderEnum.WAITING_FOR_SHIPMENT) {
            ExceptionUtil.business(500,"对不起,当前订单不可退款");
        }
        entity.setOrderStatus(OrderEnum.RETURN_REQUEST.getCode());
        if (!updateById(entity)) {
            ExceptionUtil.business(500,"申请退款失败");
        }
    }
}




