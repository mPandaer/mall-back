package com.pandaer.project.server.modules.spread.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.base.exception.util.ExceptionUtil;
import com.pandaer.project.base.utils.IdUtil;
import com.pandaer.project.server.modules.spread.constants.SpreadConstants;
import com.pandaer.project.server.modules.spread.entity.Advertisement;
import com.pandaer.project.server.modules.spread.po.AddADPO;
import com.pandaer.project.server.modules.spread.po.PageQueryADPO;
import com.pandaer.project.server.modules.spread.po.UpdateADPO;
import com.pandaer.project.server.modules.spread.service.AdvertisementService;
import com.pandaer.project.server.modules.spread.mapper.AdvertisementMapper;
import com.pandaer.project.server.modules.spread.vo.ADVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
* @author pandaer
* @description 针对表【advertisements】的数据库操作Service实现
* @createDate 2024-05-21 16:22:32
*/
@Service
public class AdvertisementServiceImpl extends ServiceImpl<AdvertisementMapper, Advertisement>
    implements AdvertisementService{

    /**
     * 添加广告
     * 1. 校验参数
     * 2. 设置ID
     * 3. 保存
     * @param po
     */
    @Override
    public void addAD(AddADPO po) {
        if (ObjectUtil.isNull(po)) {
            ExceptionUtil.business(500,"添加广告参数为空");
        }
        Advertisement entity = BeanUtil.toBean(po, Advertisement.class);
        entity.setAdId(IdUtil.genNextId());
        if (!save(entity)) {
            ExceptionUtil.business(500,"广告添加失败");
        }
    }

    /**
     * 更新广告信息
     * 1. 校验参数
     * 2. 检查广告是否存在
     * 3. 更新信息
     * @param po
     */
    @Override
    public void updateAD(UpdateADPO po) {
        if (ObjectUtil.isNull(po)) {
            ExceptionUtil.business(500,"更新广告参数为空");
        }
        Advertisement entity = getById(po.getAdId());
        if (ObjectUtil.isNull(entity)) {
            ExceptionUtil.business(500,"广告不存在");
        }
        BeanUtil.copyProperties(po,entity);
        if (!updateById(entity)) {
            ExceptionUtil.business(500,"更新广告失败");
        }
    }

    /**
     * 逻辑删除
     * 1. 校验ID是否合法
     * 2. 更新逻辑删除字段
     * @param idList
     */
    @Override
    public void deleteAD(String idList) {
        List<Long> list = Arrays.stream(idList.split(",")).map(Long::valueOf).toList();
        LambdaQueryWrapper<Advertisement> query = Wrappers.lambdaQuery();
        query.in(Advertisement::getAdId,list);
        long dbCount = count(query);
        if (dbCount != list.size()) {
            ExceptionUtil.business(500,"存在广告ID不合法");
        }

        LambdaUpdateWrapper<Advertisement> update = Wrappers.lambdaUpdate();
        update.set(Advertisement::getIsDelete, SpreadConstants.DELETE).in(Advertisement::getAdId,list);
        if (!update(update)) {
            ExceptionUtil.business(500,"删除广告失败");
        }
    }

    @Override
    public IPage<ADVO> pageQueryAD(PageQueryADPO po) {
        Page<Advertisement> page = new Page<>();
        page.setCurrent(po.getCurrentPage());
        page.setSize(po.getPageSize());

        LambdaQueryWrapper<Advertisement> query = Wrappers.lambdaQuery();
        query.eq(Advertisement::getIsDelete,SpreadConstants.NOT_DELETE);
        page = page(page,query);

        Page<ADVO> advoPage = new Page<>();
        BeanUtil.copyProperties(page,advoPage,"records");
        List<ADVO> list = page.getRecords().stream().map(item -> BeanUtil.toBean(item, ADVO.class)).toList();
        advoPage.setRecords(list);
        return advoPage;
    }
}




