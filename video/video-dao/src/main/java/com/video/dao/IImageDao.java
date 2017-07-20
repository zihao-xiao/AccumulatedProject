package com.video.dao;

import com.jacob.base.bean.PageVo;
import com.jacob.base.dao.IBasicDao;
import com.video.domain.enums.ImageTypeEnum;
import com.video.domain.vo.ImageVo;

public interface IImageDao extends IBasicDao  {
	
	/**
	 * 分页条件查询列表信息
	 * @param shot 排序方式
	 * @param e 图片类型
	 * @param page 分页
	 * @return
	 */
	public PageVo<ImageVo> findAllByPageVo(Integer shot, ImageTypeEnum e, PageVo<ImageVo> page);
}
