package com.video.service.thread;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jacob.base.bean.TranscoderConfigure;
import com.jacob.tools.PropertiesInfo;
import com.jacob.util.VideoTranscoderUtil;
import com.video.domain.enums.VideoSequenceEnum;
import com.video.domain.vo.VideoVo;
import com.video.service.IThreadService;
import com.video.service.IVideoService;

/**
 * 视频解码
 * 
 * @author jacob 2017年6月29日13:34:26
 */
public class VideoTranscoderThread extends Thread {

	/** 视频文件位置 */
	private static String sourcePath;
	/** 解码后视频位置 */
	private static String transcoderPath;
	/** 水印所在位置*/
	private static String watermarkPath;
	/** 获取服务器域名和项目名称*/
	private static String webUrl;
	private static final String LOGO_NAME="logo.png";
	static{
		Properties prop = PropertiesInfo.getProperties("conf/configure.properties");
		sourcePath = prop.getProperty("video.sourcePath");
		transcoderPath = prop.getProperty("video.transcoderPath");
		watermarkPath = prop.getProperty("video.watermarkPath");
		webUrl = prop.getProperty("session.webUrl");
	}
	
	private ServletContext servletContext;
	public VideoTranscoderThread(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void run() {
		String webPath=servletContext.getRealPath("/").replace('\\', '/');
		
		TranscoderConfigure tc = new TranscoderConfigure();
		
		VideoTranscoderUtil vt = new VideoTranscoderUtil(tc, webPath + watermarkPath);
		try {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
			IThreadService threadService = (IThreadService)wac.getBean("threadService"); 
			do {
				//Map<String, String> map = videoService.getVideoConfigure();
				//tc.setBv(map.get("t_bv"));
				vt.setTc(tc);
				List<VideoVo> listVo = threadService.findAllTranscoderVideo();
				
				for (VideoVo vo : listVo) {

					vt.setVideoPath(webPath + sourcePath + vo.getId());
					vt.setTranscoderPath(webPath + transcoderPath + vo.getId());
					boolean bool = vt.createTranscoder(vo.getVideoName(), vo.getId(), LOGO_NAME);
					
					if(bool){
						VideoVo vo2 = new VideoVo();
						vo2.setId(vo.getId());
						vo2.setCompilePath(webUrl+transcoderPath+vo.getId()+"/"+vo.getId()+"."+tc.getOutfmt());
						vo2.setSequence(VideoSequenceEnum.COMPLETE.getSequ());
						vo2.setStatus(1);//是否显示
						threadService.updateVideo(vo2);
					}else{
						System.out.println("视频解码出错："+sourcePath+ vo.getId() + "/" + vo.getVideoName());
					}
				}
				
				sleep(10 * 1000);
			} while (true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println();
	}
}
