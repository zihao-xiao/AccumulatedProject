package com.video.service.thread;

import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jacob.tools.PropertiesInfo;
import com.jacob.util.VideoThumbnailUtil;
import com.video.domain.enums.VideoSequenceEnum;
import com.video.domain.vo.VideoVo;
import com.video.service.IThreadService;
import com.video.service.IVideoService;

public class VideoThumbnailThread extends Thread {
	/** 视频文件位置 */
	private static String sourcePath;
	/** 缩率图放置位置 */
	private static String imagePath;
	/** 获取服务器域名和项目名称*/
	private static String webUrl;
	//转码从什么时间开始
	private static final int TIME_SS=10;
	
	static{
		Properties prop = PropertiesInfo.getProperties("conf/configure.properties");
		sourcePath = prop.getProperty("video.sourcePath");
		imagePath = prop.getProperty("video.imagePath");
		webUrl = prop.getProperty("session.webUrl");
	}
	
	private ServletContext servletContext;
	public VideoThumbnailThread(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void run(){
		// 服务器 绝对路径
		String realPath = servletContext.getRealPath("/").replace('\\', '/');
		VideoThumbnailUtil vtu = new VideoThumbnailUtil();
		try {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			IThreadService threadService = (IThreadService)wac.getBean("threadService"); 
			do {
				List<VideoVo> listVo = threadService.findAllThumbnailVideo();
				for (VideoVo vo : listVo) {
					vtu.setVideoPath(realPath + sourcePath + vo.getId());
					vtu.setImagePath(realPath + imagePath + vo.getId());
					boolean bool = vtu.createThumbnails(vo.getVideoName(), vo.getId()+".png", TIME_SS);
					
					if(bool && null!=vo.getId()){
						VideoVo vo2 = new VideoVo();
						vo2.setId(vo.getId());
						vo2.setThumbnailsPath(webUrl + imagePath + vo.getId() + "/" + vo.getId()+".png");
						vo2.setSequence(VideoSequenceEnum.TRANSCODER.getSequ());
						threadService.updateVideo(vo2);
					}else{
						System.out.println("视频解码生成缩略图出错："+realPath + sourcePath + vo.getId() + "/" + vo.getVideoName());
					}
				}
				sleep(10 * 1000);
			} while(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
