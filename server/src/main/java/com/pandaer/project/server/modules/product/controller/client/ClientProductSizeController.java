package com.pandaer.project.server.modules.product.controller.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.modules.product.po.PageQueryProductBrandPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductSizePO;
import com.pandaer.project.server.modules.product.service.ProductBrandService;
import com.pandaer.project.server.modules.product.service.ProductSizeService;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import com.pandaer.project.server.modules.product.vo.ProductSizeVO;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequestMapping("/product/size")
@RestController
@Tag(name = "公开的商品尺码管理")
public class ClientProductSizeController {

    @Resource
    private ProductSizeService productSizeService;

    @NotLogin
    @Operation(description = "分页查询商品尺寸")
    @GetMapping("/page/query")
    public Resp<Page<ProductSizeVO>> pageQuerySize(@Validated PageQueryProductSizePO po) {
        Page<ProductSizeVO> voPage = productSizeService.pageQueryProductSize(po);
        return Resp.success(voPage);
    }

    @NotLogin
    @Operation(description = "查询所有商品尺寸")
    @GetMapping("/all")
    public Resp<List<ProductSizeVO>> all() {
        List<ProductSizeVO> list = productSizeService.getAll();
        return Resp.success(list);
    }
}
