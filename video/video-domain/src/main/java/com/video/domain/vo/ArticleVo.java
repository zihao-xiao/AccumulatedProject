package com.video.domain.vo;

import com.video.domain.pojo.Article;

public class ArticleVo extends Article {

	private static final long serialVersionUID = -4417732388832723910L;

	private String text;
	private String sequenceStr;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSequenceStr() {
		return sequenceStr;
	}
	public void setSequenceStr(String sequenceStr) {
		this.sequenceStr = sequenceStr;
	}
	
}
