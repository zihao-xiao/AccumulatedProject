package com.jacob.base.bean;

/**
 * 视频解码配置参数
 * 
 * @author jacob 2017年6月25日15:46:37
 */
public class TranscoderConfigure {
	
	/** 输出文件格式:flv */
	public String outfmt="flv";
	/** 编码器 :libx264 */
	public String vcodec="libx264";
	/** 比特率 :500000*/
	public String bv="500000";
	/** 视频帧速率:25*/
	public String framerate="25";
	/** 编码器：libmp3lame*/
	public String acodec="libmp3lame";
	/** 比特率:64000*/
	public String bc="64000";
	/** 音频采样率:22050*/
	public String ar="22050";
	/** 宽度:640*/
	public String scaleWidth="640";
	/** 高度:360*/
	public String scaleHeight="360";
	/** 保留原始宽高比:true*/
	public String keepaspectratio="true";
	/** 使用水印:true*/
	public String watermarkuse="true";
	/** 水印所在左边距*/
	public String watermarkWidth="5";
	/** 水印所在下边距*/
	public String watermarkHeight="5";
	
	public String getWatermarkuse() {
		return watermarkuse;
	}

	public void setWatermarkuse(String watermarkuse) {
		this.watermarkuse = watermarkuse;
	}

	public String getOutfmt() {
		return outfmt;
	}

	public void setOutfmt(String outfmt) {
		this.outfmt = outfmt;
	}

	public String getVcodec() {
		return vcodec;
	}

	public void setVcodec(String vcodec) {
		this.vcodec = vcodec;
	}

	public String getBv() {
		return bv;
	}

	public void setBv(String bv) {
		this.bv = bv;
	}

	public String getFramerate() {
		return framerate;
	}

	public void setFramerate(String framerate) {
		this.framerate = framerate;
	}

	public String getAcodec() {
		return acodec;
	}

	public void setAcodec(String acodec) {
		this.acodec = acodec;
	}

	public String getBc() {
		return bc;
	}

	public void setBc(String bc) {
		this.bc = bc;
	}

	public String getAr() {
		return ar;
	}

	public void setAr(String ar) {
		this.ar = ar;
	}

	public String getScaleWidth() {
		return scaleWidth;
	}

	public void setScaleWidth(String scaleWidth) {
		this.scaleWidth = scaleWidth;
	}

	public String getScaleHeight() {
		return scaleHeight;
	}

	public void setScaleHeight(String scaleHeight) {
		this.scaleHeight = scaleHeight;
	}

	public String getKeepaspectratio() {
		return keepaspectratio;
	}

	public void setKeepaspectratio(String keepaspectratio) {
		this.keepaspectratio = keepaspectratio;
	}

	public String getWatermarkWidth() {
		return watermarkWidth;
	}

	public void setWatermarkWidth(String watermarkWidth) {
		this.watermarkWidth = watermarkWidth;
	}

	public String getWatermarkHeight() {
		return watermarkHeight;
	}

	public void setWatermarkHeight(String watermarkHeight) {
		this.watermarkHeight = watermarkHeight;
	}
}
