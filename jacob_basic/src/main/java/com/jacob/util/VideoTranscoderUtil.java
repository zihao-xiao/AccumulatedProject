package com.jacob.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.jacob.base.bean.TranscoderConfigure;

public class VideoTranscoderUtil {

	private final Logger logger = Logger.getLogger(getClass());

	/** 解码器配置参数*/
	private TranscoderConfigure tc;

	public void setTc(TranscoderConfigure tc) {
		this.tc = tc;
	}
	
	/** 视频文件位置 */
	private String videoPath;
	/** 解码后视频位置 */
	private String transcoderPath;
	
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
		File videoFile = new File(videoPath);
		if (!videoFile.exists() && !videoFile.isDirectory()) {
			System.out.println("Directory not exist. Create it.");
			videoFile.mkdir();
		}
	}

	public void setTranscoderPath(String transcoderPath) {
		this.transcoderPath = transcoderPath;
		File tVideoFile = new File(transcoderPath);
		if (!tVideoFile.exists() && !tVideoFile.isDirectory()) {
			System.out.println("Directory not exist. Create it.");
			tVideoFile.mkdir();
		}
	}

	/** 水印所在位置*/
	private String watermarkPath;
	
	/**判断是否为win系统*/
	private static boolean isWin=false;
	static{
		String os = System.getProperty("os.name");
		System.out.println("系统识别为："+os);
		if(os.toLowerCase().startsWith("win")){
			
			isWin=true;
		}
	}
	public VideoTranscoderUtil(TranscoderConfigure tc, String watermarkPath){
		this.tc=tc;
		this.watermarkPath=watermarkPath;
	}
	/**
	 * @param tc 解码器配置参数
	 * @param videoPath 视频文件位置 
	 * @param tVideoPath 解码后视频位置
	 * @param watermarkPath 水印位置
	 */
	public VideoTranscoderUtil(TranscoderConfigure tc, String videoPath, String transcoderPath, String watermarkPath){
		this.tc=tc;
		this.videoPath=videoPath;
		this.transcoderPath=transcoderPath;
		this.watermarkPath=watermarkPath;
		init(videoPath, transcoderPath);
	}

	// 判断文件夹是否存在--如果不存在则创建
	private static void init(String videoPath, String transcoderPath) {
		File videoFile = new File(videoPath);
		if (!videoFile.exists() && !videoFile.isDirectory()) {
			System.out.println("Directory not exist. Create it.");
			videoFile.mkdir();
		}

		File tVideoFile = new File(transcoderPath);
		if (!tVideoFile.exists() && !tVideoFile.isDirectory()) {
			System.out.println("Directory not exist. Create it.");
			tVideoFile.mkdir();
		}

	}
	
	/**
	 * @param videoName 原视频名称（含后缀）
	 * @param tVideoName 解码后的视屏名称（可含后缀名）
	 * @param watermarkName 水印名称
	 * @return
	 */
	public boolean createTranscoder(String videoName,String tVideoName,String watermarkName){

		String transcodecommand="";
		if(isWin){
			transcodecommand+="cmd /c start ";
		}
		
		transcodecommand += "ffmpeg -y -i \"" + videoPath + "/" + videoName + "\"";
		transcodecommand += " -vcodec " + tc.getVcodec() + " -b:v " + tc.getBv() + " ";
		transcodecommand += " -r "+ tc.getFramerate() + " -acodec " + tc.getAcodec() + " ";
		transcodecommand += " -b:a " + tc.getBc() + " ";
		transcodecommand += " -ar " + tc.getAr() + " ";
		if(isWin){
			transcodecommand += "-vf scale=w=" + tc.getScaleWidth() + ":h=" + tc.getScaleHeight();
		}
		//是否保留原始宽高比
		if (tc.getKeepaspectratio().equals("true") && isWin) {
			transcodecommand += ":force_original_aspect_ratio=decrease,pad=w=" + tc.getScaleWidth() + ":h=" + tc.getScaleHeight() + ":x=(ow-iw)/2:y=(oh-ih)/2" + "[aa]";
		}
		
		//是否添加水印
		if (tc.getWatermarkuse().equals("true") && isWin) {
			
			transcodecommand += ";movie=" + watermarkName + "[bb];" + "[aa][bb]" + "overlay=x=" + tc.getWatermarkWidth() + ":y=" + tc.getWatermarkHeight();
		}
		transcodecommand += " \""+ transcoderPath + "/" + tVideoName + "." + tc.getOutfmt() +"\"";
		System.out.println("FFMPEG执行命令" + transcodecommand);
		
		Process process = null;
		BufferedInputStream in = null;
		BufferedReader inBr = null;
		try {
			if(isWin){
				process = Runtime.getRuntime().exec(transcodecommand, null, new File(watermarkPath));
			}else{
				String[] str=new String[]{"sh","-c",transcodecommand};
				process = Runtime.getRuntime().exec(str, null, new File(watermarkPath));
			}
			//process = Runtime.getRuntime().exec(transcodecommand, null, watermarkFile);
			// ------------------------
			in = new BufferedInputStream(process.getInputStream());
			BufferedInputStream err = new BufferedInputStream(process.getErrorStream());
			inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(
					new InputStreamReader(err));
			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				System.out.println(lineStr);
			}
			while ((lineStr = errBr.readLine()) != null) {
				System.out.println(lineStr);
			}
			if (process.waitFor() != 0) {
				if (process.exitValue() == 1)
					System.err.println("Failed!");
			}
		} catch (IOException e) {
			logger.debug(e);
			return false;
		} catch (InterruptedException e) {
			logger.debug(e);
			return false;
		}finally{
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
		return true;
	}
	//Test
	public static void main(String[] args) {
		TranscoderConfigure tc = new TranscoderConfigure();
		VideoTranscoderUtil vtu = new VideoTranscoderUtil(tc, "D:/迅雷下载/","D:/测试解码", "D:/迅雷下载/");
		vtu.createTranscoder("123.mp4", "123", "svw.png");
	}
}
