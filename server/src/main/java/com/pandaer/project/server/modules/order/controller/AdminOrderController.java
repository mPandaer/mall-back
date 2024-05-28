package com.pandaer.project.server.modules.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.modules.order.po.AddOrderPO;
import com.pandaer.project.server.modules.order.po.PageQueryOrderPO;
import com.pandaer.project.server.modules.order.service.OrderService;
import com.pandaer.project.server.modules.order.vo.OrderDetailVO;
import com.pandaer.project.server.modules.order.vo.OrderVO;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Tag(name = "订单管理接口", description = "对订单进行管理的一些基本操作")
@RequestMapping("/admin/order")
@RestController
public class AdminOrderController {

    @Resource
    private OrderService orderService;

    @Operation(description = "分页获取不同状态的订单数据")
    @GetMapping("/page/query")
    public Resp<IPage<OrderVO>> pageQueryOrder(@Validated PageQueryOrderPO po) {
        IPage<OrderVO> orderVOIPage = orderService.pageQueryOrder(po);
        return Resp.success(orderVOIPage);
    }

    @Operation(description = "根据订单ID查询订单明细")
    @GetMapping("/detail/{orderId}")
    public Resp<List<OrderDetailVO>> getOrderDetailById(@PathVariable("orderId") @NotNull(message = "订单ID不能为空") Long orderId) {
        List<OrderDetailVO> vo = orderService.getOrderDetailById(orderId);
        return Resp.success(vo);
    }

    @Operation(description = "根据订单ID查询订单状态")
    @PutMapping("/status/{orderId}")
    public Resp<Object> updateOrderStatus(@PathVariable("orderId") @NotNull(message = "订单ID不能为空") Long orderId,
                                          @NotNull(message = "订单状态不能为空") Integer orderStatus) {

        orderService.updateOrderStatus(orderId,orderStatus);
        return Resp.success();
    }

    @NotLogin
    @Operation(description = "下订单")
    @PostMapping("/add")
    public Resp<String> addOrder(@RequestBody @Validated AddOrderPO po) {
        String orderId = orderService.addOrder(po);
        return Resp.success(orderId);
    }

    @NotLogin
    @Operation(description = "根据订单状态查询订单基本信息")
    @GetMapping("/get/status")
    public Resp<List<OrderVO>> getOrderListByStatus(@NotNull(message = "订单状态不能为空") Integer statusCode) {
        List<OrderVO> orderByStatus = orderService.getOrderByStatus(statusCode);
        return Resp.success(orderByStatus);
    }

    @NotLogin
    @Operation(description = "根据订单ID查询订单基本信息")
    @GetMapping("/get/{id}")
    public Resp<OrderVO> getOrderById(@NotEmpty(message = "订单ID不能为空")  @PathVariable("id") String orderId) {
        OrderVO vo = orderService.getOrderById(Long.valueOf(orderId));
        return Resp.success(vo);
    }

    @NotLogin
    @Operation(description = "订单状态流转")
    @GetMapping("/flow/status")
    public Resp<Object> flowOrderStatus(String orderId,Integer curOrderStatus) {
        orderService.flowOrderStatus(Long.valueOf(orderId),curOrderStatus);
        return Resp.success();
    }

//    @NeedLogin
//    @Operation(description = "申请退款")
//    @GetMapping("/request/refund")
//    public Resp<Object> requestRefund(String orderId) {
//        orderService.requestRefund(Long.valueOf(orderId));
//        return Resp.success();
//    }


}
