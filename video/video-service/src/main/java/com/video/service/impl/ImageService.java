package com.video.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.jacob.base.bean.PageVo;
import com.jacob.util.AjaxMessage;
import com.video.dao.IImageDao;
import com.video.domain.enums.ImageTypeEnum;
import com.video.domain.pojo.Image;
import com.video.domain.vo.ImageVo;
import com.video.service.IImageService;

@Service
public class ImageService implements IImageService {
	@Resource private IImageDao imageDao;
	
	@Override
	public String addImage(ImageVo bean) {
		Image image = new Image();
		BeanUtils.copyProperties(bean, image);
		imageDao.save(image);
		return image.getId();
	}

	@Override
	public void updateVideo(ImageVo vo) {
		Image v = new Image();
		BeanUtils.copyProperties(vo, v);
		imageDao.update(v);
	}

	@Override
	public List<ImageVo> findAllAtlasImage() {
		PageVo<ImageVo> page = new PageVo<ImageVo>(1,5);
		page = imageDao.findAllByPageVo(1, ImageTypeEnum.ATLAS, page);
		for (ImageVo vo:page.getList()) {
			//vo.setSequenceStr(ImageSequenceEnum.getEnum(vo.getSequence()).getDesc());
			vo.setSourcesPaths(vo.getSourcesPath().split(";"));
			vo.setThumbnailsPaths(vo.getThumbnailsPath().split(";"));
		}
		return page.getList();
	}

	@Override
	public List<ImageVo> findAllRotationImage() {
		PageVo<ImageVo> page = new PageVo<ImageVo>(1,5);
		page = imageDao.findAllByPageVo(1, ImageTypeEnum.ROTATION, page);
		for (ImageVo vo:page.getList()) {
			//vo.setSequenceStr(ImageSequenceEnum.getEnum(vo.getSequence()).getDesc());
			vo.setSourcesPaths(vo.getSourcesPath().split(";"));
			vo.setThumbnailsPaths(vo.getThumbnailsPath().split(";"));
		}
		return page.getList();
	}

	@Override
	public AjaxMessage findAllByPageVo(Integer shot, ImageTypeEnum e, PageVo<ImageVo> page) {
		page = imageDao.findAllByPageVo(shot, e, page);
		if(null != page.getList()){
			for(ImageVo vo:page.getList()){
				if(null != vo.getThumbnailsPath()){
					vo.setThumbnailsPaths(vo.getThumbnailsPath().split(";"));
				}
				if(null != vo.getSourcesPath()){
					vo.setSourcesPaths(vo.getSourcesPath().split(";"));
				}
			}
		}
		return AjaxMessage.success(page);
	}

}
