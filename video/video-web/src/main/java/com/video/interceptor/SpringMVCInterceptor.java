package com.video.interceptor;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

/**
 * 拦截器
 * 拦截器是在面向切面编程中应用的，就是在你的service或者一个方法前调用一个方法，或者在方法后调用一个方法。是基于JAVA的反射机制。
 * 拦截器不是在web.xml，比如spring在spring.xml中配置
 * @author Administrator
 *
 */
public class SpringMVCInterceptor implements HandlerInterceptor{
	
	private static final Logger logger = Logger.getLogger(SpringMVCInterceptor.class);
	
	/**
	 * 执行完控制器后调用，即离开时
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		logger.debug("拦截器：整个请求处理完毕回调方法");
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		logger.debug("拦截器：后处理回调方法");
		
	}
	
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		logger.debug("拦截器：预处理回调方法");
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String url = urlPathHelper.getLookupPathForRequest(arg0);
		logger.debug("请求路径："+url);
		if (Pattern.matches("/assets/.*", url)){
			return true;
		}
		if (Pattern.matches("/login/.*", url)) {
			return true;
		}
		return true;
	}
}
