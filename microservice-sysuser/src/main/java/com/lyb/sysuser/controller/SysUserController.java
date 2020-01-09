package com.lyb.sysuser.controller;

import com.lyb.common.vo.R;
import com.lyb.sysuser.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyoubin
 * @since 2019/11/14 - 16:56
 */
@Api(value = "系统用户管理")
@RestController
@CrossOrigin
@RequestMapping("/admin/sysuser")
public class SysUserController {

    /**
     * 用户登陆接口
     */
    @ApiOperation(value = "用户登陆")
    @PostMapping("/login")
    public R login(
            @ApiParam(name = "sysUser",value = "系统用户对象",required = true)
            @RequestBody SysUser sysUser){
        return R.ok().data("token", "admin");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("info")
    @ApiOperation(value = "获取用户信息")
    public R info(
        @ApiParam(name = "token", value = "令牌", required = true)
        @RequestParam String token){
        return R.ok()
                .data("roles", "admin")
                .data("name", "admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    /**
     * 用户退出
     */
    @PostMapping("logout")
    @ApiOperation(value = "用户登出")
    public R logout(){
        return R.ok();
    }
}
