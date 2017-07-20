package com.video.domain.pojo;

import java.util.Date;

import com.jacob.base.annoTable.Constraint;
import com.jacob.base.annoTable.GenerateKey;
import com.jacob.base.annoTable.TableColumnSQL;
import com.jacob.base.annoTable.TableSQL;
import com.jacob.base.bean.BaseBean;

@TableSQL(value="v_image")
public class Image  extends BaseBean {

	private static final long serialVersionUID = 6262196503109538157L;
	
	@TableColumnSQL(value="id",
			constraint=@Constraint(
					allowNull=false,
					isPrimary=true,
					generateKey=GenerateKey.UUID))
	private String id;
	private String imgName;
	private String imgTitle;
	private String realPath;
	private String sourcesPath;
	private String thumbnailsPath;
	private Integer imgNum;
	private String imgDesc;
	private String createId;
	private Date createdTime;
	private String updateId;
	private Date updateTime;
	private Integer type;
	private Integer views;
	private Integer index;
	private Integer sequence;
	private Integer status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgTitle() {
		return imgTitle;
	}
	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getSourcesPath() {
		return sourcesPath;
	}
	public void setSourcesPath(String sourcesPath) {
		this.sourcesPath = sourcesPath;
	}
	public String getThumbnailsPath() {
		return thumbnailsPath;
	}
	public void setThumbnailsPath(String thumbnailsPath) {
		this.thumbnailsPath = thumbnailsPath;
	}
	public Integer getImgNum() {
		return imgNum;
	}
	public void setImgNum(Integer imgNum) {
		this.imgNum = imgNum;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
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
	/**类型*/
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	/**访问量*/
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	/**排序*/
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
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
	@Override
	public String toString() {
		return "Image [id=" + id + ", imgName=" + imgName + ", imgTitle=" + imgTitle + ", realPath=" + realPath
				+ ", sourcesPath=" + sourcesPath + ", thumbnailsPath=" + thumbnailsPath + ", imgNum=" + imgNum
				+ ", imgDesc=" + imgDesc + ", createId=" + createId + ", createdTime=" + createdTime + ", updateId="
				+ updateId + ", updateTime=" + updateTime + ", sequence=" + sequence + ", status=" + status + "]";
	}

}
