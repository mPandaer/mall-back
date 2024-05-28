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
import com.pandaer.project.server.modules.spread.entity.FriendLink;
import com.pandaer.project.server.modules.spread.po.AddFriendLinkPO;
import com.pandaer.project.server.modules.spread.po.PageQueryFriendLinkPO;
import com.pandaer.project.server.modules.spread.po.UpdateFriendLinkPO;
import com.pandaer.project.server.modules.spread.service.FriendLinkService;
import com.pandaer.project.server.modules.spread.mapper.FriendLinkMapper;
import com.pandaer.project.server.modules.spread.vo.FriendLinkVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
* @author pandaer
* @description 针对表【friend_links(友情链接)】的数据库操作Service实现
* @createDate 2024-05-21 16:22:32
*/
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink>
    implements FriendLinkService{

    /**
     * 创建友情链接
     * 1. 简单校验一下参数
     * 2. 设置ID
     * 3. 保存
     * @param po
     */
    @Override
    public void addFriendLink(AddFriendLinkPO po) {
        if (ObjectUtil.isNull(po)) {
            ExceptionUtil.business(500,"添加友情链接参数为空");
        }
        FriendLink entity = BeanUtil.toBean(po, FriendLink.class);
        entity.setLinkId(IdUtil.genNextId());
        if (!save(entity)) {
            ExceptionUtil.business(500,"添加友情链接失败");
        }
    }

    /**
     * 更新友情链接
     * 1. 简单校验一下参数
     * 2. 看看链接是否存在
     * 3. 根据ID更新
     * @param po
     */
    @Override
    public void updateFriendLink(UpdateFriendLinkPO po) {
        if (ObjectUtil.isNull(po)) {
            ExceptionUtil.business(500,"更新友情链接参数为空");
        }
        FriendLink dbLink = getById(po.getLinkId());
        if (ObjectUtil.isNull(dbLink)) {
            ExceptionUtil.business(500,"友情链接不存在");
        }
        BeanUtil.copyProperties(po,dbLink);
        if (!updateById(dbLink)) {
            ExceptionUtil.business(500,"友情链接更新失败");
        }

    }

    /**
     * 逻辑删除
     * @param idList
     */
    @Override
    public void deleteFriendLink(String idList) {
        List<Long> list = Arrays.stream(idList.split(",")).map(Long::valueOf).toList();
        LambdaUpdateWrapper<FriendLink> update = Wrappers.lambdaUpdate();
        update.set(FriendLink::getIsDelete,SpreadConstants.DELETE).in(FriendLink::getLinkId,list);
        if (!update(update)) {
            ExceptionUtil.business(500,"友情链接删除失败");
        }
    }

    @Override
    public IPage<FriendLinkVO> pageQueryFriendLink(PageQueryFriendLinkPO po) {
        //设置分页参数
        Page<FriendLink> linkPage = new Page<>();
        linkPage.setSize(po.getPageSize());
        linkPage.setCurrent(po.getCurrentPage());
        //配置分页条件
        LambdaQueryWrapper<FriendLink> query = Wrappers.lambdaQuery();
        query.eq(FriendLink::getIsDelete, SpreadConstants.NOT_DELETE);
        Page<FriendLink> page = page(linkPage, query);

        //封装返回对象
        Page<FriendLinkVO> voPage = new Page<>();
        BeanUtil.copyProperties(page,voPage,"records");
        List<FriendLinkVO> voList = page.getRecords().stream().map(item -> BeanUtil.toBean(item, FriendLinkVO.class)).toList();
        voPage.setRecords(voList);
        return voPage;

    }
}




