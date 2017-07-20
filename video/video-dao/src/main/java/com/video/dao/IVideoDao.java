package com.video.dao;

import java.util.Map;

import com.jacob.base.bean.PageVo;
import com.jacob.base.dao.IBasicDao;
import com.video.domain.enums.VideoTypeEnum;
import com.video.domain.vo.VideoVo;

public interface IVideoDao extends IBasicDao {
	
	/**
	 * 获取配置参数
	 * @return
	 */
	public Map<String, String> getVideoConfigure();
	
	/**
	 * 分页条件查询列表信息
	 * @param shot 排序方式 1/2/3 综合排序 最新发布 最多播放 时长
	 * @param lenTime 时间长度 时长选择  1/2/3/4 0~30 30~60 60~90 90~120 120以上
	 * @param e 视频类型
	 * @param page 分页
	 * @return
	 */
	public PageVo<VideoVo> findAllByPageVo(Integer shot, Integer lenTime, VideoTypeEnum e, PageVo<VideoVo> page);
}
