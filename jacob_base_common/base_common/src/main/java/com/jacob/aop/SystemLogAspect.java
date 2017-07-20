package com.jacob.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jacob.base.annoTable.SystemControllerLog;
import com.jacob.base.entity.Log;
import com.jacob.dao.ILogDao;
import com.jacob.service.aspect.LogService;

/**
 * spring aop<br>
 * service,controller层日志记录
 * @author Administrator
 * 2016年10月8日16:16:09
 */
@Aspect
@Component
public class SystemLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	@Resource
	private LogService logService;
	/**
	 * 添加service层业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.video.service.*.*(..))")
	public void serviceAspect() {

	}

	/**
	 * 添加controller层业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.video.controller.*.*(..))")
	public void controllerAspect() {

	}

	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// HttpSession session = request.getSession();
		// 请求的IP
		String ip = request.getRemoteAddr();
		try {
			// *========控制台输出=========*//
			System.out.println("=====前置通知开始=====");
			System.out.println("请求方法:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));
			System.out.println("请求IP:" + ip);
			
			// *========数据库日志=========*//
			Log log = new Log();
			log.setContent(getControllerMethodDescription(joinPoint));
			log.setCreated(new Date());
			// 保存数据库
			logService.saveLog(log);
			System.out.println("=====前置通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).desc();
					break;
				}
			}
		}
		return description;
	}
}
