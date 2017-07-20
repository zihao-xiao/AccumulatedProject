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
import com.video.domain.vo.ArticleVo;
import com.video.service.IArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	private final Logger logger = Logger.getLogger(getClass());
	@Resource private IArticleService articleService;
	
	/**
	 * @return
	 */
	@RequestMapping(value={"/line_text"})
	public String lineText(Model model){
		//文章类型
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(ArticleTypeEnum e:ArticleTypeEnum.values()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type",e.getType());
			map.put("name", e.getDesc());
			list.add(map);
		}
		model.addAttribute("articleTypes", list);
		return "page/article/lineText";
	}
	
	/**
	 * 分页条件查询图片列表信息
	 * @param shot 1/2/3 综合排序 最新发布 时长
	 * @param e 图片类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="article_paging_query",produces=GlobalConstant.JSON_ENCODING)
	public String ArticlePagingQuery(HttpServletRequest request,
			@RequestParam(value = "s", required = false)Integer shot, 
			@RequestParam(value = "e", required = false)Integer enums){
		PageVo<ArticleVo> page=ServletToo.getPage(request, ArticleVo.class);
		AjaxMessage ajax = articleService.findAllByPageVo(shot, ArticleTypeEnum.getEnum(enums), page);
		return ajax.toJsonString();
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value={"/line_text_look"})
	public String lineTextLook(String aid, Model model){
		ArticleVo vo = articleService.findById(aid);
		model.addAttribute("bean", vo);
		return "page/article/lineTextLook";
	}
}
