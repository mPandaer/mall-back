package com.pandaer.project.server.modules.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.entity.ProductSize;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.product.po.AddProductSizePO;
import com.pandaer.project.server.modules.product.po.PageQueryProductSizePO;
import com.pandaer.project.server.modules.product.po.UpdateProductSizePO;
import com.pandaer.project.server.modules.product.vo.ProductSizeVO;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;

import java.util.List;

/**
* @author pandaer
* @description 针对表【product_sizes】的数据库操作Service
* @createDate 2024-05-17 08:37:21
*/
public interface ProductSizeService extends IService<ProductSize> {
    void addProductSize(AddProductSizePO po);

    void updateProductSize(UpdateProductSizePO po);

    void deleteProductSize(Long SizeId);

    void batchDeleteProductSize(String idList);

    Page<ProductSizeVO> pageQueryProductSize(PageQueryProductSizePO po);

    ProductSizeVO getSizeById(Long id);

    List<ProductSizeVO> getAll();
}
