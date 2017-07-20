package com.video.domain.vo;

import com.video.domain.pojo.Video;

public class VideoVo extends Video{

	private static final long serialVersionUID = -2280929744597201569L;
	
	private String sequenceStr;

	public String getSequenceStr() {
		return sequenceStr;
	}

	public void setSequenceStr(String sequenceStr) {
		this.sequenceStr = sequenceStr;
	}
}
