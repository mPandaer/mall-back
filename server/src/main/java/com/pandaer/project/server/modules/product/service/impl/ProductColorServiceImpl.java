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
import com.pandaer.project.server.modules.product.entity.ProductColor;
import com.pandaer.project.server.modules.product.po.AddProductColorPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductColorPO;
import com.pandaer.project.server.modules.product.po.UpdateProductColorPO;
import com.pandaer.project.server.modules.product.service.ProductColorService;
import com.pandaer.project.server.modules.product.mapper.ProductColorMapper;
import com.pandaer.project.server.modules.product.vo.ProductBrandVO;
import com.pandaer.project.server.modules.product.vo.ProductColorVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class ProductColorServiceImpl extends ServiceImpl<ProductColorMapper, ProductColor>
    implements ProductColorService{


    /**
     * 1. 简单校验一下参数对象
     * 2. 装配实体对象
     * 3. 保存实体对象
     * @param po 参数实体
     */
    @Override
    public void addProductColor(AddProductColorPO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500,"添加颜色参数为空");
        }

        //构建实体对象
        ProductColor productColor = new ProductColor();
        productColor.setColorId(IdUtil.getSnowflakeNextId());
        productColor.setColorName(po.getColorName());
        productColor.setCreateUser(0L);
        productColor.setUpdateUser(0L);

        //保存实体对象
        if(!save(productColor)) {
            throw new BusinessException(500,"添加颜色失败");
        }

    }

    /**
     * 1. 校验颜色是否存在
     * 2. 将对应属性复制一遍
     * 3. 执行更新操作
     * @param po
     */
    @Override
    public void updateProductColor(UpdateProductColorPO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500,"修改颜色参数为空");
        }
        ProductColor entity = getProductColorById(po.getColorId());
        BeanUtil.copyProperties(po,entity);
        if (ObjectUtil.isNull(entity)) {
            throw new BusinessException(500,"颜色不存在");
        }
        if (!updateById(entity)) {
            throw new BusinessException(500,"颜色更新失败");
        }

    }


    /**
     * 根据颜色ID获取实体信息 一定保证不为空 为空会抛出异常
     * @param colorId
     * @return
     */
    private ProductColor getProductColorById(Long colorId) {
        LambdaQueryWrapper<ProductColor> query = Wrappers.lambdaQuery();
        query.eq(ProductColor::getColorId, colorId)
                .eq(ProductColor::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        ProductColor entity = getOne(query);
        return entity;
    }


    /**
     * 逻辑删除
     * @param colorId
     */
    @Override
    public void deleteProductColor(Long colorId) {
        if (ObjectUtil.isNull(colorId)) {
            throw new BusinessException(500,"颜色ID为空");
        }
        LambdaUpdateWrapper<ProductColor> update = Wrappers.lambdaUpdate();
        update.set(ProductColor::getIsDelete,ProductConstants.PRODUCT_DELETE).eq(ProductColor::getColorId,colorId);
        if (!update(update)) {
            throw new BusinessException(500,"颜色删除失败");
        }
    }

    @Override
    public void batchDeleteProductColor(String idList) {
        List<Long> list = Arrays.stream(idList.split(",")).map(Long::valueOf).toList();
        LambdaUpdateWrapper<ProductColor> update = Wrappers.lambdaUpdate();
        update.set(ProductColor::getIsDelete,ProductConstants.PRODUCT_DELETE).in(ProductColor::getColorId,list);
        if (!update(update)) {
            throw new BusinessException(500,"颜色删除失败");
        }
    }

    @Override
    public Page<ProductColorVO> pageQueryProductColor(PageQueryProductColorPO po) {
        //设置查询参数
        Page<ProductColor> page = new Page<>();
        page.setCurrent(po.getCurrentPage());
        page.setSize(po.getPageSize());

        LambdaQueryWrapper<ProductColor> query = Wrappers.lambdaQuery();
        query.eq(ProductColor::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        Page<ProductColor> colorPage = page(page,query);

        //构建返回实体对象
        Page<ProductColorVO> voPage = new Page<>();
        BeanUtil.copyProperties(colorPage,voPage,"records");
        List<ProductColorVO> list = colorPage.getRecords().stream().map((entity) -> BeanUtil.toBean(entity, ProductColorVO.class)).toList();
        voPage.setRecords(list);

        return voPage;
    }

    @Override
    public ProductColorVO getColorById(Long id) {
        ProductColor entity = getById(id);
        return BeanUtil.toBean(entity, ProductColorVO.class);
    }

    @Override
    public List<ProductColorVO> getAll() {
        LambdaQueryWrapper<ProductColor> query = Wrappers.lambdaQuery();
        query.eq(ProductColor::getIsDelete,ProductConstants.PRODUCT_NOT_DELETE);
        List<ProductColor> list = list(query);
        return list.stream().map((item) -> BeanUtil.toBean(item,ProductColorVO.class)).toList();
    }
}




