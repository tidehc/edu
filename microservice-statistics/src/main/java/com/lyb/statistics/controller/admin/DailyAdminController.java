package com.lyb.statistics.controller.admin;

import com.lyb.common.vo.R;
import com.lyb.statistics.query.ChartQuery;
import com.lyb.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author liuyoubin
 * @since 2020/1/16 - 22:57
 */
@Api(description = "日常信息统计管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/statistics/daily")
public class DailyAdminController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation(value = "生成指定日期的统计记录")
    @PostMapping("/{day}")
    public R createStatisticsInfoByDay(@PathVariable(value = "day") String day){
        dailyService.createStatisticsInfoByDay(day);
        return R.ok();
    }

    @GetMapping("/chart")
    @ApiOperation(value = "查询指定条件的统计信息")
    public R queryChartData(
            @ApiParam(value = "chartQuery",name = "查询对象",required = true)
            @Valid ChartQuery chartQuery){
        Map<String,Object> map = dailyService.queryChartData(chartQuery);
        return R.ok().data(map);
    }

}
