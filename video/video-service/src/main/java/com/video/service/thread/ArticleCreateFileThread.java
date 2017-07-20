package com.video.service.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.video.domain.enums.ArticleSequenceEnum;
import com.video.domain.vo.ArticleVo;
import com.video.service.IThreadService;

public class ArticleCreateFileThread extends Thread {

	private ServletContext servletContext;

	public ArticleCreateFileThread(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void run() {
		// 静态文章文本信息存放位置--绝对路径
		String filePath = servletContext.getRealPath("/").replace('\\', '/') + "WEB-INF/templates/article/";
		try {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			IThreadService threadService = (IThreadService) wac.getBean("threadService");
			do {
				List<ArticleVo> listVo = threadService.findAllCreateFileArticleVo();
				for (ArticleVo vo : listVo) {
					if (null != vo.getId()) {
						ArticleVo vo2 = new ArticleVo();
						vo2.setId(vo.getId());

						FileOutputStream o = null;
						try {
							File articleFile = new File(filePath + vo.getId() + ".txt");
							if (!articleFile.exists()) {
								articleFile.createNewFile();
							}
							o = new FileOutputStream(articleFile);
							o.write(vo.getText().getBytes("UTF-8"));
							o.close();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (o != null) {
								try {
									o.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}

						vo2.setSequence(ArticleSequenceEnum.COMPLETE.getSequ());
						vo2.setStatus(1);
						threadService.updateArticle(vo2);
					}
				}
				sleep(10 * 1000);
			} while (true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
