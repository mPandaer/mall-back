package com.pandaer.project.server.modules.spread.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.project.server.modules.spread.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.spread.po.AddFriendLinkPO;
import com.pandaer.project.server.modules.spread.po.PageQueryFriendLinkPO;
import com.pandaer.project.server.modules.spread.po.UpdateFriendLinkPO;
import com.pandaer.project.server.modules.spread.vo.FriendLinkVO;

/**
 *
*/
public interface FriendLinkService extends IService<FriendLink> {

    /**
     * 增加链接
     * @param po
     */
    void addFriendLink(AddFriendLinkPO po);

    /**
     * 更新链接
     * @param po
     */
    void updateFriendLink(UpdateFriendLinkPO po);

    /**
     * 批量删除链接
     * @param idList
     */
    void deleteFriendLink(String idList);

    /**
     * 分页查询链接
     * @param po
     * @return
     */
    IPage<FriendLinkVO> pageQueryFriendLink(PageQueryFriendLinkPO po);

}
