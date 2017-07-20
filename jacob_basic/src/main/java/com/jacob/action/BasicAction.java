package com.jacob.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author 肖琦
 * 
 */
public class BasicAction {
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 输出图片共用方法
	 * 
	 * @param bytes
	 *            图片2进制
	 * @throws IOException
	 */
	public void outImage(HttpServletResponse response, byte[] bytes){
		InputStream buffin = new ByteArrayInputStream((byte[]) bytes);
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		try {
			BufferedImage img = ImageIO.read(buffin);
			// 将图像输出到Servlet输出流中。
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write(img, "jpeg", sos);
			sos.close();
		} catch (IOException e) {
			logger.debug("文件下载失败");
		}
	}

	/**
	 * 输出excel表文件共用方法
	 * @param response
	 * @param file
	 * @throws IOException
	 */
	public void outExcel(HttpServletResponse response, File file,String name){
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Content-Disposition", "attachment;filename='"+name+".xls'");
		response.setContentType("application/msexcel;charset=GBK");
		response.setContentLength((int)file.length());
		
		byte[] buffer = new byte[4096];// 缓冲区
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = -1;
			// 遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); // 不可少
			response.flushBuffer();// 不可少
		} catch (IOException e) {
			logger.debug("文件下载失败");
		} finally {
			try {
				// 关闭流，不可少
				if (input != null)
					input.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				logger.debug("关闭流失败");
			}
		}
	}

	

}
