package com.jacob.base.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义信息异常
 * @author jacob
 *	2015年7月3日14:10:56
 */
public class MyException extends Exception{

	private static final long serialVersionUID = 4519290541289742456L;
	
	/**
	 * 异常信息描述
	 */
	private String errorMessage = "";
	
	/**
	 * 异常信息类型
	 */
	private static List<String> errorType = null;
	
	/**
	 * 自定义信息异常
	 */
	public MyException(Throwable value,String messageValue){
		super(value);
		errorMessage = messageValue;
	}
	
	/**
	 * 自定义信息异常
	 * @param messageValue 错误描述
	 */
	public MyException(String messageValue){
		super(messageValue);
	}
	
	/**
	 * 自定义信息异常
	 * @param value
	 * @param messageValue 错误描述
	 * @param messageType 错误类型
	 */
	public MyException(Throwable value,String messageValue,String messageType){
		super(value);
		if(errorType == null){
			errorType = new ArrayList<String>();
		}
		errorMessage = messageValue;
		errorType.add(messageType);
	}
	
	/**
	 * 自定义信息异常
	 * @param value
	 * @param messageValue 错误描述
	 * @param messageType 错误类型集合
	 */
	public MyException(Throwable value,String messageValue,List<String> messageType){
		super(value);
		if(errorType == null){
			errorType = new ArrayList<String>();
		}
		errorMessage = messageValue;
		errorType = messageType;
	}

	/**
	 * 异常信息
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 异常信息
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getErrorType() {
		return errorType;
	}
	
	/**
	 * 清理错误类型
	 */
	public void clearErrorType(){
		errorType.clear();
	}
}
