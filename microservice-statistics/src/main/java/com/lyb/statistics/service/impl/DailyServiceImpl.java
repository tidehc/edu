package com.lyb.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.statistics.client.UcenterClient;
import com.lyb.statistics.entity.Daily;
import com.lyb.statistics.mapper.DailyMapper;
import com.lyb.statistics.query.ChartQuery;
import com.lyb.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author liuyoubin
 * @since 2020-01-16
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 创建指定日期当天的统计记录，添加到数据库
     * 统计记录包括：
     *  当天的注册人数（registerNum）
     *  当天的登录人数（loginNum）
     *  当天的视频播放次数（videoViewNum）
     *  当天的新增课程数（courseNum）
     *
     * @param day 指定日期
     */
    @Override
    public void createStatisticsInfoByDay(String day) {

        //删除旧统计记录,保证每天只有一条统计记录
        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper.eq("date_calculated",day);
        baseMapper.delete(dailyQueryWrapper);

        //获取统计信息
        Integer registerNum = (Integer)ucenterClient.registerMemberCountByDay(day).getData().get("count");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum  = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象 TODO
        Daily daily = new Daily()
                .setRegisterNum(registerNum)
                .setLoginNum(loginNum)
                .setVideoViewNum(videoViewNum)
                .setCourseNum(courseNum)
                .setDateCalculated(day);;

        //保存统计信息 TODO
        baseMapper.insert(daily);


    }

    /**
     * 查询指定条件的统计信息
     *
     * @param chartQuery 查询对象
     * @return 统计信息集合
     */
    @Override
    public Map<String, Object> queryChartData(ChartQuery chartQuery) {

        //获取查询对象参数 : 查询数据类型、查询起始时间、查询终止时间
        String type = chartQuery.getType();
        String begin = chartQuery.getBegin();
        String end = chartQuery.getEnd();

        //构造查询包装对象
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(type,"date_calculated");//指定要查找的统计信息的类型列以及统计日期
        queryWrapper.between("date_calculated", begin, end);

        List<Daily> dailyList = baseMapper.selectList(queryWrapper);

        //要返回的Map集合
        HashMap<String, Object> map = new HashMap<>();

        ArrayList<Integer> dataList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        map.put("dataList",dataList);
        map.put("dateList", dateList);

        //填充数据到List中
        for (Daily daily : dailyList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        return map;
    }
}
