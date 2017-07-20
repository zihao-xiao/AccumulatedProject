package com.video.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 过滤器：登录<br>
 * Servlet中的过滤器Filter是实现了javax.servlet.Filter接口的服务器端程序，主要的用途是过滤字符编码、做一些业务逻辑判断等。
 * 其工作原理是，只要你在web.xml文件配置好要拦截的客户端请求，它都会帮你拦截到请求，此时你就可以对请求或响应(Request、Response)统一设置编码，简化操作；
 * 同时还可进行逻辑判断，如用户是否已经登陆、有没有权限访问该页面等等工作。
 * 它是随你的web应用启动而启动的，只初始化一次，以后就可以拦截相关请求，只有当你的web应用停止或重新部署的时候才销毁
 * @author Administrator
 *
 */
public class LoginFilter implements Filter{
	
	private static final Logger logger = Logger.getLogger(LoginFilter.class);
	
	private FilterConfig config = null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;   
		logger.debug("LoginFilter初始化...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 强制类型转换   
        HttpServletRequest arg0 = (HttpServletRequest)request;
        HttpServletResponse arg1 = (HttpServletResponse)response;
        // 获取web.xm设置的编码集，设置到Request、Response中     
        request.setCharacterEncoding(config.getInitParameter("charset"));
        response.setContentType(config.getInitParameter("contentType"));
        response.setCharacterEncoding(config.getInitParameter("charset"));
        // 将请求转发到目的地
        chain.doFilter(arg0, arg1);
	}

	@Override
	public void destroy() {
		logger.debug("MyCharsetFilter准备销毁...");
	}

}
