package com.pandaer.project.server.modules.report.service.impl;

import cn.hutool.json.JSONUtil;
import com.pandaer.project.server.modules.report.dto.MarketItemDTO;
import com.pandaer.project.server.modules.report.dto.PerformanceDTO;
import com.pandaer.project.server.modules.report.dto.TrafficItemDTO;
import com.pandaer.project.server.modules.report.service.ReportService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReportServiceTest {

    @Resource
    private ReportService reportService;

    @Test
    public void testMarketReportData() {
        List<MarketItemDTO> marketReportData = reportService.getMarketReportData();
        System.out.println(JSONUtil.toJsonPrettyStr(marketReportData));
    }

    @Test
    public void testPerformanceData() {
        List<PerformanceDTO> performanceData = reportService.getPerformanceData();
        System.out.println(JSONUtil.toJsonPrettyStr(performanceData));
    }

    @Test
    public void testTrafficItemDTO() {
        List<TrafficItemDTO> trafficItemDTO = reportService.getTrafficData();
        System.out.println(JSONUtil.toJsonPrettyStr(trafficItemDTO));
    }


}