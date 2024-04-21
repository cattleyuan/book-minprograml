package com.ab.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 设置方式一
//        metaObject.setValue("createTime",new Date());
//        metaObject.setValue("updateTime",new Date());

        //设置方式二
        // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime",LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject,"version", Integer.class,1);
        this.strictInsertFill(metaObject,"deleted", Integer.class,0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime",LocalDateTime.class,LocalDateTime.now());
    }
}