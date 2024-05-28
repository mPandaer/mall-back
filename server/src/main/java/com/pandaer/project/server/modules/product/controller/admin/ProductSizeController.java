package com.pandaer.project.server.modules.product.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.po.AddProductSizePO;
import com.pandaer.project.server.modules.product.po.PageQueryProductSizePO;
import com.pandaer.project.server.modules.product.po.UpdateProductSizePO;
import com.pandaer.project.server.modules.product.service.ProductSizeService;
import com.pandaer.project.server.modules.product.vo.ProductSizeVO;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/admin/product/size")
@RestController
@Tag(name = "商品尺寸管理",description = "对商品尺寸的管理接口")
public class ProductSizeController {

    @Resource
    private ProductSizeService productSizeService;

    @Operation(description = "增加商品尺寸")
    @PostMapping("/add")
    public Resp<Object> addSize(@RequestBody @Validated AddProductSizePO po) {
        productSizeService.addProductSize(po);
        return Resp.success();
    }

    @Operation(description = "更新商品尺寸")
    @PostMapping("/update")
    public Resp<Object> updateSize(@RequestBody @Validated UpdateProductSizePO po) {
        productSizeService.updateProductSize(po);
        return Resp.success();
    }

    @Operation(description = "删除商品尺寸")
    @GetMapping("/delete")
    public Resp<Object> deleteSize(@NotEmpty(message = "删除的尺寸ID不能为空") String idList) {
        productSizeService.batchDeleteProductSize(idList);
        return Resp.success();
    }

    @Operation(description = "分页查询商品尺寸")
    @GetMapping("/page/query")
    public Resp<Page<ProductSizeVO>> pageQuerySize( @Validated PageQueryProductSizePO po) {
        Page<ProductSizeVO> voPage = productSizeService.pageQueryProductSize(po);
        return Resp.success(voPage);
    }

    @Operation(description = "查询所有商品尺寸")
    @GetMapping("/all")
    public Resp<List<ProductSizeVO>> all() {
        List<ProductSizeVO> list = productSizeService.getAll();
        return Resp.success(list);
    }
}
