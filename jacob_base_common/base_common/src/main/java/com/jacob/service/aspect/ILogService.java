package com.jacob.service.aspect;

import org.springframework.transaction.annotation.Transactional;

import com.jacob.base.entity.Log;

public interface ILogService {
	
	/** 
     * 日志记录 
     * @param log 
     */  
    @Transactional  
    public void saveLog(Log log);  
}
