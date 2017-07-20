package com.video.dao;

import com.jacob.base.bean.PageVo;
import com.jacob.base.dao.IBasicDao;
import com.video.domain.enums.ArticleTypeEnum;
import com.video.domain.vo.ArticleVo;

public interface IArticleDao extends IBasicDao {
	/**
	 * 分页条件查询列表信息
	 * @param shot 排序方式
	 * @param e 文章类型
	 * @param page 分页
	 * @return
	 */
	public PageVo<ArticleVo> findAllByPageVo(Integer shot, ArticleTypeEnum e, PageVo<ArticleVo> page);
}
