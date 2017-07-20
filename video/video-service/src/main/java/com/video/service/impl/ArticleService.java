package com.video.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.jacob.base.bean.PageVo;
import com.jacob.util.AjaxMessage;
import com.video.dao.IArticleDao;
import com.video.domain.enums.ArticleTypeEnum;
import com.video.domain.pojo.Article;
import com.video.domain.vo.ArticleVo;
import com.video.service.IArticleService;

@Service
public class ArticleService implements IArticleService {
	@Resource private IArticleDao articleDao;

	@Override
	public ArticleVo findById(String aid) {
		Article a = articleDao.getEntityById(Article.class, aid, "id", "titleName", "createdTime");
		ArticleVo vo = new ArticleVo();
		BeanUtils.copyProperties(a, vo);
		return vo;
	}

	@Override
	public AjaxMessage findAllByPageVo(Integer shot, ArticleTypeEnum e, PageVo<ArticleVo> page) {
		page = articleDao.findAllByPageVo(shot, e, page);
		return AjaxMessage.success(page);
	}
}
