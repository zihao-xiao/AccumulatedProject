package com.video.domain.enums;

public enum ArticleTypeEnum {
	CAMPUS(1,"校园"),
	SKILL(2,"技巧"),
	;
	private Integer type;
	private String desc;
	private ArticleTypeEnum(Integer type, String desc) {
		this.type=type;
		this.desc=desc;
	}
	
	public static ArticleTypeEnum getEnum(Integer type){
		if(null != type){
			for (ArticleTypeEnum e:ArticleTypeEnum.values()) {
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
