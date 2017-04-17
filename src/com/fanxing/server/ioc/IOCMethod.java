package com.fanxing.server.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 存在RetentionPolicy.RUNTIME、RetentionPolicy.CLASS、RetentionPolicy.SOURCE
@Documented // javadoc可生成相应文档
@Inherited // 可继承
public @interface IOCMethod {

}
