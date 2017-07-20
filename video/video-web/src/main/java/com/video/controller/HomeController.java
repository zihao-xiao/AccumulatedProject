package com.video.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jacob.base.bean.PageVo;
import com.jacob.tools.ServletToo;
import com.video.domain.vo.ImageVo;
import com.video.domain.vo.VideoVo;
import com.video.service.IImageService;
import com.video.service.IVideoService;

@Controller
public class HomeController {
	private final Logger logger = Logger.getLogger(getClass());
	@Resource private IImageService imageService;
	@Resource private IVideoService videoService;
	
	/**
	 * @return
	 */
	@RequestMapping(value={"/","/index"})
	public String index(HttpServletRequest request, Model model){
		logger.debug("首页");
		List<ImageVo> imgList = imageService.findAllRotationImage();
		model.addAttribute("imgList", imgList);
		
		
		PageVo<VideoVo> page=ServletToo.getPage(request, VideoVo.class);
		page.setSize(5);
		page = videoService.findAllUpToDateVideo(page);
		model.addAttribute("list", page.getList());
		return "page/index";
	}
	
}
