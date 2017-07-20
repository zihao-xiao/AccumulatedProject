package com.video.domain.vo;

import com.video.domain.pojo.Image;

public class ImageVo extends Image{

	private static final long serialVersionUID = -1558494639603677020L;
	
	private String[] sourcesPaths;
	private String[] thumbnailsPaths;
	public String[] getSourcesPaths() {
		return sourcesPaths;
	}
	public void setSourcesPaths(String[] sourcesPaths) {
		this.sourcesPaths = sourcesPaths;
	}
	public String[] getThumbnailsPaths() {
		return thumbnailsPaths;
	}
	public void setThumbnailsPaths(String[] thumbnailsPaths) {
		this.thumbnailsPaths = thumbnailsPaths;
	}
}
