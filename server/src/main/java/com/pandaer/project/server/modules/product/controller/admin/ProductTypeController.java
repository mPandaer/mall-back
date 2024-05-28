package com.pandaer.project.server.modules.product.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.po.AddProductTypePO;
import com.pandaer.project.server.modules.product.po.PageQueryProductTypePO;
import com.pandaer.project.server.modules.product.po.UpdateProductTypePO;
import com.pandaer.project.server.modules.product.service.ProductTypeService;
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
@RequestMapping("/admin/product/type")
@RestController
@Tag(name = "商品类型管理",description = "对商品类型的管理接口")
public class ProductTypeController {

    @Resource
    private ProductTypeService productTypeService;

    @Operation(description = "增加商品类型")
    @PostMapping("/add")
    public Resp<Object> addType(@RequestBody @Validated AddProductTypePO po) {
        productTypeService.addProductType(po);
        return Resp.success();
    }

    @Operation(description = "更新商品类型")
    @PostMapping("/update")
    public Resp<Object> updateType(@RequestBody @Validated UpdateProductTypePO po) {
        productTypeService.updateProductType(po);
        return Resp.success();
    }

    @Operation(description = "删除商品类型")
    @GetMapping("/delete")
    public Resp<Object> deleteType(@NotEmpty(message = "删除的类型ID不能为空") String idList) {
        productTypeService.batchDeleteProductType(idList);
        return Resp.success();
    }

    @Operation(description = "分页查询商品类型")
    @GetMapping("/page/query")
    public Resp<Page<ProductTypeVO>> pageQueryType( @Validated PageQueryProductTypePO po) {
        Page<ProductTypeVO> voPage = productTypeService.pageQueryProductType(po);
        return Resp.success(voPage);
    }

    @Operation(description = "查询所有商品类型")
    @GetMapping("/all")
    public Resp<List<ProductTypeVO>> all() {
        List<ProductTypeVO> list = productTypeService.getAll();
        return Resp.success(list);
    }
}
