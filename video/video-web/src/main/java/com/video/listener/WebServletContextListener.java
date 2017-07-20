package com.video.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.video.service.thread.ArticleCreateFileThread;
import com.video.service.thread.ImageThumbnailThread;
import com.video.service.thread.VideoThumbnailThread;
import com.video.service.thread.VideoTranscoderThread;

/**
 * 监听器:Web应用监听器<br>
 * Servlet的监听器Listener，它是实现了javax.servlet.ServletContextListener
 * 接口的服务器端程序，它也是随web应用的启动而启动，只初始化一次，随web应用的停止而销毁。 主要作用是：
 * 做一些初始化的内容添加工作、设置一些基本的内容、比如一些参数或者是一些固定的对象等等。
 * 
 * @author Administrator
 *
 */
public class WebServletContextListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(WebServletContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 通过这个事件可以获取整个应用的空间
		// 在整个web应用下面启动的时候做一些初始化的内容添加工作
		ServletContext sc = sce.getServletContext();
		
		//生成缩略图的图片
		ImageThumbnailThread itt1 = new ImageThumbnailThread(sc);
		itt1.start();
				
		//生成缩略图线程
		VideoThumbnailThread vtt1 = new VideoThumbnailThread(sc);
		vtt1.start();
		//生成解码的视频
		VideoTranscoderThread vtt2 = new VideoTranscoderThread(sc);
		vtt2.start();
		
		//生成解码的视频
		ArticleCreateFileThread acft = new ArticleCreateFileThread(sc);
		acft.start();
				
		logger.debug("应用监听器初始化工作完成...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		// 在整个web应用销毁之前调用，将所有应用空间所设置的内容清空
		sc.removeAttribute("dataSource");
		logger.debug("销毁工作完成...");
	}

}
