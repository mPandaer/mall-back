package com.pandaer.project.server.modules.product.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.po.AddProductBrandPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductBrandPO;
import com.pandaer.project.server.modules.product.po.UpdateProductBrandPO;
import com.pandaer.project.server.modules.product.service.ProductBrandService;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ProductBrandServiceTest {

    @Resource
    private ProductBrandService productBrandService;

    @Test
    void addProductBrand() {
        AddProductBrandPO po = new AddProductBrandPO();
        po.setBrandName("天猫");
        productBrandService.addProductBrand(po);
    }

    @Test
    void updateProductBrand() {
        UpdateProductBrandPO po = new UpdateProductBrandPO();
        po.setBrandId(1791278242406596608L);
        po.setBrandName("天猫一号");
        po.setIsEnable(0);
        productBrandService.updateProductBrand(po);
    }

    @Test
    void deleteProductBrand() {
        productBrandService.deleteProductBrand(1791279722652098560L);
    }

    @Test
    void pageQueryProductBrand() {
        PageQueryProductBrandPO po = new PageQueryProductBrandPO();
        po.setPageSize(10);
        po.setCurrentPage(1L);
        Page<ProductBrandVO> voPage = productBrandService.pageQueryProductBrand(po);
        System.out.println(JSONUtil.toJsonPrettyStr(voPage));
    }
}