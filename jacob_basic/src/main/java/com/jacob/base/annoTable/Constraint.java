package com.jacob.base.annoTable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：数据库字段约束条件
 * @author Administrator
 */
@Target(ElementType.FIELD)//定义注解应用于成员变量
@Retention(RetentionPolicy.RUNTIME)//定义注解在JVM运行时保留
public @interface Constraint{
	
	/**
	 * @return 是否允许为空
	 */
	boolean allowNull() default true; 
	
	/**
	 * @return 是否为主键
	 */
	boolean isPrimary() default false;
	
	/**
	 * 主键生成方式
	 * @return
	 */
	 public GenerateKey generateKey() default GenerateKey.IDENTITY;
}
