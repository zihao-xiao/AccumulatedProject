package com.video.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jacob.base.bean.PageVo;
import com.jacob.base.bean.SqlPageQuery;
import com.jacob.base.bean.SqlQuery;
import com.jacob.base.dao.BasicDao;
import com.video.dao.IVideoDao;
import com.video.domain.enums.VideoTypeEnum;
import com.video.domain.vo.VideoVo;

@Repository
public class VideoDao extends BasicDao implements IVideoDao{

	@Override
	public Map<String, String> getVideoConfigure() {
		List<Map<String, Object>> mapList = queryListMap("SELECT a.key,a.value FROM video_configure a");
		Map<String, String> map = new HashMap<String, String>();
		for(Map<String, Object> m:mapList){
			map.put(m.get("key").toString(), m.get("value").toString());
		}
		return map;
	}

	@Override
	public PageVo<VideoVo> findAllByPageVo(Integer shot, Integer lenTime, VideoTypeEnum e, PageVo<VideoVo> page) {
		SqlQuery sq = new SqlQuery();
		sq.setSelect("SELECT v.id, v.video_desc, v.thumbnails_path, v.create_time ");
		sq.setFrom("FROM v_video v ");
		
		List<Object> list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("WHERE ");
		if(null != lenTime){
			if(1 == lenTime.intValue()){
				list.add(0);
				list.add(30);
				sb.append("v.duration > ? AND v.duration <= ? AND ");
			}else if(2 == lenTime.intValue()){
				list.add(30);
				list.add(60);
				sb.append("v.duration > ? AND v.duration <= ? AND ");
			}else if(3 == lenTime.intValue()){
				list.add(60);
				list.add(90);
				sb.append("v.duration > ? AND v.duration <= ? AND ");
			}else if(4 == lenTime.intValue()){
				list.add(90);
				list.add(120);
				sb.append("v.duration > ? AND v.duration <= ? AND ");
			}else{
				list.add(120);
				sb.append("v.duration > ? AND ");
			}
		}
		
		if(null != e){
			list.add(e.getType());
			sb.append("v.type = ? AND ");
		}
		
		list.add(0);
		sb.append("v.status <> ? ");
		/*
		if(0 < list.size()){
			sb = sb.delete(sb.length() - 4, sb.length());
		}
		*/
		
		if(null != shot){
			if(1 == shot.intValue()){
				sb.append(" ORDER BY v.index DESC");
			}else if(2 == shot.intValue()){
				sb.append(" ORDER BY v.create_time DESC");
			}else if(3 == shot.intValue()){
				sb.append(" ORDER BY v.views DESC");
			}else{
				sb.append(" ORDER BY v.duration DESC");
			}
		}
		sq.setWhere(sb.toString());
		return SqlPageQuery.query(this, VideoVo.class, sq, page, list.toArray());
	}

}
