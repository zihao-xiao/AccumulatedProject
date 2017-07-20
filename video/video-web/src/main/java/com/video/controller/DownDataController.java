package com.video.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jacob.tools.PropertiesInfo;

@Controller
@RequestMapping("/downData")
public class DownDataController {
	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * @return
	 */
	@RequestMapping(value = { "index", "/down_data" })
	public String downData() {
		logger.debug("进入登录页面页面");
		return "page/uploadDown/downData";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/down_image" })
	public void downImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "uid") String uuid, @RequestParam(value = "name") String name) {
		try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// 文件保存路径
		Properties prop = PropertiesInfo.getProperties("conf/configure.properties");
		// 图片存放位置
		String sourcePath = prop.getProperty("image.sourcePath");
		// 文件位置
		String fileName = request.getSession().getServletContext().getRealPath("/") + sourcePath + uuid + "/" + name;

		// 读取文件
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(fileName);
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int b;
			while ((b = in.read(buffer)) != -1) {
				out.write(buffer, 0, b);  
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
