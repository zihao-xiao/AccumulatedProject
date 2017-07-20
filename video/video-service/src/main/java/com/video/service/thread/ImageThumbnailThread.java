package com.video.service.thread;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jacob.tools.PropertiesInfo;
import com.jacob.util.ImageUtils;
import com.video.domain.enums.ImageSequenceEnum;
import com.video.domain.vo.ImageVo;
import com.video.service.IImageService;
import com.video.service.IThreadService;

public class ImageThumbnailThread extends Thread {
	/** 缩率图放置位置 */
	private static String thumbnailsPath;
	/** 获取服务器域名和项目名称*/
	private static String webUrl;

	static{
		Properties prop = PropertiesInfo.getProperties("conf/configure.properties");
		thumbnailsPath = prop.getProperty("image.thumbnailsPath");
		webUrl = prop.getProperty("session.webUrl");
	}
	
	private ServletContext servletContext;
	public ImageThumbnailThread(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void run(){
		// 服务器 绝对路径
		String realPath = servletContext.getRealPath("/").replace('\\', '/');
		try {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			IThreadService threadService = (IThreadService)wac.getBean("threadService"); 
			do {
				List<ImageVo> listVo = threadService.findAllThumbnailImage();
				for (ImageVo vo : listVo) {
					if(null!=vo.getId()){
						ImageVo vo2 = new ImageVo();
						vo2.setId(vo.getId());
						String[] reals = vo.getRealPath().split(";");
						
						File thumbnailsFile = new File(realPath + thumbnailsPath + vo.getId());
						if (!thumbnailsFile.exists() && !thumbnailsFile.isDirectory()) {
							System.out.println("Directory not exist. Create it.");
							thumbnailsFile.mkdirs();
						}
						
						for (int i = 0; i < reals.length; i++) {
							String imageRealPath=realPath + thumbnailsPath + vo.getId()+ "/" + (i+1) + ".png";
							ImageUtils.scale(reals[i], imageRealPath, 5, false);
							
							String imageThumbnailsPath = webUrl + thumbnailsPath + vo.getId() + "/" + (i+1) + ".png";
							
							if(vo2.getThumbnailsPath()!=null){
								vo2.setThumbnailsPath(vo2.getThumbnailsPath()+";"+imageThumbnailsPath);
							}else{
								vo2.setThumbnailsPath(imageThumbnailsPath);
							}
						}
						
						vo2.setSequence(ImageSequenceEnum.COMPLETE.getSequ());
						vo2.setStatus(1);
						threadService.updateVideo(vo2);
					}
				}
				sleep(10 * 1000);
			} while(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
