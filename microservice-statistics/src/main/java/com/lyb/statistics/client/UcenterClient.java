package com.lyb.statistics.client;

import com.lyb.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liuyoubin
 * @since 2020/1/16 - 22:44
 * 远程调用Ucenter模块的接口
 */
@Component
@FeignClient("ucenter") //指定从哪个服务调用功能，名称与被调用的服务名保持一致
public interface UcenterClient {

    /**
     * 查询指定日期的注册人数
     * @param day 指定日期
     */
    @GetMapping(value = "/admin/ucenter/member/registerCount/{day}")
    R registerMemberCountByDay(@PathVariable(value = "day") String day);

}
