package com.video.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jacob.base.bean.PageVo;
import com.jacob.base.bean.SqlPageQuery;
import com.jacob.base.bean.SqlQuery;
import com.jacob.base.dao.BasicDao;
import com.video.dao.IArticleDao;
import com.video.domain.enums.ArticleTypeEnum;
import com.video.domain.vo.ArticleVo;

@Repository
public class ArticleDao extends BasicDao implements IArticleDao {

	@Override
	public PageVo<ArticleVo> findAllByPageVo(Integer shot, ArticleTypeEnum e, PageVo<ArticleVo> page) {
		SqlQuery sq = new SqlQuery();
		sq.setSelect("SELECT t.id, t.title_name, t.title_desc, t.created_time ");
		sq.setFrom("FROM v_title t ");
		
		List<Object> list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("WHERE ");
		
		if(null != e){
			list.add(e.getType());
			sb.append("t.type = ? AND ");
		}
		
		list.add(0);
		sb.append("t.status <> ? ");
		
		if(null != shot){
			if(1 == shot.intValue()){
				sb.append(" ORDER BY t.index DESC");
			}else if(2 == shot.intValue()){
				sb.append(" ORDER BY t.create_time DESC");
			}else if(3 == shot.intValue()){
				sb.append(" ORDER BY t.views DESC");
			}
		}
		sq.setWhere(sb.toString());
		return SqlPageQuery.query(this, ArticleVo.class, sq, page, list.toArray());
	}

}
