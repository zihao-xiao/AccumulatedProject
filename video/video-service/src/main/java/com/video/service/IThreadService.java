package com.video.service;

import java.util.List;

import com.video.domain.vo.ArticleVo;
import com.video.domain.vo.ImageVo;
import com.video.domain.vo.VideoVo;

public interface IThreadService {
	
	/**
	 * 查询所有需要生成缩略图的图片
	 * @return
	 */
	public List<ImageVo> findAllThumbnailImage();
	
	/**
	 * 修改图片信息
	 * @param vo
	 */
	public void updateVideo(ImageVo vo);
	
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
	 * 修改数据
	 * @return
	 */
	public void updateVideo(VideoVo vo);
	
	/**
	 * 擦訊所有需要创建静态文件的文章
	 * @return
	 */
	public List<ArticleVo> findAllCreateFileArticleVo();
	
	/**
	 * 修改文章信息
	 * @param vo
	 */
	public void updateArticle(ArticleVo vo);
}
