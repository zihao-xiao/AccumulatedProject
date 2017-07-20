package com.jacob.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileToo {
	private static Logger logger = Logger.getLogger(FileToo.class);
	
	/**
	 * 图片文件转二进制流文件
	 * 
	 * @return 转换后的对象
	 * @throws Exception
	 * @throws IOException
	 */
	public static byte[] readPicture(File fileData){
		if (fileData != null) {
			long fileSize = fileData.length();
			if (fileSize > Integer.MAX_VALUE) {
				logger.debug("file too big...");return null;
			}
			FileInputStream fi=null;
			try {
				fi = new FileInputStream(fileData);
				byte[] buffer = new byte[(int) fileSize];
				int offset = 0;
				int numRead = 0;
				while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
					offset += numRead;
				}
				// 确保所有数据均被读取
				if (offset != buffer.length) {
					logger.debug("Could not completely read file " + fileData.getName());
				}
				return buffer;
			} catch (FileNotFoundException e) {
				logger.debug("文件不存在");
			} catch (IOException e) {
				logger.debug("生成流失败");
			}finally {
				try {
					fi.close();
				} catch (IOException e) {
					logger.debug("关闭流失败");
				}
			}
		}
		return null;
	}
}
