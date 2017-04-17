package com.fanxing.server.io.proto;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记使用单独线程处理的协议方法
 * @author 	hyfu
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 存在RetentionPolicy.RUNTIME、RetentionPolicy.CLASS、RetentionPolicy.SOURCE
@Documented // javadoc可生成相应文档
@Inherited // 可继承
public @interface Alone {

}
