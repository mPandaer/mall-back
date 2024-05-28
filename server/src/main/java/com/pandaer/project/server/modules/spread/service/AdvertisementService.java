package com.pandaer.project.server.modules.spread.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.spread.entity.Advertisement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.spread.po.AddADPO;
import com.pandaer.project.server.modules.spread.po.PageQueryADPO;
import com.pandaer.project.server.modules.spread.po.UpdateADPO;
import com.pandaer.project.server.modules.spread.vo.ADVO;

/**
* @author pandaer
* @description 针对表【advertisements】的数据库操作Service
* @createDate 2024-05-21 16:22:32
*/
public interface AdvertisementService extends IService<Advertisement> {

    void addAD(AddADPO po);

    void updateAD(UpdateADPO po);

    void deleteAD(String idList);

    IPage<ADVO> pageQueryAD(PageQueryADPO po);
}
