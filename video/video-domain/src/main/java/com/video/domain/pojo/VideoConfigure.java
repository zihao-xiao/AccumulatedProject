package com.video.domain.pojo;

import com.jacob.base.annoTable.TableSQL;
import com.jacob.base.bean.BaseBean;

/**
 * 视频解码配置参数
 * @author jacob
 * 2017年6月29日13:04:49
 */
@TableSQL(value="video_configure")
public class VideoConfigure extends BaseBean {

	private static final long serialVersionUID = 8899653454157250989L;
	private String key;
	private String value;
	private String remark;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
