package com.pandaer.project.server.modules.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.entity.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.product.po.AddProductTypePO;
import com.pandaer.project.server.modules.product.po.PageQueryProductTypePO;
import com.pandaer.project.server.modules.product.po.UpdateProductTypePO;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;

import java.util.List;

/**
* @author pandaer
* @description 针对表【product_types】的数据库操作Service
* @createDate 2024-05-17 08:37:21
*/
public interface ProductTypeService extends IService<ProductType> {

    void addProductType(AddProductTypePO po);

    void updateProductType(UpdateProductTypePO po);

    void deleteProductType(Long TypeId);

    void batchDeleteProductType(String idList);

    Page<ProductTypeVO> pageQueryProductType(PageQueryProductTypePO po);

    ProductTypeVO getTypeById(Long id);

    List<ProductTypeVO> getAll();
}

