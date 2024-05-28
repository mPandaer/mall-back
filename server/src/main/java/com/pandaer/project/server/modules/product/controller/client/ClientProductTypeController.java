package com.pandaer.project.server.modules.product.controller.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.modules.product.po.PageQueryProductBrandPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductTypePO;
import com.pandaer.project.server.modules.product.service.ProductBrandService;
import com.pandaer.project.server.modules.product.service.ProductTypeService;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;
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
@RequestMapping("product/type")
@RestController
@Tag(name = "公开的商品类型服务")
public class ClientProductTypeController {

    @Resource
    private ProductTypeService productTypeService;

    @NotLogin
    @Operation(description = "分页查询商品类型")
    @GetMapping("/page/query")
    public Resp<Page<ProductTypeVO>> pageQueryType(@Validated PageQueryProductTypePO po) {
        Page<ProductTypeVO> voPage = productTypeService.pageQueryProductType(po);
        return Resp.success(voPage);
    }

    @NotLogin
    @Operation(description = "查询所有商品类型")
    @GetMapping("/all")
    public Resp<List<ProductTypeVO>> all() {
        List<ProductTypeVO> list = productTypeService.getAll();
        return Resp.success(list);
    }
}
