package com.pandaer.project.server.modules.product.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.po.AddProductPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductPO;
import com.pandaer.project.server.modules.product.po.UpdateProductPO;
import com.pandaer.project.server.modules.product.service.ProductService;
import com.pandaer.project.server.modules.product.vo.ProductVO;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/admin/product")
@RestController
@Tag(name = "商品管理",description = "对商品进行管理")
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping("/add")
    @Operation(description = "增加商品")
    public Resp<Object> add(@RequestBody @Validated AddProductPO po) {
        productService.addProduct(po);
        return Resp.success();
    }

    @PostMapping("/update")
    @Operation(description = "修改商品")
    public Resp<Object> update(@RequestBody @Validated UpdateProductPO po) {
        productService.updateProduct(po);
        return Resp.success();
    }

    @GetMapping("/delete")
    @Operation(description = "删除商品")
    public Resp<Object> delete(@NotEmpty(message = "删除的商品ID列表不能为空") String idList) {
        productService.batchDeleteProduct(idList);
        return Resp.success();
    }

    @GetMapping("/query")
    @Operation(description = "分页查询商品")
    public Resp<Page<ProductVO>> pageQuery(@Validated PageQueryProductPO po) {
        Page<ProductVO> voPage = productService.pageQueryProduct(po);
        return Resp.success(voPage);
    }

    @GetMapping("/one/{id}")
    @Operation(description = "根据ID查询商品")
    public Resp<ProductVO> getOne(@PathVariable("id") @NotEmpty(message = "商品ID不能为空") String productId) {
        ProductVO productVO = productService.getProductById(Long.valueOf(productId));
        return Resp.success(productVO);
    }




}
