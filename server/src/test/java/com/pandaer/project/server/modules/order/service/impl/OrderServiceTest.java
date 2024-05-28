package com.pandaer.project.server.modules.order.service.impl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.project.base.utils.IdUtil;
import com.pandaer.project.server.modules.order.entity.Order;
import com.pandaer.project.server.modules.order.entity.OrderDetail;
import com.pandaer.project.server.modules.order.enums.OrderEnum;
import com.pandaer.project.server.modules.order.po.PageQueryOrderPO;
import com.pandaer.project.server.modules.order.service.OrderDetailService;
import com.pandaer.project.server.modules.order.service.OrderService;
import com.pandaer.project.server.modules.order.vo.OrderDetailVO;
import com.pandaer.project.server.modules.order.vo.OrderVO;
import com.pandaer.project.server.modules.product.entity.Product;
import com.pandaer.project.server.modules.product.service.ProductService;
import com.pandaer.project.server.modules.user.entity.Address;
import com.pandaer.project.server.modules.user.entity.User;
import com.pandaer.project.server.modules.user.service.AddressService;
import com.pandaer.project.server.modules.user.service.UserService;
import com.pandaer.project.server.modules.user.utils.PasswordUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OrderServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private AddressService addressService;

    @Resource
    private ProductService productService;

    @Resource
    private OrderService orderService;

    @Resource
    private OrderDetailService orderDetailService;


    /**
     * 准备测试环境
     * 1. Order与用户,地址,商品管理,所以需要新建一个用户
     * 为这个用户创建一个地址
     * 然后还需要添加一个订单,订单和商品信息挂钩
     */
    @Test
    void prepared() {
        //添加用户
        User user = new User();
        user.setUserId(IdUtil.genNextId());
        user.setUsername("order_test_2");
        user.setPassword(PasswordUtil.hashPassword("12345678"));
        user.setEmail("1111@qq.com");
        user.setAvatarUrl("");
        user.setCreateUser(0L);
        user.setUpdateUser(0L);
        userService.save(user);


        //添加地址
        Address address = new Address();
        address.setAddressId(IdUtil.genNextId());
        address.setUserId(user.getUserId());
        address.setRecipientName("order_test_real_2");
        address.setRecipientPhone("12345678901");
        address.setProvince("四川");
        address.setCity("成都");
        address.setAddress("四川-成都-双流");
        address.setCreateUser(0L);
        address.setUpdateUser(0L);
        addressService.save(address);

        //添加一个商品
        Product product = new Product();
        product.setProductId(IdUtil.genNextId());
        product.setName("超级跑步鞋-Nike_2");
        product.setDetailImgUrls("");
        product.setPrice(new BigDecimal("999.0"));
        product.setBrandId(1791356684552646656L);
        product.setTypeId(1791356745198088192L);
        product.setColorId(1791357080608190464L);
        product.setSizeId(1791357128528113664L);
        product.setInventory(10);
        product.setCreateUser(0L);
        product.setUpdateUser(0L);
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        productService.save(product);

        //添加一个订单
        Order order = new Order();
        order.setOrderId(IdUtil.genNextId());
        order.setUserId(user.getUserId());
        order.setAddressId(address.getAddressId());
        order.setOrderStatus(OrderEnum.WAITING_FOR_SHIPMENT.getCode());
        order.setTotalAmount(new BigDecimal("100.00"));
        order.setCreateUser(0L);
        order.setUpdateUser(0L);
        orderService.save(order);

        //建立订单和商品之间的关系
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(IdUtil.genNextId());
        orderDetail.setOrderId(order.getOrderId());
        orderDetail.setProductId(product.getProductId());
        orderDetail.setQuantity(2);
        orderDetail.setUnitPrice(new BigDecimal("100"));
        orderDetail.setTotalPrice(new BigDecimal("200"));
        orderDetailService.save(orderDetail);
    }

    @Test
    void pageQueryOrder() {
        PageQueryOrderPO pageQueryOrderPO = new PageQueryOrderPO();
        pageQueryOrderPO.setCurrentPage(1);
        pageQueryOrderPO.setPageSize(10);
        pageQueryOrderPO.setOrderStatus(OrderEnum.SHIPPING.getCode());
        IPage<OrderVO> orderVOIPage = orderService.pageQueryOrder(pageQueryOrderPO);
        System.out.println(JSONUtil.toJsonPrettyStr(orderVOIPage));
    }

    @Test
    void getOrderDetailById() {
        List<OrderDetailVO> orderDetailById = orderService.getOrderDetailById(1792393071430860800L);
        System.out.println(JSONUtil.toJsonPrettyStr(orderDetailById));
    }

    @Test
    void updateOrderStatus() {
        orderService.updateOrderStatus(1792393071430860800L,OrderEnum.SHIPPING.getCode());
    }
}