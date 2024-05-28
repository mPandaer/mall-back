package com.pandaer.project.server.modules.report.controller;

import com.pandaer.project.server.modules.report.dto.MarketItemDTO;
import com.pandaer.project.server.modules.report.dto.PerformanceDTO;
import com.pandaer.project.server.modules.report.dto.TrafficItemDTO;
import com.pandaer.project.server.modules.report.service.ReportService;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin/report")
@RestController
@Tag(name = "报表服务")
public class ReportController {

    @Resource
    private ReportService reportService;


    @Operation(description = "获取营销报表数据")
    @GetMapping("/market")
    public Resp<List<MarketItemDTO>> getMarketReportData() {
        List<MarketItemDTO> marketReportData = reportService.getMarketReportData();
        return Resp.success(marketReportData);
    }

    @Operation(description = "获取业绩报表数据")
    @GetMapping("/performance")
    public Resp<List<PerformanceDTO>> getPerformanceData() {
        List<PerformanceDTO> data = reportService.getPerformanceData();
        return Resp.success(data);
    }

    @Operation(description = "获取流量报表数据")
    @GetMapping("/traffic")
    public Resp<List<TrafficItemDTO>> getTrafficData() {
        List<TrafficItemDTO> trafficItemDTO = reportService.getTrafficData();
        return Resp.success(trafficItemDTO);
    }
}
