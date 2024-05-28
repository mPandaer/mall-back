package com.pandaer.project.server.modules.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.server.modules.product.constants.ProductConstants;
import com.pandaer.project.server.modules.product.entity.*;
import com.pandaer.project.server.modules.product.entity.ProductSize;
import com.pandaer.project.server.modules.product.po.AddProductSizePO;
import com.pandaer.project.server.modules.product.po.PageQueryProductSizePO;
import com.pandaer.project.server.modules.product.po.UpdateProductSizePO;
import com.pandaer.project.server.modules.product.service.ProductSizeService;
import com.pandaer.project.server.modules.product.mapper.ProductSizeMapper;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import com.pandaer.project.server.modules.product.vo.ProductSizeVO;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
* @author pandaer
* @description 针对表【product_sizes】的数据库操作Service实现
* @createDate 2024-05-17 08:37:21
*/
@Service
public class ProductSizeServiceImpl extends ServiceImpl<ProductSizeMapper, ProductSize>
    implements ProductSizeService{

    /**
     * 1. 简单校验一下参数对象
     * 2. 装配实体对象
     * 3. 保存实体对象
     * @param po 参数实体
     */
    @Override
    public void addProductSize(AddProductSizePO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500,"添加尺寸参数为空");
        }

        //构建实体对象
        ProductSize productSize = new ProductSize();
        productSize.setSizeId(IdUtil.getSnowflakeNextId());
        productSize.setSizeName(po.getSizeName());
        productSize.setCreateUser(0L);
        productSize.setUpdateUser(0L);

        //保存实体对象
        if(!save(productSize)) {
            throw new BusinessException(500,"添加尺寸失败");
        }

    }

    /**
     * 1. 校验尺寸是否存在
     * 2. 将对应属性复制一遍
     * 3. 执行更新操作
     * @param po
     */
    @Override
    public void updateProductSize(UpdateProductSizePO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500,"修改尺寸参数为空");
        }
        ProductSize entity = getProductSizeById(po.getSizeId());
        if (ObjectUtil.isNull(entity)) {
            throw new BusinessException(500,"尺寸不存在");
        }
        BeanUtil.copyProperties(po,entity);
        if (!updateById(entity)) {
            throw new BusinessException(500,"尺寸更新失败");
        }

    }


    /**
     * 根据尺寸ID获取实体信息 一定保证不为空 为空会抛出异常
     * @param sizeId
     * @return
     */
    private ProductSize getProductSizeById(Long sizeId) {
        LambdaQueryWrapper<ProductSize> query = Wrappers.lambdaQuery();
        query.eq(ProductSize::getSizeId, sizeId)
                .eq(ProductSize::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        ProductSize entity = getOne(query);
        return entity;
    }


    /**
     * 逻辑删除
     * @param sizeId
     */
    @Override
    public void deleteProductSize(Long sizeId) {
        if (ObjectUtil.isNull(sizeId)) {
            throw new BusinessException(500,"尺寸ID为空");
        }
        LambdaUpdateWrapper<ProductSize> update = Wrappers.lambdaUpdate();
        update.set(ProductSize::getIsDelete,ProductConstants.PRODUCT_DELETE).eq(ProductSize::getSizeId,sizeId);
        if (!update(update)) {
            throw new BusinessException(500,"尺寸删除失败");
        }
    }

    @Override
    public void batchDeleteProductSize(String idList) {
        List<Long> list = Arrays.stream(idList.split(",")).map(Long::valueOf).toList();
        LambdaUpdateWrapper<ProductSize> update = Wrappers.lambdaUpdate();
        update.set(ProductSize::getIsDelete,ProductConstants.PRODUCT_DELETE).in(ProductSize::getSizeId,list);
        if (!update(update)) {
            throw new BusinessException(500,"尺码删除失败");
        }
    }

    @Override
    public Page<ProductSizeVO> pageQueryProductSize(PageQueryProductSizePO po) {
        //设置查询参数
        Page<ProductSize> page = new Page<>();
        page.setCurrent(po.getCurrentPage());
        page.setSize(po.getPageSize());

        LambdaQueryWrapper<ProductSize> query = Wrappers.lambdaQuery();
        query.eq(ProductSize::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        Page<ProductSize> sizePage = page(page,query);

        //构建返回实体对象
        Page<ProductSizeVO> voPage = new Page<>();
        BeanUtil.copyProperties(sizePage,voPage,"records");
        List<ProductSizeVO> list = sizePage.getRecords().stream().map((entity) -> BeanUtil.toBean(entity, ProductSizeVO.class)).toList();
        voPage.setRecords(list);

        return voPage;
    }

    @Override
    public ProductSizeVO getSizeById(Long id) {
        ProductSize entity = getById(id);
        return BeanUtil.toBean(entity, ProductSizeVO.class);
    }

    @Override
    public List<ProductSizeVO> getAll() {
        LambdaQueryWrapper<ProductSize> query = Wrappers.lambdaQuery();
        query.eq(ProductSize::getIsDelete,ProductConstants.PRODUCT_NOT_DELETE);
        List<ProductSize> list = list(query);
        return list.stream().map((item) -> BeanUtil.toBean(item, ProductSizeVO.class)).toList();
    }
}




