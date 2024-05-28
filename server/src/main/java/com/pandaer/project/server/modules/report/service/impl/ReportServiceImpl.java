package com.pandaer.project.server.modules.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.pandaer.project.server.modules.order.entity.Order;
import com.pandaer.project.server.modules.order.entity.OrderDetail;
import com.pandaer.project.server.modules.order.service.OrderDetailService;
import com.pandaer.project.server.modules.order.service.OrderService;
import com.pandaer.project.server.modules.product.entity.Product;
import com.pandaer.project.server.modules.product.service.ProductService;
import com.pandaer.project.server.modules.product.service.ProductTypeService;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;
import com.pandaer.project.server.modules.report.dto.MarketItemDTO;
import com.pandaer.project.server.modules.report.dto.PerformanceDTO;
import com.pandaer.project.server.modules.report.dto.TrafficItemDTO;
import com.pandaer.project.server.modules.report.service.ReportService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 报表服务
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ProductTypeService productTypeService;

    @Resource
    private ProductService productService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private OrderService orderService;

    /**
     * 获取营销报表数据
     * 1. 获取的全部的商品类型
     * 2. 根据不同的商品类型,查询出对应类型的商品ID
     * 3. 从订单明细中,筛选出已购买的对应类型的ID,并统计数量
     * 4. 封装返回实体
     * @return
     */
    @Override
    public List<MarketItemDTO> getMarketReportData() {
        List<ProductTypeVO> typeList = productTypeService.getAll();
        Map<Long, String> typeIdToNameMap = typeList.stream().collect(Collectors.toMap(ProductTypeVO::getTypeId, ProductTypeVO::getTypeName));
        //利用Map缓存对应的商品ID集合, key typeId,value productIdList
        Map<Long, List<Long>> typeMap = typeList.stream()
                .collect(Collectors.toMap(ProductTypeVO::getTypeId, (item) -> {
            List<Product> productList = productService.getProductListByType(item.getTypeId());
            return productList.stream().map(Product::getProductId).toList();
        }));

        List<OrderDetail> orderDetailList = orderDetailService.allDetail();
        List<Long> orderProductIdList = orderDetailList.stream().map(OrderDetail::getProductId).toList();

        ArrayList<MarketItemDTO> list = new ArrayList<>();
        typeMap.forEach((key,value) -> {
            MarketItemDTO dto = new MarketItemDTO();
            dto.setType(typeIdToNameMap.get(key));
            dto.setValue(CollUtil.count(orderProductIdList, value::contains));
            list.add(dto);
        });

        return list;
    }


    /**
     * 业绩报表
     * 1. 获取全部的商品信息
     * 2. 获取订单中的商品信息
     * 3. 统计出订单中对应的商品数量
     * @return
     */
    @Override
    public List<PerformanceDTO> getPerformanceData() {
        List<Product> allProductList = productService.list();
        Map<Long, String> productId2NameMap = allProductList.stream().collect(Collectors.toMap(Product::getProductId, Product::getName));
        List<OrderDetail> orderDetailList = orderDetailService.allDetail();

        List<Long> orderProductIdList = orderDetailList.stream().map(OrderDetail::getProductId).toList();
        List<Long> productIdList = allProductList.stream().map(Product::getProductId).toList();

        ArrayList<PerformanceDTO> list = new ArrayList<>();
        productIdList.forEach(productId -> {
            PerformanceDTO dto = new PerformanceDTO();
            dto.setType(productId2NameMap.get(productId));
            dto.setValue(CollUtil.count(orderProductIdList,(orderProductId) -> Objects.equals(orderProductId, productId)));
            list.add(dto);
        });
        return list;
    }

    /**
     * 流量报表
     * 1. 获取全部的基础订单,按照日期统计
     * @return
     */
    @Override
    public List<TrafficItemDTO> getTrafficData() {
        List<Order> orderList = orderService.list();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Integer> orderCountByDate = orderList.stream()
                .collect(Collectors.groupingBy(order -> order.getCreateTime().toLocalDate().format(formatter),
                        Collectors.summingInt(order -> 1)));

        ArrayList<TrafficItemDTO> list = new ArrayList<>();
        orderCountByDate.forEach((date,count) -> {
            TrafficItemDTO trafficItemDTO = new TrafficItemDTO();
            trafficItemDTO.setDate(date);
            trafficItemDTO.setSum(count);
            list.add(trafficItemDTO);
        });

        return list;
    }
}
