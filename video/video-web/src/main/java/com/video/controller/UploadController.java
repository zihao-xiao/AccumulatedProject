package com.video.controller;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jacob.tools.PropertiesInfo;
import com.video.domain.enums.ImageSequenceEnum;
import com.video.domain.enums.VideoSequenceEnum;
import com.video.domain.vo.ImageVo;
import com.video.domain.vo.VideoVo;
import com.video.service.IImageService;
import com.video.service.IVideoService;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private final Logger logger = Logger.getLogger(getClass());
	@Resource private IVideoService videoService;
	@Resource private IImageService imageService;
	
	/**
	 * @return
	 */
	@RequestMapping(value={"/", "/index"})
	public String uploadData(){
		logger.debug("进入上传页面");
		return "page/uploadDown/uploadData";
	}
	
	/**
	 * 上传视频
	 * @param file
	 * @param titleName
	 * @param remark
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/upload_video"})
	public String uploadVideo(@RequestParam(value="uploadfile") MultipartFile file, 
			String titleName, @RequestParam(required=false)String remark,
			HttpServletRequest request, HttpServletResponse response){
		String uuid="";
		if (!file.isEmpty()) {
			// 文件保存路径  
			Properties prop = PropertiesInfo.getProperties("conf/configure.properties");
			// 图片存放位置
			String sourcePath = prop.getProperty("video.sourcePath");
		    // 服务器域名 www.jacob.com
			String webUrl = prop.getProperty("session.webUrl");
			// 服务器 绝对路径
			String realPath = request.getSession().getServletContext().getRealPath("/");
			// id
			uuid = UUID.randomUUID().toString().replace("-", "");
			
			//是否有指定文件夹
			File realSourceFile = new File(realPath + sourcePath + uuid);
			if (!realSourceFile.exists() && !realSourceFile.isDirectory()) {
				System.out.println("Directory not exist. Create it.");
				realSourceFile.mkdirs();
			}
			
			VideoVo vo = new VideoVo();
			String videoName = file.getOriginalFilename();
            vo.setVideoName(videoName);
            vo.setId(uuid);
            vo.setVideoTitle(titleName); 
            vo.setVideoDesc(remark);
            vo.setCreateTime(new Date());
            
			try {  
				//物理地址
				String videoRealPath = realPath + sourcePath + uuid + "/" + videoName;
				// 转存文件  
                file.transferTo(new File(videoRealPath));
                vo.setRealPath(videoRealPath);
                
				//服务器地址
                String videoSourcePath=webUrl + sourcePath + uuid + "/" + videoName;
				vo.setSourcePath(videoSourcePath);
                
            } catch (Exception e) {  
                e.printStackTrace();  
            }
			vo.setSequence(VideoSequenceEnum.THUMBNAIL.getSequ());
            vo.setStatus(0);//不展示到客户端--》》当生成缩率图--》》视频解码后可显示
            videoService.addVideo(vo);
        }     
		return "redirect:/video/index?vid="+uuid;
	}
	
	/**
	 * 单图上传
	 * @param file
	 * @param titleName
	 * @param remark
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/upload_image"})
	public String uploadImage(@RequestParam(value="uploadfile") MultipartFile file, 
			String titleName, @RequestParam(required=false)String remark,
			HttpServletRequest request, HttpServletResponse response){
		String uuid="";
		if (!file.isEmpty()) {
			
			// 文件保存路径  
			Properties prop = PropertiesInfo.getProperties("conf/configure.properties");
			// 图片存放位置
			String sourcePath = prop.getProperty("image.sourcePath");
		    // 服务器域名 www.jacob.com
			String webUrl = prop.getProperty("session.webUrl");
			// 服务器 绝对路径
			String realPath = request.getSession().getServletContext().getRealPath("/");
			// id
			uuid = UUID.randomUUID().toString().replace("-", "");
			
			File realSourceFile = new File(realPath + sourcePath + uuid);
			if (!realSourceFile.exists() && !realSourceFile.isDirectory()) {
				System.out.println("Directory not exist. Create it.");
				realSourceFile.mkdirs();
			}
		
			ImageVo vo = new ImageVo();
			
			String imageName = file.getOriginalFilename();
	        vo.setImgName(imageName);
	        vo.setId(uuid);
            vo.setImgTitle(titleName); 
            vo.setImgDesc(remark);
            vo.setCreatedTime(new Date());
            vo.setImgNum(1);
            
			try {
				//物理地址
				String imageRealPath = realPath + sourcePath + uuid + "/" + imageName;
				// 转存文件  
                file.transferTo(new File(imageRealPath));
                vo.setRealPath(imageRealPath);
                
                //服务器地址
                String imageSourcePath=webUrl + sourcePath + uuid + "/" + imageName;
				vo.setSourcesPath(imageSourcePath);
                vo.setSequence(ImageSequenceEnum.THUMBNAIL.getSequ());
                vo.setStatus(0);//不展示到客户端--》》当生成缩率图
                
                imageService.addImage(vo);
                
            } catch (Exception e) {  
                e.printStackTrace();
            }
        }
		return "redirect:/image/index?vid="+uuid;
	}
	
	/**
	 * 多图上传
	 * @param file
	 * @param titleName
	 * @param remark
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/upload_images"})
	public String uploadImages(@RequestParam(value="uploadfile") MultipartFile[] file, 
			String titleName, @RequestParam(required=false)String remark,
			HttpServletRequest request, HttpServletResponse response){
		String uuid="";
		if (file.length>=1) {
			// 文件保存路径  
			Properties prop = PropertiesInfo.getProperties("conf/configure.properties");
			// 图片存放位置
			String sourcePath = prop.getProperty("image.sourcePath");
		    // 服务器域名 www.jacob.com
			String webUrl = prop.getProperty("session.webUrl");
			// 服务器 绝对路径
			String realPath = request.getSession().getServletContext().getRealPath("/");
			// id
			uuid = UUID.randomUUID().toString().replace("-", "");
			
			File realSourceFile = new File(realPath + sourcePath + uuid);
			if (!realSourceFile.exists() && !realSourceFile.isDirectory()) {
				System.out.println("Directory not exist. Create it.");
				realSourceFile.mkdirs();
			}
			
			ImageVo vo = new ImageVo();
			String imageName = file[0].getOriginalFilename();
            vo.setImgName(imageName);
            vo.setId(uuid);
            vo.setImgTitle(titleName); 
            vo.setImgDesc(remark);
            vo.setCreatedTime(new Date());
    		vo.setImgNum(file.length);
    		
            for (int i = 0; i < file.length; i++) {
            	if(!file[i].isEmpty()){
	    			try {
	    				//物理地址
	    				String imageRealPath = realPath + sourcePath + uuid + "/" + file[i].getOriginalFilename();
	    				// 转存文件  
	                    file[i].transferTo(new File(imageRealPath));
	                    if(vo.getRealPath()!=null){
	                    	vo.setRealPath(vo.getRealPath()+";"+imageRealPath);
	                    }else{
	                    	vo.setRealPath(imageRealPath);
	                    }
	                    
	                    //服务器地址
	                    String imageSourcesPath=webUrl + sourcePath + uuid + "/" + file[i].getOriginalFilename();
	                    if(vo.getSourcesPath()!=null){
	                    	vo.setSourcesPath(vo.getSourcesPath()+";"+imageSourcesPath);
	                    }else{
	                    	vo.setSourcesPath(imageSourcesPath);
	                    }
	                    
	                } catch (Exception e) {  
	                    e.printStackTrace();
	                }
            	}
			}
            
            vo.setSequence(ImageSequenceEnum.THUMBNAIL.getSequ());
            vo.setStatus(0);//不展示到客户端--》》当生成缩率图
            imageService.addImage(vo);
        }     
		return "redirect:/image/index?vid="+uuid;
	}
}
