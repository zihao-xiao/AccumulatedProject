package com.video.service;

import java.util.List;
import java.util.Map;

import com.jacob.base.bean.PageVo;
import com.jacob.util.AjaxMessage;
import com.video.domain.enums.VideoTypeEnum;
import com.video.domain.vo.VideoVo;

public interface IVideoService{
	
	/**
	 * 获取配置参数
	 * @return
	 */
	public Map<String, String> getVideoConfigure();
	
	/**
	 * 添加视频信息返回生成的ID字符串
	 * @param bean
	 * @return
	 */
	public String addVideo(VideoVo bean);
	
	/**
	 * 擦訊所有需要生成缩略图的视屏文件信息
	 * @return
	 */
	public List<VideoVo> findAllThumbnailVideo();
	
	/**
	 * 擦訊所有需要解碼的視頻信息
	 * @return
	 */
	public List<VideoVo> findAllTranscoderVideo();
	
	/**
	 * 更新数据
	 * @return
	 */
	public void updateVideo(VideoVo vo);
	
	/**
	 * 通过项目ID获取视频信息
	 * @param vid
	 * @return
	 */
	public VideoVo findById(String vid);
	
	/**
	 * 查询最新的上传视频
	 * @return
	 */
	public PageVo<VideoVo> findAllUpToDateVideo(PageVo<VideoVo> page);
	
	/**
	 * 分页条件查询列表信息
	 * @param shot 排序方式
	 * @param lenTime 时间长度
	 * @param e 视频类型
	 * @param page 分页
	 * @return
	 */
	public AjaxMessage findAllByPageVo(Integer shot, Integer lenTime, VideoTypeEnum e, PageVo<VideoVo> page);
}
