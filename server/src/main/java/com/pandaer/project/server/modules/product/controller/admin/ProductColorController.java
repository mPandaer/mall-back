package com.pandaer.project.server.modules.product.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.po.AddProductColorPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductColorPO;
import com.pandaer.project.server.modules.product.po.UpdateProductColorPO;
import com.pandaer.project.server.modules.product.service.ProductColorService;
import com.pandaer.project.server.modules.product.vo.ProductColorVO;
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
@RequestMapping("/admin/product/color")
@RestController
@Tag(name = "商品颜色管理",description = "对商品颜色的管理接口")
public class ProductColorController {

    @Resource
    private ProductColorService productColorService;

    @Operation(description = "增加商品颜色")
    @PostMapping("/add")
    public Resp<Object> addColor(@RequestBody @Validated AddProductColorPO po) {
        productColorService.addProductColor(po);
        return Resp.success();
    }

    @Operation(description = "更新商品颜色")
    @PostMapping("/update")
    public Resp<Object> updateColor(@RequestBody @Validated UpdateProductColorPO po) {
        productColorService.updateProductColor(po);
        return Resp.success();
    }

    @Operation(description = "删除商品颜色")
    @GetMapping("/delete")
    public Resp<Object> deleteColor(@NotEmpty(message = "删除的颜色ID不能为空") String idList) {
        productColorService.batchDeleteProductColor(idList);
        return Resp.success();
    }

    @Operation(description = "分页查询商品颜色")
    @GetMapping("/page/query")
    public Resp<Page<ProductColorVO>> pageQueryColor( @Validated PageQueryProductColorPO po) {
        Page<ProductColorVO> voPage = productColorService.pageQueryProductColor(po);
        return Resp.success(voPage);
    }

    @Operation(description = "查询所有商品颜色")
    @GetMapping("/all")
    public Resp<List<ProductColorVO>> all() {
        List<ProductColorVO> list = productColorService.getAll();
        return Resp.success(list);
    }
}
