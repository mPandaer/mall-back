package com.pandaer.project.server.modules.product.controller.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.modules.product.po.PageQueryProductPO;
import com.pandaer.project.server.modules.product.service.ProductService;
import com.pandaer.project.server.modules.product.vo.ProductVO;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/product")
@RestController
@Tag(name = "公开的商品服务")
public class ClientProductController {

    @Resource
    private ProductService productService;

    @NotLogin
    @GetMapping("/query")
    @Operation(description = "分页查询商品")
    public Resp<Page<ProductVO>> pageQuery(@Validated PageQueryProductPO po) {
        Page<ProductVO> voPage = productService.pageQueryProduct(po);
        return Resp.success(voPage);
    }

    @NotLogin
    @GetMapping("/one/{id}")
    @Operation(description = "根据ID查询商品")
    public Resp<ProductVO> getOne(@PathVariable("id") @NotEmpty(message = "商品ID不能为空") String productId) {
        ProductVO productVO = productService.getProductById(Long.valueOf(productId));
        return Resp.success(productVO);
    }
}
