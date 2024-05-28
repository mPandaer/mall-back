package com.pandaer.project.server.modules.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.entity.ProductBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.product.po.AddProductBrandPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductBrandPO;
import com.pandaer.project.server.modules.product.po.UpdateProductBrandPO;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;

import java.util.List;

/**
 * 商品品牌服务 提供基本的增删改查 搜索服务
*/
public interface ProductBrandService extends IService<ProductBrand> {

    void addProductBrand(AddProductBrandPO po);

    void updateProductBrand(UpdateProductBrandPO po);

    void deleteProductBrand(Long brandId);

    /**
     * 批量删除
     * @param idList
     */
    void batchDeleteProductBrand(String idList);

    Page<ProductBrandVO> pageQueryProductBrand(PageQueryProductBrandPO po);

    ProductBrandVO getBrandById(Long id);
    List<ProductBrandVO> getAll();




}
