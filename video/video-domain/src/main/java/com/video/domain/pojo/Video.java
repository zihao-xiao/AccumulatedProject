package com.video.domain.pojo;

import java.util.Date;

import com.jacob.base.annoTable.Constraint;
import com.jacob.base.annoTable.GenerateKey;
import com.jacob.base.annoTable.TableColumnSQL;
import com.jacob.base.annoTable.TableSQL;
import com.jacob.base.bean.BaseBean;

/**
 * @author jacob
 * 
 */
@TableSQL(value="v_video")
public class Video extends BaseBean {

	private static final long serialVersionUID = 8906967430975499228L;
	
	@TableColumnSQL(value="id",
			constraint=@Constraint(
					allowNull=false,
					isPrimary=true,
					generateKey=GenerateKey.UUID))
	private String id;
	private String videoName;
	private String videoDesc;
	private String videoTitle;
	private String realPath;
	private String sourcePath;
	private String thumbnailsPath;
	private String compilePath;
	private String createId;
	private Date createTime;
	private String updateId;
	private Date updateTime;
	private Integer index;
	private Integer type;
	private Integer views;
	private Integer duration;
	private Integer sequence;
	private Integer status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoDesc() {
		return videoDesc;
	}
	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getThumbnailsPath() {
		return thumbnailsPath;
	}
	public void setThumbnailsPath(String thumbnailsPath) {
		this.thumbnailsPath = thumbnailsPath;
	}
	public String getCompilePath() {
		return compilePath;
	}
	public void setCompilePath(String compilePath) {
		this.compilePath = compilePath;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	/**排序*/
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
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
	/**时长*/
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
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
