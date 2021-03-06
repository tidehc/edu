package com.lyb.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liuyoubin
 * @since 2019/11/9 - 14:07
 * 自动填充处理器
 */
@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入数据时进行自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    /**
     * 更新数据时进行自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
