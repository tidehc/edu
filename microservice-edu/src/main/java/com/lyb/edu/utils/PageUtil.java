package com.lyb.edu.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyoubin
 * @since 2020/2/15 - 22:00
 * 分页处理工具类
 */
public class PageUtil {

    /**
     * 通过分页查询对象获取封装的分页详情对象
     * @param pageParam 分页查询对象
     * @return  分页详情对象
     */
    public static Map<String,Object> getPageMapOnPageParam(Page pageParam){

        //封装查询数据
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("items", pageParam.getRecords());
        resultMap.put("current", pageParam.getCurrent());
        resultMap.put("pages", pageParam.getPages());
        resultMap.put("size", pageParam.getSize());
        resultMap.put("total", pageParam.getTotal());
        resultMap.put("hasNext",pageParam.hasNext());
        resultMap.put("hasPrevious", pageParam.hasPrevious());

        return resultMap;
    }

}
