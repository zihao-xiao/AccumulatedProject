package com.video.domain.enums;

public enum ImageTypeEnum {
	ATLAS(1,"图集"),
	ROTATION(2,"轮播"),
	;
	private Integer type;
	private String desc;
	private ImageTypeEnum(Integer type, String desc) {
		this.type=type;
		this.desc=desc;
	}
	
	public static ImageTypeEnum getEnum(int type){
		for (ImageTypeEnum e:ImageTypeEnum.values()) {
			if(type == e.getType().intValue()){
				return e;
			}
		}
		return null;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
