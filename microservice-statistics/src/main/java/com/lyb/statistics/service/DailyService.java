package com.lyb.statistics.service;

import com.lyb.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.statistics.query.ChartQuery;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author liuyoubin
 * @since 2020-01-16
 */
public interface DailyService extends IService<Daily> {

    /**
     * 生成指定日期的统计记录
     * @param day 指定日期
     */
    void createStatisticsInfoByDay(String day);

    /**
     * 查询指定条件的统计信息
     * @param chartQuery 查询对象
     * @return 统计信息集合
     */
    Map<String, Object> queryChartData(ChartQuery chartQuery);
}
