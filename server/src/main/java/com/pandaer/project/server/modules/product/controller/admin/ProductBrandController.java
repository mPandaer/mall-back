package com.pandaer.project.server.modules.product.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.po.AddProductBrandPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductBrandPO;
import com.pandaer.project.server.modules.product.po.UpdateProductBrandPO;
import com.pandaer.project.server.modules.product.service.ProductBrandService;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
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
@RequestMapping("/admin/product/brand")
@RestController
@Tag(name = "商品品牌管理",description = "对商品品牌的管理接口")
public class ProductBrandController {

    @Resource
    private ProductBrandService productBrandService;

    @Operation(description = "增加商品品牌")
    @PostMapping("/add")
    public Resp<Object> addBrand(@RequestBody @Validated AddProductBrandPO po) {
        productBrandService.addProductBrand(po);
        return Resp.success();
    }

    @Operation(description = "更新商品品牌")
    @PostMapping("/update")
    public Resp<Object> updateBrand(@RequestBody @Validated UpdateProductBrandPO po) {
        productBrandService.updateProductBrand(po);
        return Resp.success();
    }

    @Operation(description = "删除商品品牌")
    @GetMapping("/delete")
    public Resp<Object> deleteBrand(@NotEmpty(message = "删除的品牌ID不能为空") String idList) {
        productBrandService.batchDeleteProductBrand(idList);
        return Resp.success();
    }

    @Operation(description = "分页查询商品品牌")
    @GetMapping("/page/query")
    public Resp<Page<ProductBrandVO>> pageQueryBrand( @Validated PageQueryProductBrandPO po) {
        Page<ProductBrandVO> voPage = productBrandService.pageQueryProductBrand(po);
        return Resp.success(voPage);
    }

    @Operation(description = "查询所有商品品牌")
    @GetMapping("/all")
    public Resp<List<ProductBrandVO>> all() {
        List<ProductBrandVO> list = productBrandService.getAll();
        return Resp.success(list);
    }
}
