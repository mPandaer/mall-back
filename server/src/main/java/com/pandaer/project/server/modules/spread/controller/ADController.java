package com.pandaer.project.server.modules.spread.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.project.server.modules.spread.po.AddADPO;
import com.pandaer.project.server.modules.spread.po.PageQueryADPO;
import com.pandaer.project.server.modules.spread.po.UpdateADPO;
import com.pandaer.project.server.modules.spread.service.AdvertisementService;
import com.pandaer.project.server.modules.spread.vo.ADVO;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/admin/ad")
@RestController
@Tag(name = "广告管理模块",description = "对广告信息增删改查")
public class ADController {

    @Resource
    private AdvertisementService advertisementService;

    @PostMapping("/add")
    @Operation(description = "增加广告信息")
    public Resp<Object> addAD(@RequestBody @Validated AddADPO po) {
        advertisementService.addAD(po);
        return Resp.success();
    }

    @PostMapping("update")
    @Operation(description = "更新广告信息")
    public Resp<Object> updateAD(@RequestBody @Validated UpdateADPO po) {
        advertisementService.updateAD(po);
        return Resp.success();
    }

    @GetMapping("delete")
    @Operation(description = "删除广告信息")
    public Resp<Object> batchDeleteAD(@NotEmpty(message = "删除广告的ID不能为空") String idList) {
        advertisementService.deleteAD(idList);
        return Resp.success();
    }

    @GetMapping("page/query")
    @Operation(description = "分页查询广告信息")
    public Resp<IPage<ADVO>> pageQueryAD( @Validated PageQueryADPO po) {
        IPage<ADVO> voPage = advertisementService.pageQueryAD(po);
        return Resp.success(voPage);
    }


}
