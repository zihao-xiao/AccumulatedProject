package com.video.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.video.dao.IArticleDao;
import com.video.dao.IImageDao;
import com.video.dao.IVideoDao;
import com.video.domain.enums.ArticleSequenceEnum;
import com.video.domain.enums.ImageSequenceEnum;
import com.video.domain.enums.VideoSequenceEnum;
import com.video.domain.pojo.Article;
import com.video.domain.pojo.Image;
import com.video.domain.pojo.Video;
import com.video.domain.vo.ArticleVo;
import com.video.domain.vo.ImageVo;
import com.video.domain.vo.VideoVo;
import com.video.service.IThreadService;

public class ThreadService implements IThreadService{
	
	@Resource private IImageDao imageDao;
	public void setImageDao(IImageDao imageDao) {
		this.imageDao = imageDao;
	}
	
	@Resource private IVideoDao videoDao;
	public void setVideoDao(IVideoDao videoDao) {
		this.videoDao = videoDao;
	}
	
	@Resource private IArticleDao articleDao;
	public void setArticleDao(IArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	@Override
	public List<ImageVo> findAllThumbnailImage() {
		List<Image> list = imageDao.getListByProperty(Image.class, "sequence", ImageSequenceEnum.THUMBNAIL.getSequ());
		List<ImageVo> listVo=new ArrayList<ImageVo>();
		for (Image i:list) {
			ImageVo vo = new ImageVo();
			BeanUtils.copyProperties(i, vo);
			//vo.setSequenceStr(ImageSequenceEnum.getEnum(vo.getSequence()).getDesc());
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public void updateVideo(ImageVo vo) {
		Image v = new Image();
		BeanUtils.copyProperties(vo, v);
		imageDao.update(v);
	}

	@Override
	public List<VideoVo> findAllThumbnailVideo() {
		List<Video> list = videoDao.getListByProperty(Video.class, "sequence", VideoSequenceEnum.THUMBNAIL.getSequ());
		List<VideoVo> listVo=new ArrayList<VideoVo>();
		for (Video v:list) {
			VideoVo vo = new VideoVo();
			BeanUtils.copyProperties(v, vo);
			vo.setSequenceStr(VideoSequenceEnum.getEnum(vo.getSequence()).getDesc());
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public List<VideoVo> findAllTranscoderVideo() {
		List<Video> list = videoDao.getListByProperty(Video.class, "sequence", VideoSequenceEnum.TRANSCODER.getSequ());
		List<VideoVo> listVo=new ArrayList<VideoVo>();
		for (Video v:list) {
			VideoVo vo = new VideoVo();
			BeanUtils.copyProperties(v, vo);
			vo.setSequenceStr(VideoSequenceEnum.getEnum(vo.getSequence()).getDesc());
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public void updateVideo(VideoVo vo) {
		Video v = new Video();
		BeanUtils.copyProperties(vo, v);
		videoDao.update(v);
	}


	@Override
	public List<ArticleVo> findAllCreateFileArticleVo() {
		List<ArticleVo> list = articleDao.query(ArticleVo.class, "SELECT a1.id, a2.text FROM v_title a1 LEFT JOIN v_title_text a2 ON a1.id = a2.id "
				+ "WHERE a1.sequence=?", ArticleSequenceEnum.CREATE_FILE.getSequ());
		return list;
	}

	@Override
	public void updateArticle(ArticleVo vo) {
		Article v = new Article();
		BeanUtils.copyProperties(vo, v);
		articleDao.update(v);
	}

}
