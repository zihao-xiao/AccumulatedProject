package com.jacob.base.annoTable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：数据库字段名
 * @author Administrator
 * 
 */
@Target(ElementType.FIELD)// 定义注解应用于成员变量
@Retention(RetentionPolicy.RUNTIME)// 定义注解在JVM运行时保留
public @interface TableColumnSQL {
	/**
	 * @return 字段名
	 */
	String value() default "";
	
	/**
	 * @return 字段约束条件
	 */
	Constraint constraint() default @Constraint();
}
