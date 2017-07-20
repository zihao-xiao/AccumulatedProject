package com.jacob.base.entity;

import java.util.Date;

import com.jacob.base.annoTable.Constraint;
import com.jacob.base.annoTable.TableColumnSQL;
import com.jacob.base.annoTable.TableSQL;
import com.jacob.base.bean.BaseBean;

@TableSQL(value="t_log")
public class Log extends BaseBean{

	private static final long serialVersionUID = -77422058039463342L;
	
	@TableColumnSQL(value="id",constraint=@Constraint(allowNull=false,isPrimary=true))
	private Long id;
	private Long userid;//管理员id  
    private Date created;//日期  
    private String content;//日志内容  
    private String operation;//操作(主要是"添加"、"修改"、"删除")
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Override
	public String toString() {
		return "Log [userid=" + userid + ", createdate=" + created + ", content=" + content + ", operation="
				+ operation + "]";
	}
}
