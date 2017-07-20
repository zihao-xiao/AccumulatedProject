package com.video.controller;

import java.util.List;

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
import com.video.domain.enums.ImageTypeEnum;
import com.video.domain.vo.ImageVo;
import com.video.service.IImageService;

@Controller
@RequestMapping("/image")
public class ImageController {
	private final Logger logger = Logger.getLogger(getClass());
	@Resource private IImageService imageService;
	
	/**
	 * @return
	 */
	@RequestMapping(value={"/index", "/line_img"})
	public String lineImg(Model model){
		List<ImageVo> list = imageService.findAllAtlasImage();
		model.addAttribute("list", list);
		return "page/image/lineImg";
	}
	
	/**
	 * 分页条件查询图片列表信息
	 * @param shot 1/2/3 综合排序 最新发布 时长
	 * @param e 图片类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Image_paging_query",produces=GlobalConstant.JSON_ENCODING)
	public String ImagePagingQuery(HttpServletRequest request,
			@RequestParam(value = "s", required = false)Integer shot, 
			@RequestParam(value = "e", required = false)Integer enums){
		PageVo<ImageVo> page=ServletToo.getPage(request, ImageVo.class);
		AjaxMessage ajax = imageService.findAllByPageVo(shot, ImageTypeEnum.getEnum(enums), page);
		return ajax.toJsonString();
	}
}
