package com.pandaer.project.server.modules.report.service;

import com.pandaer.project.server.modules.report.dto.MarketItemDTO;
import com.pandaer.project.server.modules.report.dto.PerformanceDTO;
import com.pandaer.project.server.modules.report.dto.TrafficItemDTO;

import java.util.List;

public interface ReportService {


    /**
     * 获取营销报表数据 饼图数据
     * @return
     */
    List<MarketItemDTO> getMarketReportData();

    /**
     * 业绩报表数据 饼图数据
     */
    List<PerformanceDTO> getPerformanceData();


    /**
     * 流量报表 柱状图数据
     */
    List<TrafficItemDTO> getTrafficData();

}
