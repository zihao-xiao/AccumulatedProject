package com.video.service;

import java.util.List;

import com.jacob.base.bean.PageVo;
import com.jacob.util.AjaxMessage;
import com.video.domain.enums.ImageTypeEnum;
import com.video.domain.vo.ImageVo;

public interface IImageService {

	/**
	 * 添加图片信息返回生成的ID字符串
	 * @param bean
	 * @return
	 */
	public String addImage(ImageVo bean);
	
	/**
	 * 修改图片信息
	 * @param vo
	 */
	public void updateVideo(ImageVo vo);
	
	/**
	 * 查询轮播（banner）图片集
	 * @return
	 */
	public List<ImageVo> findAllAtlasImage();
	
	/**
	 * 查询所有轮播图
	 * @return
	 */
	public List<ImageVo> findAllRotationImage();
	
	/**
	 * 分页条件查询列表信息
	 * @param shot 排序方式 1/2/3 综合排序 最新上传 最多访问
	 * @param e 图片类型
	 * @param page 分页
	 * @return
	 */
	public AjaxMessage findAllByPageVo(Integer shot, ImageTypeEnum e, PageVo<ImageVo> page);
}
