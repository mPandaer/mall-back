package com.pandaer.project.server.modules.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.product.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.product.po.AddProductPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductPO;
import com.pandaer.project.server.modules.product.po.UpdateProductPO;
import com.pandaer.project.server.modules.product.vo.ProductDetailVO;
import com.pandaer.project.server.modules.product.vo.ProductVO;

import java.util.List;
import java.util.Map;

/**
 * 商品的增删改查
 */
public interface ProductService extends IService<Product> {

    /**
     *  添加产品
     * @param po
     */
    void addProduct(AddProductPO po);

    /**
     * 修改产品信息
     * @param po
     */
    void updateProduct(UpdateProductPO po);

    /**
     * 批量删除产品
     * @param idList
     */
    void batchDeleteProduct(String idList);

    /**
     * 分页查询产品
     * @param po
     * @return
     */
    Page<ProductVO> pageQueryProduct(PageQueryProductPO po);


    ProductVO getProductById(Long productId);

    /**
     * 输入的map的value是Product的ID
     * 返回是 key保持不变
     * @param detailToProductMap
     * @return
     */
    Map<Long, ProductVO> getProductVOMap(Map<Long, Long> detailToProductMap);

    ProductDetailVO getProductDetailByProductId(String productId);

    List<Product> getProductListByType(Long typeId);
}
