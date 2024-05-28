package com.pandaer.project.server.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.server.modules.order.entity.RefundRequest;
import com.pandaer.project.server.modules.order.mapper.RefundRequestMapper;
import com.pandaer.project.server.modules.order.service.RefundRequestService;
import org.springframework.stereotype.Service;

/**
* @author pandaer
* @description 针对表【refund_requests】的数据库操作Service实现
* @createDate 2024-05-18 19:54:41
*/
@Service
public class RefundRequestServiceImpl extends ServiceImpl<RefundRequestMapper, RefundRequest>
    implements RefundRequestService {

}




