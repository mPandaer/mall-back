package com.pandaer.project.server.modules.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.entity.ProductColor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.product.po.AddProductColorPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductColorPO;
import com.pandaer.project.server.modules.product.po.UpdateProductColorPO;
import com.pandaer.project.server.modules.product.vo.ProductColorVO;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;

import java.util.List;


/**
* @author pandaer
* @description 针对表【product_colors】的数据库操作Service
* @createDate 2024-05-17 08:37:21
*/
public interface ProductColorService extends IService<ProductColor> {

    void addProductColor(AddProductColorPO po);

    void updateProductColor(UpdateProductColorPO po);

    void deleteProductColor(Long ColorId);

    void batchDeleteProductColor(String idList);

    Page<ProductColorVO> pageQueryProductColor(PageQueryProductColorPO po);

    ProductColorVO getColorById(Long id);
    List<ProductColorVO> getAll();

}
