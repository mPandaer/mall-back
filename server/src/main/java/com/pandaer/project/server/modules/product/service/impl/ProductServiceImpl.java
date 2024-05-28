package com.pandaer.project.server.modules.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.base.exception.util.ExceptionUtil;
import com.pandaer.project.server.modules.product.constants.ProductConstants;
import com.pandaer.project.server.modules.product.entity.Product;
import com.pandaer.project.server.modules.product.po.AddProductPO;
import com.pandaer.project.server.modules.product.po.PageQueryProductPO;
import com.pandaer.project.server.modules.product.po.UpdateProductPO;
import com.pandaer.project.server.modules.product.service.*;
import com.pandaer.project.server.modules.product.mapper.ProductMapper;
import com.pandaer.project.server.modules.product.vo.*;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductBrandService brandService;

    @Resource
    private ProductColorService colorService;

    @Resource
    private ProductSizeService sizeService;

    @Resource
    private ProductTypeService typeService;


    /**
     * 添加商品
     * 1. 校验各类ID是否存在
     * 2. 装配实体对象
     * 3. 执行保存操作
     *
     * @param po
     */
    @Override
    public void addProduct(AddProductPO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500, "添加商品参数为空");
        }

        boolean isAllExist = validAllId(po.getBrandId(), po.getColorId(), po.getSizeId(), po.getTypeId());

        if (!isAllExist) {
            throw new BusinessException(500, "添加商品参数异常");
        }

        //装配对象
        Product entity = BeanUtil.toBean(po, Product.class);
        entity.setProductId(IdUtil.getSnowflakeNextId());
        entity.setCreateUser(0L);
        entity.setUpdateUser(0L);

        //执行保存操作
        if (!save(entity)) {
            throw new BusinessException(500, "添加商品失败");
        }

    }

    /**
     * 判断各类ID是否存在
     *
     * @param
     * @return
     */
    private boolean validAllId(Long brandId, Long colorId, Long sizeId, Long typeId) {
        if (ObjectUtil.isNotNull(brandId) && ObjectUtil.isNull(brandService.getBrandById(brandId))) {
            return false;
        }
        if (ObjectUtil.isNotNull(colorId) && ObjectUtil.isNull(colorService.getColorById(colorId))) {
            return false;
        }

        if (ObjectUtil.isNotNull(sizeId) && ObjectUtil.isNull(sizeService.getSizeById(sizeId))) {
            return false;
        }
        if (ObjectUtil.isNotNull(typeId) && ObjectUtil.isNull(typeService.getTypeById(typeId))) {
            return false;
        }
        return true;
    }


    /**
     * 更新商品
     * 1. 判断参数实体是否为空
     * 2. 校验各类ID是否正确
     * 3. 根据商品ID更新对象
     *
     * @param po
     */
    @Override
    public void updateProduct(UpdateProductPO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500, "修改商品参数为空");
        }
        if (!validAllId(po.getBrandId(), po.getColorId(), po.getSizeId(), po.getTypeId())) {
            throw new BusinessException(500, "修改商品参数异常");
        }
        Product entity = BeanUtil.toBean(po, Product.class);
        if (!updateById(entity)) {
            throw new BusinessException(500, "商品更新失败");
        }
    }

    /**
     * 批量删除用户
     * 修改逻辑删除字段
     *
     * @param idList
     */
    @Override
    public void batchDeleteProduct(String idList) {
        List<Long> list = Arrays.stream(idList.split(",")).map(Long::valueOf).toList();
        LambdaUpdateWrapper<Product> update = Wrappers.<Product>lambdaUpdate().set(Product::getIsDelete, ProductConstants.PRODUCT_DELETE).in(Product::getProductId, list);
        if (!update(update)) {
            throw new BusinessException(500, "商品删除失败");
        }
    }

    /**
     * 分页查询商品信息并封装成ProductVO对象
     *
     * @param po
     * @return
     */
    @Override
    public Page<ProductVO> pageQueryProduct(PageQueryProductPO po) {
        //分页配置
        Page<Product> productPage = new Page<>();
        productPage.setSize(po.getPageSize());
        productPage.setCurrent(po.getCurrentPage());

        //查询条件配置
        LambdaQueryWrapper<Product> query = Wrappers.<Product>lambdaQuery().eq(Product::getIsDelete, ProductConstants.PRODUCT_NOT_DELETE);
        if (ObjectUtil.isNotEmpty(po.getName())) {
            query.likeRight(Product::getName,po.getName());
        }
        if (ObjectUtil.isNotNull(po.getBrandId())) {
            query.eq(Product::getBrandId,po.getBrandId());
        }

        if (ObjectUtil.isNotNull(po.getTypeId())) {
            query.eq(Product::getTypeId,po.getTypeId());
        }

        if (ObjectUtil.isNotNull(po.getColorId())) {
            query.eq(Product::getColorId,po.getColorId());
        }
        if (ObjectUtil.isNotNull(po.getSizeId())) {
            query.eq(Product::getSizeId,po.getSizeId());
        }
        Page<Product> page = page(productPage, query);

        //封装返回对象
        Page<ProductVO> voPage = new Page<>();
        BeanUtil.copyProperties(page, voPage, "records");
        List<ProductVO> list = mappingProductInVO(page.getRecords());
        voPage.setRecords(list);
        return voPage;
    }

    @Override
    public ProductVO getProductById(Long productId) {
        Product entity = getById(productId);
        return mappingProductInVO(Collections.singletonList(entity)).get(0);
    }

    @Override
    public Map<Long, ProductVO> getProductVOMap(Map<Long, Long> detailToProductMap) {
        if (detailToProductMap.isEmpty()) {
            return null;
        }
        List<Product> productList = listByIds(detailToProductMap.values());
        List<ProductVO> voList = mappingProductInVO(productList);
        Map<Long, ProductVO> productVOMap = voList.stream().collect(Collectors.toMap(ProductVO::getProductId, Function.identity()));
        HashMap<Long, ProductVO> resultMap = new HashMap<>();
        detailToProductMap.forEach((detailId,productId) -> resultMap.put(detailId,productVOMap.get(productId)));
        return resultMap;
    }

    /**
     *
     * todo 未实现
     * 根据ID查询产品,
     * 通过产品的类型,品牌查询同类产品
     * 拼装对象
     * @param productId
     * @return
     */
    @Override
    public ProductDetailVO getProductDetailByProductId(String productId) {
        if (ObjectUtil.isEmpty(productId)) {
            ExceptionUtil.business(500,"商品ID不能为空");
        }
        Product entity = getById(Long.valueOf(productId));
        if (ObjectUtil.isNull(entity)) {
            ExceptionUtil.business(500,"商品不存在");
        }
        LambdaQueryWrapper<Product> query = Wrappers.lambdaQuery();
        query.eq(Product::getTypeId,entity.getTypeId()).eq(Product::getBrandId,entity.getBrandId());

        List<Product> list = list(query);
        List<ProductVO> voList = mappingProductInVO(list);

        List<ProductColorVO> colorList = voList.stream().map(ProductVO::getColor).toList();
        List<ProductSizeVO> sizeList = voList.stream().map(ProductVO::getSize).toList();

        ProductDetailVO productDetailVO = new ProductDetailVO();

        return null;

    }

    /**
     * 根据商品类型返回商品实体
     * @param typeId
     * @return
     */
    @Override
    public List<Product> getProductListByType(Long typeId) {
        LambdaQueryWrapper<Product> query = Wrappers.lambdaQuery();
        query.eq(Product::getIsDelete,0).eq(Product::getTypeId,typeId);
        return list(query);
    }

    /**
     * 将Product 转换为ProductVO 主要就是将各类ID转换为各类对应的Name
     *
     * @param records
     * @return
     */
    private List<ProductVO> mappingProductInVO(List<Product> records) {
        // 获取全部的品牌,类型,颜色,尺码数据 todo 是否可以利用泛型优化
        Map<Long, ProductBrandVO> brandMap = brandService.getAll().stream().collect(Collectors.toMap(ProductBrandVO::getBrandId, item -> item));
        Map<Long, ProductTypeVO> typeMap = typeService.getAll().stream().collect(Collectors.toMap(ProductTypeVO::getTypeId, item -> item));
        Map<Long, ProductColorVO> colorMap = colorService.getAll().stream().collect(Collectors.toMap(ProductColorVO::getColorId, item -> item));
        Map<Long, ProductSizeVO> sizeMap = sizeService.getAll().stream().collect(Collectors.toMap(ProductSizeVO::getSizeId, item -> item));
        return records.stream().map((item) -> {
            ProductVO productVO = new ProductVO();
            BeanUtil.copyProperties(item,productVO);
            productVO.setBrand(brandMap.get(item.getBrandId()));
            productVO.setType(typeMap.get(item.getTypeId()));
            productVO.setColor(colorMap.get(item.getColorId()));
            productVO.setSize(sizeMap.get(item.getSizeId()));
            return productVO;
        }).toList();
    }
}




