package com.pandaer.project.server.modules.spread.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.project.server.modules.spread.po.*;
import com.pandaer.project.server.modules.spread.service.FriendLinkService;
import com.pandaer.project.server.modules.spread.vo.FriendLinkVO;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/admin/link")
@RestController
@Tag(name = "友情链接管理模块",description = "对友情链接信息增删改查")
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;

    @PostMapping("add")
    @Operation(description = "增加友情链接信息")
    public Resp<Object> addLink(@RequestBody @Validated AddFriendLinkPO po) {
        friendLinkService.addFriendLink(po);
        return Resp.success();
    }

    @PostMapping("update")
    @Operation(description = "更新友情链接信息")
    public Resp<Object> updateLink(@RequestBody @Validated UpdateFriendLinkPO po) {
        friendLinkService.updateFriendLink(po);
        return Resp.success();
    }

    @GetMapping("delete")
    @Operation(description = "删除友情链接信息")
    public Resp<Object> batchDeleteLink(@NotEmpty(message = "删除广告的ID不能为空") String idList) {
        friendLinkService.deleteFriendLink(idList);
        return Resp.success();
    }

    @GetMapping("page/query")
    @Operation(description = "分页查询友情链接信息")
    public Resp<IPage<FriendLinkVO>> pageQueryLink(@Validated PageQueryFriendLinkPO po) {
        IPage<FriendLinkVO> voPage = friendLinkService.pageQueryFriendLink(po);
        return Resp.success(voPage);
    }
}
