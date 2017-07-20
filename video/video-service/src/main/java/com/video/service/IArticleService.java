package com.video.service;

import com.jacob.base.bean.PageVo;
import com.jacob.util.AjaxMessage;
import com.video.domain.enums.ArticleTypeEnum;
import com.video.domain.vo.ArticleVo;

public interface IArticleService {
	
	/**
	 * 通过文章ID获取文章信息
	 * @param vid
	 * @return
	 */
	public ArticleVo findById(String aid);
	
	/**
	 * 分页条件查询列表信息
	 * @param shot 排序方式
	 * @param e 视频类型
	 * @param page 分页
	 * @return
	 */
	public AjaxMessage findAllByPageVo(Integer shot, ArticleTypeEnum e, PageVo<ArticleVo> page);
}
