package com.jacob.base.bean;

public class PropertyColumn {
	
	/**项目中对应的字段*/
	private String proColName;
	
	/**数据库对应的字段*/
	private String sqlColName;
	
	public String getProColName() {
		return proColName;
	}
	public void setProColName(String proColName) {
		this.proColName = proColName;
	}
	public String getSqlColName() {
		return sqlColName;
	}
	public void setSqlColName(String sqlColName) {
		this.sqlColName = sqlColName;
	}
	
}
