package com.pandaer.project.server.modules.product.service.impl;
import java.math.BigDecimal;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.po.AddProductPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductPO;
import com.pandaer.project.server.modules.product.po.UpdateProductPO;
import com.pandaer.project.server.modules.product.service.ProductService;
import com.pandaer.project.server.modules.product.vo.ProductVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Resource
    private ProductService productService;


    @Test
    void addProduct() {
        AddProductPO po = new AddProductPO();
        po.setName("耐克超级跑鞋");
        po.setPrice(new BigDecimal("900.99"));
        po.setInventory(10);
        po.setBrandId(1791279722652098560L);
        po.setTypeId(1791341613004886016L);
        po.setColorId(1791356803154980864L);
        po.setSizeId(1791357157544308736L);

        productService.addProduct(po);

    }

    @Test
    void updateProduct() {

        UpdateProductPO updateProductPO = new UpdateProductPO();
        updateProductPO.setProductId(1791381979116609536L);
        updateProductPO.setName("耐克超级跑鞋莆田版");
        productService.updateProduct(updateProductPO);

    }

    @Test
    void batchDeleteProduct() {
        productService.batchDeleteProduct("1791381979116609536");
    }

    @Test
    void pageQueryProduct() {
        PageQueryProductPO po = new PageQueryProductPO();
        po.setCurrentPage(1);
        po.setPageSize(10);
        Page<ProductVO> voPage = productService.pageQueryProduct(po);
        System.out.println(JSONUtil.toJsonPrettyStr(voPage));


    }
}