package com.video.domain.enums;

/**
 * 視頻文件保存 後台執行順序
 * @author jacob
 *
 */
public enum ArticleSequenceEnum {
	/** 創建 */
	SAVE(1, "創建"),
	/** 创建静态文件*/
	CREATE_FILE(2, "创建静态文件"),
	/** 創建完成 */
	COMPLETE(3, "創建完成");
	
	private int sequ;
	private String  desc;

	private ArticleSequenceEnum(int sequ, String desc) {
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
	
	public static ArticleSequenceEnum getEnum(int i){
		for (ArticleSequenceEnum e:ArticleSequenceEnum.values()) {
			if(e.getSequ() == i){
				return e;
			}
		}
		return null;
	}
}
