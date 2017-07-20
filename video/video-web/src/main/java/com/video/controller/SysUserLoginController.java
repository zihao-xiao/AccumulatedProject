package com.video.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jacob.base.annoTable.SystemControllerLog;
import com.jacob.util.GlobalConstant;

@Controller
@RequestMapping("/login")
public class SysUserLoginController {
	private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * @return
	 */
	@RequestMapping(value={"/","index"})
	public String index(){
		logger.debug("进入登录页面页面");
		return "page/login/index";
	}
	
	/**
	 * @return
	 */
	@ResponseBody
	@SystemControllerLog(desc = "登录账号-------------------------------------------")
	@RequestMapping(value="login",produces=GlobalConstant.JSON_ENCODING)
	public String login(String name,String pwd){
		logger.debug("进入登录页面页面");
		return "提交数据成功";
	}
}
