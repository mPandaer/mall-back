package com.pandaer.project.server.modules.product.controller.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.modules.product.po.PageQueryProductBrandPO;
import com.pandaer.project.server.modules.product.service.ProductBrandService;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
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
@RequestMapping("product/brand")
@RestController
@Tag(name = "公开的商品品牌管理")
public class ClientProductBrandController {

    @Resource
    private ProductBrandService productBrandService;

    @NotLogin
    @Operation(description = "查询所有商品品牌")
    @GetMapping("/all")
    public Resp<List<ProductBrandVO>> all() {
        List<ProductBrandVO> list = productBrandService.getAll();
        return Resp.success(list);
    }

    @NotLogin
    @Operation(description = "分页查询商品品牌")
    @GetMapping("/page/query")
    public Resp<Page<ProductBrandVO>> pageQueryBrand(@Validated PageQueryProductBrandPO po) {
        Page<ProductBrandVO> voPage = productBrandService.pageQueryProductBrand(po);
        return Resp.success(voPage);
    }
}
