package com.video.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jacob.base.bean.PageVo;
import com.jacob.tools.ServletToo;
import com.jacob.util.AjaxMessage;
import com.jacob.util.GlobalConstant;
import com.video.domain.enums.ArticleTypeEnum;
import com.video.domain.enums.VideoTypeEnum;
import com.video.domain.vo.VideoVo;
import com.video.service.IVideoService;

@Controller
@RequestMapping("/video")
public class VideoController {
	private final Logger logger = Logger.getLogger(getClass());
	@Resource private IVideoService videoService;
	
	
	
	/**
	 * @return
	 */
	@RequestMapping(value={"/line_video"})
	public String lineVideo(Model model){
		//视频类型
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(VideoTypeEnum e:VideoTypeEnum.values()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type",e.getType());
			map.put("name", e.getDesc());
			list.add(map);
		}
		model.addAttribute("videoTypes", list);
		return "page/video/lineVideo";
	}

	/**
	 * 分页条件查询视频列表信息
	 * @param shot 1/2/3 综合排序 最新发布 最多播放 时长
	 * @param lenTime 时长选择  1/2/3/4 0~30 30~60 60~90 90~120 120以上
	 * @param e 视频类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="video_paging_query",produces=GlobalConstant.JSON_ENCODING)
	public String videoPagingQuery(HttpServletRequest request,
			@RequestParam(value = "s", required = false)Integer shot, 
			@RequestParam(value = "l", required = false)Integer lenTime, 
			@RequestParam(value = "e", required = false)Integer enums){
		PageVo<VideoVo> page=ServletToo.getPage(request, VideoVo.class);
		AjaxMessage ajax = videoService.findAllByPageVo(shot, lenTime, VideoTypeEnum.getEnum(enums), page);
		return ajax.toJsonString();
	}
	
	
	/**
	 * 视频播放页面
	 * @param vid
	 * @return
	 */
	@RequestMapping(value={"/video_player"})
	public String videoPlayer(String vid, Model model){
		logger.debug("进入登录页面页面");
		VideoVo vo = videoService.findById(vid);
		model.addAttribute("video", vo);
		return "page/video/videoPlayer";
	}

}