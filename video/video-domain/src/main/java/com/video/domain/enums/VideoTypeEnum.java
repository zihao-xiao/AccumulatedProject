package com.video.domain.enums;

public enum VideoTypeEnum {
	CHINA(1,"中国"),
	JAPAN(2,"日本"),
	EUROPE_AND_THE_UNITED_STATES(3,"欧美"),
	;
	private Integer type;
	private String desc;
	private VideoTypeEnum(Integer type, String desc) {
		this.type=type;
		this.desc=desc;
	}
	
	public static VideoTypeEnum getEnum(Integer type){
		if(null != type){
			for (VideoTypeEnum e:VideoTypeEnum.values()) {
				if(type == e.getType().intValue()){
					return e;
				}
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
