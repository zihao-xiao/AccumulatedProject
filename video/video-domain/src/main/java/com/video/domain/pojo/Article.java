package com.video.domain.pojo;

import java.util.Date;

import com.jacob.base.annoTable.Constraint;
import com.jacob.base.annoTable.GenerateKey;
import com.jacob.base.annoTable.TableColumnSQL;
import com.jacob.base.annoTable.TableSQL;
import com.jacob.base.bean.BaseBean;

@TableSQL(value="v_title")
public class Article  extends BaseBean {

	private static final long serialVersionUID = 1734209746030824666L;
	
	@TableColumnSQL(value="id",
			constraint=@Constraint(
					allowNull=false,
					isPrimary=true,
					generateKey=GenerateKey.UUID))
	private String id;
	private String titleName;
	private String titleDesc;
	private String titleAuthor;
	private String createId;
	private Date createdTime;
	private String updateId;
	private Date updateTime;
	private Integer index;
	private Integer views;
	private Integer type;
	private Integer sequence;
	private Integer status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getTitleDesc() {
		return titleDesc;
	}
	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}
	public String getTitleAuthor() {
		return titleAuthor;
	}
	public void setTitleAuthor(String titleAuthor) {
		this.titleAuthor = titleAuthor;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
