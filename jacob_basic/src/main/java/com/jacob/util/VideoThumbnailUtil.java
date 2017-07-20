package com.jacob.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * 视频获取缩略图
 * 
 * @author jacob 2017年6月25日14:51:20
 */
public class VideoThumbnailUtil {
	private final Logger logger = Logger.getLogger(getClass());
	/** 视频文件位置 */
	private String videoPath;
	/** 缩率图放置位置 */
	private String imagePath;
	
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
		File videoFile = new File(videoPath);
		if (!videoFile.exists() && !videoFile.isDirectory()) {
			System.out.println("Directory not exist. Create it.");
			videoFile.mkdirs();
		}
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
		File imageFile = new File(imagePath);
		if (!imageFile.exists() && !imageFile.isDirectory()) {
			System.out.println("Directory not exist. Create it.");
			imageFile.mkdirs();
		}
	}

	/**判断是否为win系统*/
	private static boolean isWin=false;
	static{
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){
			isWin=true;
		}
	}
	public VideoThumbnailUtil(){}
	
	/**
	 * @param videoFile
	 *            视频文件位置
	 * @param imageFile
	 *            缩率图放置位置
	 */
	public VideoThumbnailUtil(String videoPath, String imagePath) {
		this.videoPath = videoPath;
		this.imagePath = imagePath;
		init(videoPath, imagePath);
	}

	// 判断文件夹是否存在--如果不存在则创建
	private static void init(String videoPath, String imagePath) {
		File videoFile = new File(videoPath);
		if (!videoFile.exists() && !videoFile.isDirectory()) {
			System.out.println("Directory not exist. Create it.");
			videoFile.mkdirs();
		}

		File imageFile = new File(imagePath);
		if (!imageFile.exists() && !imageFile.isDirectory()) {
			System.out.println("Directory not exist. Create it.");
			imageFile.mkdirs();
		}
	}

	/**
	 * @param videoName
	 *            视频文件名称
	 * @param imageName
	 *            缩率图名称
	 * @param ss
	 *            转码时从什么时间开始的
	 * @return
	 */
	public boolean createThumbnails(String videoName, String imageName, int ss) {
		
		String thumbnailcommand="";
		if(isWin){
			thumbnailcommand+="cmd /c start ";
		}
		
		thumbnailcommand += "ffmpeg -y -i " + "\""
				+ videoPath + "/" + videoName + "\"" + " -ss " + ss
				+ " -s 220x110 -f image2 -vframes 1 " + "\"" + imagePath + "/"
				+ imageName + "\"";
		System.out.println("FFMPEG执行命令" + thumbnailcommand);
		Process process = null;
		BufferedInputStream in = null;
		BufferedReader inBr = null;
		try {
			if(isWin){
				process = Runtime.getRuntime().exec(thumbnailcommand);
			}else{
				String[] str=new String[]{"sh","-c",thumbnailcommand};
				process = Runtime.getRuntime().exec(str);
			}
			in = new BufferedInputStream(process.getInputStream());
			inBr = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				System.out.println(lineStr);
			}
			if (process.waitFor() != 0) {
				if (process.exitValue() == 1)
					System.err.println("Failed!");
			}
			return true;
		} catch (IOException e) {
			logger.debug(e);
			return false;
		} catch (InterruptedException e) {
			logger.debug(e);
			return false;
		} finally {
			if (inBr != null)
				try {
					inBr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (process != null){
				try {
					process.getErrorStream().close();  
					process.getInputStream().close();  
					process.getOutputStream().close(); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	//Test
	public static void main(String[] args) {
		VideoThumbnailUtil vtu = new VideoThumbnailUtil("D:/迅雷下载/","D:/image");
		System.out.println(vtu.createThumbnails("123.mp4", "123.jpg", 100));
		
	}
}
