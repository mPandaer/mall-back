package com.pandaer.project.server.modules.product.service.impl;

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
import com.pandaer.project.server.modules.product.entity.ProductColor;
import com.pandaer.project.server.modules.product.entity.ProductType;
import com.pandaer.project.server.modules.product.entity.ProductType;
import com.pandaer.project.server.modules.product.po.AddProductTypePO;
import com.pandaer.project.server.modules.product.po.PageQueryProductTypePO;
import com.pandaer.project.server.modules.product.po.UpdateProductTypePO;
import com.pandaer.project.server.modules.product.service.ProductTypeService;
import com.pandaer.project.server.modules.product.mapper.ProductTypeMapper;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import com.pandaer.project.server.modules.product.vo.ProductTypeVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType>
    implements ProductTypeService{


    /**
     * 1. 简单校验一下参数对象
     * 2. 装配实体对象
     * 3. 保存实体对象
     * @param po 参数实体
     */
    @Override
    public void addProductType(AddProductTypePO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500,"添加类型参数为空");
        }

        //构建实体对象
        ProductType productType = new ProductType();
        productType.setTypeId(IdUtil.getSnowflakeNextId());
        productType.setTypeName(po.getTypeName());
        productType.setCreateUser(0L);
        productType.setUpdateUser(0L);

        //保存实体对象
        if(!save(productType)) {
            throw new BusinessException(500,"添加类型失败");
        }

    }

    /**
     * 1. 校验类型是否存在
     * 2. 将对应属性复制一遍
     * 3. 执行更新操作
     * @param po
     */
    @Override
    public void updateProductType(UpdateProductTypePO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500,"修改类型参数为空");
        }
        ProductType entity = getProductTypeById(po.getTypeId());
        if (ObjectUtil.isNull(entity)) {
            throw new BusinessException(500,"类型不存在");
        }
        BeanUtil.copyProperties(po,entity);
        if (!updateById(entity)) {
            throw new BusinessException(500,"类型更新失败");
        }

    }


    /**
     * 根据类型ID获取实体信息 一定保证不为空 为空会抛出异常
     * @param typeId
     * @return
     */
    private ProductType getProductTypeById(Long typeId) {
        LambdaQueryWrapper<ProductType> query = Wrappers.lambdaQuery();
        query.eq(ProductType::getTypeId, typeId)
                .eq(ProductType::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        return getOne(query);
    }


    /**
     * 逻辑删除
     * @param typeId
     */
    @Override
    public void deleteProductType(Long typeId) {
        if (ObjectUtil.isNull(typeId)) {
            throw new BusinessException(500,"类型ID为空");
        }
        LambdaUpdateWrapper<ProductType> update = Wrappers.lambdaUpdate();
        update.set(ProductType::getIsDelete,ProductConstants.PRODUCT_DELETE).eq(ProductType::getTypeId,typeId);
        if (!update(update)) {
            throw new BusinessException(500,"类型删除失败");
        }
    }

    @Override
    public void batchDeleteProductType(String idList) {
        List<Long> list = Arrays.stream(idList.split(",")).map(Long::valueOf).toList();
        LambdaUpdateWrapper<ProductType> update = Wrappers.lambdaUpdate();
        update.set(ProductType::getIsDelete,ProductConstants.PRODUCT_DELETE).in(ProductType::getTypeId,list);
        if (!update(update)) {
            throw new BusinessException(500,"类型删除失败");
        }
    }

    @Override
    public Page<ProductTypeVO> pageQueryProductType(PageQueryProductTypePO po) {
        //设置查询参数
        Page<ProductType> page = new Page<>();
        page.setCurrent(po.getCurrentPage());
        page.setSize(po.getPageSize());

        LambdaQueryWrapper<ProductType> query = Wrappers.lambdaQuery();
        query.eq(ProductType::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        Page<ProductType> typePage = page(page,query);

        //构建返回实体对象
        Page<ProductTypeVO> voPage = new Page<>();
        BeanUtil.copyProperties(typePage,voPage,"records");
        List<ProductTypeVO> list = typePage.getRecords().stream().map((entity) -> BeanUtil.toBean(entity, ProductTypeVO.class)).toList();
        voPage.setRecords(list);

        return voPage;
    }

    @Override
    public ProductTypeVO getTypeById(Long id) {
        ProductType entity = getById(id);
        return BeanUtil.toBean(entity, ProductTypeVO.class);
    }

    @Override
    public List<ProductTypeVO> getAll() {
        LambdaQueryWrapper<ProductType> query = Wrappers.lambdaQuery();
        query.eq(ProductType::getIsDelete,ProductConstants.PRODUCT_NOT_DELETE);
        List<ProductType> list = list(query);
        return list.stream().map((item) -> BeanUtil.toBean(item,ProductTypeVO.class)).toList();
    }

}









