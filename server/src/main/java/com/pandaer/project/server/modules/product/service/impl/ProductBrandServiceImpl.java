package com.pandaer.project.server.modules.product.service.impl;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.server.modules.product.constants.ProductConstants;
import com.pandaer.project.server.modules.product.entity.ProductBrand;
import com.pandaer.project.server.modules.product.po.AddProductBrandPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductBrandPO;
import com.pandaer.project.server.modules.product.po.UpdateProductBrandPO;
import com.pandaer.project.server.modules.product.service.ProductBrandService;
import com.pandaer.project.server.modules.product.mapper.ProductBrandMapper;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import org.springframework.stereotype.Service;

/**
 * 产品品牌服务
*/
@Service
public class ProductBrandServiceImpl extends ServiceImpl<ProductBrandMapper, ProductBrand>
    implements ProductBrandService {


    /**
     * 1. 简单校验一下参数对象
     * 2. 装配实体对象
     * 3. 保存实体对象
     * @param po 参数实体
     */
    @Override
    public void addProductBrand(AddProductBrandPO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500,"添加品牌参数为空");
        }

        //构建实体对象
        ProductBrand productBrand = new ProductBrand();
        productBrand.setBrandId(IdUtil.getSnowflakeNextId());
        productBrand.setBrandName(po.getBrandName());
        productBrand.setBrandLogoUrl(po.getBrandLogoUrl()); //默认处理机制,后续改进
        productBrand.setCreateUser(0L);
        productBrand.setUpdateUser(0L);

        //保存实体对象
        if(!save(productBrand)) {
            throw new BusinessException(500,"添加品牌失败");
        }

    }

    /**
     * 1. 校验品牌是否存在
     * 2. 将对应属性复制一遍
     * 3. 执行更新操作
     * @param po
     */
    @Override
    public void updateProductBrand(UpdateProductBrandPO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500,"修改品牌参数为空");
        }
        ProductBrand entity = getProductBrandById(po.getBrandId());
        if (ObjectUtil.isNull(entity)) {
            throw new BusinessException(500,"品牌不存在");
        }
        BeanUtil.copyProperties(po,entity);
        if (!updateById(entity)) {
            throw new BusinessException(500,"品牌更新失败");
        }

    }


    /**
     * 根据品牌ID获取实体信息 一定保证不为空 为空会抛出异常
     * @param brandId
     * @return
     */
    private ProductBrand getProductBrandById(Long brandId) {
        LambdaQueryWrapper<ProductBrand> query = Wrappers.lambdaQuery();
        query.eq(ProductBrand::getBrandId, brandId)
        .eq(ProductBrand::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        return getOne(query);
    }


    /**
     * 逻辑删除
     * @param brandId
     */
    @Override
    public void deleteProductBrand(Long brandId) {
        if (ObjectUtil.isNull(brandId)) {
            throw new BusinessException(500,"品牌ID为空");
        }
        LambdaUpdateWrapper<ProductBrand> update = Wrappers.lambdaUpdate();
        update.set(ProductBrand::getIsDelete,ProductConstants.PRODUCT_DELETE).eq(ProductBrand::getBrandId,brandId);
        if (!update(update)) {
            throw new BusinessException(500,"品牌删除失败");
        }
    }

    /**
     * 批量删除
     * @param idList 要删除的ID列表, 用逗号分隔
     */
    @Override
    public void batchDeleteProductBrand(String idList) {
        List<Long> list = Arrays.stream(idList.split(",")).map(Long::valueOf).toList();
        LambdaUpdateWrapper<ProductBrand> update = Wrappers.lambdaUpdate();
        update.set(ProductBrand::getIsDelete,ProductConstants.PRODUCT_DELETE).in(ProductBrand::getBrandId,list);
        if (!update(update)) {
            throw new BusinessException(500,"品牌删除失败");
        }
    }

    @Override
    public Page<ProductBrandVO> pageQueryProductBrand(PageQueryProductBrandPO po) {
        //设置查询参数
        Page<ProductBrand> page = new Page<>();
        page.setCurrent(po.getCurrentPage());
        page.setSize(po.getPageSize());

        LambdaQueryWrapper<ProductBrand> query = Wrappers.lambdaQuery();
        query.eq(ProductBrand::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        Page<ProductBrand> brandPage = page(page,query);

        //构建返回实体对象
        Page<ProductBrandVO> voPage = new Page<>();
        BeanUtil.copyProperties(brandPage,voPage,"records");
        List<ProductBrandVO> list = brandPage.getRecords().stream().map((entity) -> BeanUtil.toBean(entity, ProductBrandVO.class)).toList();
        voPage.setRecords(list);

        return voPage;
    }

    @Override
    public ProductBrandVO getBrandById(Long id) {
        ProductBrand entity = getById(id);
        return BeanUtil.toBean(entity,ProductBrandVO.class);
    }

    @Override
    public List<ProductBrandVO> getAll() {
        LambdaQueryWrapper<ProductBrand> query = Wrappers.lambdaQuery();
        query.eq(ProductBrand::getIsDelete,ProductConstants.PRODUCT_NOT_DELETE);
        List<ProductBrand> list = list(query);
        return list.stream().map((item) -> BeanUtil.toBean(item,ProductBrandVO.class)).toList();
    }
}




