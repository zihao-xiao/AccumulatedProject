package com.video.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.jacob.base.bean.PageVo;
import com.jacob.util.AjaxMessage;
import com.video.dao.IVideoDao;
import com.video.domain.enums.VideoSequenceEnum;
import com.video.domain.enums.VideoTypeEnum;
import com.video.domain.pojo.Video;
import com.video.domain.vo.VideoVo;
import com.video.service.IVideoService;

@Service
public class VideoService implements IVideoService{
	@Resource private IVideoDao videoDao;

	@Override
	public Map<String, String> getVideoConfigure() {
		return videoDao.getVideoConfigure();
	}
	
	@Override
	public String addVideo(VideoVo bean) {
		Video video = new Video();
		BeanUtils.copyProperties(bean, video);
		videoDao.save(video);
		return video.getId();
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
	public VideoVo findById(String vid) {
		Video v = videoDao.getEntityById(Video.class, vid, "videoTitle", "compilePath", "videoDesc");
		VideoVo vo = new VideoVo();
		BeanUtils.copyProperties(v, vo);
		return vo;
	}
	@Override
	public PageVo<VideoVo> findAllUpToDateVideo(PageVo<VideoVo> page) {
		page = videoDao.findAllByPageVo(2, null, null, page);
		return page;
	}

	@Override
	public AjaxMessage findAllByPageVo(Integer shot, Integer lenTime, VideoTypeEnum e, PageVo<VideoVo> page) {
		page = videoDao.findAllByPageVo(shot, lenTime, e, page);
		return AjaxMessage.success(page);
	}

	
}
