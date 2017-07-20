package com.jacob.base.annoTable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：数据库表名
 * @author Administrator
 * 
 */
@Target(ElementType.TYPE)//定义注解应用于类
@Retention(RetentionPolicy.RUNTIME)//定义注解在JVM运行时保留
public @interface TableSQL {
	
    /**
     * @return 数据库表名称
     */
    String value() default "";//指定对应的表名
}

