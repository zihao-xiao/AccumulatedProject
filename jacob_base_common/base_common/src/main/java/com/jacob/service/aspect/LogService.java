package com.jacob.service.aspect;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jacob.base.entity.Log;
import com.jacob.dao.ILogDao;

@Service
public class LogService implements ILogService {
	
	@Resource private ILogDao logDao;
	
	@Override
	public void saveLog(Log log) {
		System.out.println("A_Log:==>"+log.toString());
		Log log1 = logDao.getEntityById(Log.class, 1);
		System.out.println("B_Log:==>"+log1.toString());
	}

}
