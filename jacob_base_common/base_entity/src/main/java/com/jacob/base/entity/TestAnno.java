package com.jacob.base.entity;

import com.jacob.base.annoTable.Constraint;
import com.jacob.base.annoTable.TableColumnSQL;
import com.jacob.base.annoTable.TableSQL;

/**
 * 测试用：定义User类与数据库映射(注解)
 * @author Administrator
 * 2016年9月27日14:23:32
 */
@TableSQL(value="test_anno")
public class TestAnno {
  
	//定义id字段，与表user的列id相映射，指定约束为：不为空，为主键。
	@TableColumnSQL(value="id",constraint=@Constraint(allowNull=false,isPrimary=true))
	String id;
  
	//只为注解指定value字段，可省略value。
	@TableColumnSQL(value="name")
	String name;

	//如果没有注解则显示其原有的属性
	//（大写字符使用"_"+小写字符替换，例如：nickName==>nick_name）
	Byte sex;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}
	
}