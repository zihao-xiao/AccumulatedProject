package com.jacob.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jacob.base.bean.PageVo;

public class ServletToo {
	
	private static final Logger logger = Logger.getLogger(ServletToo.class);
	
	@SuppressWarnings("unchecked")
	public static String requestToString(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("path=").append(request.getRequestURI()).append(",");
		sb.append("ip=").append(getIp(request)).append(",");
		sb.append("method=").append(request.getMethod()).append(",");
		sb.append("Header=[");
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String name = headers.nextElement();
			String value = request.getHeader(name);
			try {
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e);
			}
			sb.append(name).append("=").append(value)
					.append(",");
		}
		sb.append("],");
		sb.append("Parameter=[");
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			sb.append(name).append("=").append(request.getParameter(name))
					.append(",");
		}
		sb.append("],");
		Cookie[] cooies = request.getCookies();
		if(null!=cooies){
			sb.append("Cookie=[");
			for(int i=0; i<cooies.length; i++){
				String value = cooies[i].getValue();
				try {
					value = URLDecoder.decode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e);
				}
				sb.append(cooies[i].getName() + "=" + value + " (" +cooies[i].getDomain() + cooies[i].getPath() + "," + cooies[i].getMaxAge() + "),");
			}
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
	

	/**
	 * IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip.contains(",")){
			String[] ips = ip.split(",");
			for(String s : ips){
				s = s.trim();
				if(!s.equals("unknown")){
					ip = s;
					break;
				}
			}
		}
		return ip;
	}

	public static <E> PageVo<E> getPage(HttpServletRequest request, Class<E> cls) {
		PageVo<E> page = new PageVo<E>();
		int currentPage = 1;
		int pageSize = 20;
		String currentPageStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("size");
		if (null != currentPageStr) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		if (null != pageSizeStr) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		page.setPage(currentPage);
		page.setSize(pageSize);
		String allCountsStr = request.getParameter("count");
		if (null != allCountsStr) {
			page.setAllCounts(Integer.parseInt(allCountsStr));
		}
		String order = request.getParameter("order");
		if (StringUtils.isNotBlank(order)) {
			page.setOrder(order);
		}
		return page;
	}
}
