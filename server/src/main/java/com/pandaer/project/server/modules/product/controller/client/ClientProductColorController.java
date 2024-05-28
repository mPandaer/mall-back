package com.pandaer.project.server.modules.product.controller.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.modules.product.po.PageQueryProductBrandPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductColorPO;
import com.pandaer.project.server.modules.product.service.ProductBrandService;
import com.pandaer.project.server.modules.product.service.ProductColorService;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import com.pandaer.project.server.modules.product.vo.ProductColorVO;
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
@RequestMapping("/product/color")
@RestController
@Tag(name = "公共的商品颜色服务")
public class ClientProductColorController {

    @Resource
    private ProductColorService productColorService;

    @NotLogin
    @Operation(description = "分页查询商品颜色")
    @GetMapping("/page/query")
    public Resp<Page<ProductColorVO>> pageQueryColor(@Validated PageQueryProductColorPO po) {
        Page<ProductColorVO> voPage = productColorService.pageQueryProductColor(po);
        return Resp.success(voPage);
    }

    @NotLogin
    @Operation(description = "查询所有商品颜色")
    @GetMapping("/all")
    public Resp<List<ProductColorVO>> all() {
        List<ProductColorVO> list = productColorService.getAll();
        return Resp.success(list);
    }
}
