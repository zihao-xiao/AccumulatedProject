package com.video.domain.enums;

/**
 * 視頻文件保存 後台執行順序
 * @author jacob
 *
 */
public enum ImageSequenceEnum {
	/** 創建 */
	SAVE(1, "創建"),
	/** 生成縮略圖 */
	THUMBNAIL(2, "生成縮略圖"),
	/** 創建完成 */
	COMPLETE(4, "創建完成");
	
	private int sequ;
	private String  desc;

	private ImageSequenceEnum(int sequ, String desc) {
		this.sequ = sequ;
		this.desc = desc;
	}
	
	public int getSequ() {
		return sequ;
	}

	public void setSequ(int sequ) {
		this.sequ = sequ;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static ImageSequenceEnum getEnum(int i){
		for (ImageSequenceEnum e:ImageSequenceEnum.values()) {
			if(e.getSequ() == i){
				return e;
			}
		}
		return null;
	}
}
