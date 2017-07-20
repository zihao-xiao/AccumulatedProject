package com.video.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jacob.base.bean.PageVo;
import com.jacob.base.bean.SqlPageQuery;
import com.jacob.base.bean.SqlQuery;
import com.jacob.base.dao.BasicDao;
import com.video.dao.IImageDao;
import com.video.domain.enums.ImageTypeEnum;
import com.video.domain.vo.ImageVo;

@Repository
public class ImageDao extends BasicDao implements IImageDao {

	@Override
	public PageVo<ImageVo> findAllByPageVo(Integer shot, ImageTypeEnum e, PageVo<ImageVo> page) {
		SqlQuery sq = new SqlQuery();
		sq.setSelect("SELECT i.id, i.img_desc, i.img_num, i.sources_path, i.thumbnails_path ");
		sq.setFrom("FROM v_image i ");
		
		List<Object> list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("WHERE ");
		
		if(null != e){
			list.add(e.getType());
			sb.append("i.type = ? AND ");
		}
		
		list.add(0);
		sb.append("i.status <> ? ");
		
		if(null != shot){
			if(1 == shot.intValue()){
				sb.append(" ORDER BY i.index DESC");
			}else if(2 == shot.intValue()){
				sb.append(" ORDER BY i.create_time DESC");
			}else if(3 == shot.intValue()){
				sb.append(" ORDER BY i.views DESC");
			}
		}
		sq.setWhere(sb.toString());
		return SqlPageQuery.query(this, ImageVo.class, sq, page, list.toArray());
	}

}
