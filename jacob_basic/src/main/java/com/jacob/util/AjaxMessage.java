package com.jacob.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jacob.base.bean.BaseBean;

/**
 * <strong>和页面交付同意Message</strong><br/>
 * errCode=0表示成功，否则表示失败<br/>
 * msg 成功或者失败的提示信息，一般成功可以没有提示信息，由页面确定显示内容<br/>
 * data 需要在页面展示的bean对象
 * @author hulb
 *
 */
public class AjaxMessage extends BaseBean {
	private static final long serialVersionUID = 3293938436620148823L;
	private static final String NO_DATA_JSON = "{\"errCode\":%d,\"msg\":\"%s\"}"; 
	private int errCode;// 编码
	private String msg;// 结果
	private Object data;// 数据
	private Object exData;// 数据
	
	public AjaxMessage() {
		// 默认构造方法
	}
	
	public AjaxMessage(int errCode, String msg) {
		this.errCode = errCode;
		this.msg = msg;
	}
	public AjaxMessage(int errCode, String msg, Object data) {
		this.errCode = errCode;
		this.msg = msg;
		this.data = data;
	}
	
	public AjaxMessage(int errCode, Object data) {
		this.errCode = errCode;
		this.data = data;
	}
	
	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@SuppressWarnings("unchecked")
	public <E> E getData() {
		return (E) data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public static AjaxMessage create(int errCode,String msg) {
		return new AjaxMessage(errCode, msg);
	}
	public static AjaxMessage create(int errCode,Object data) {
		return new AjaxMessage(errCode, data);
	}
	public static AjaxMessage create(int errCode,String msg, Object data) {
		return new AjaxMessage(errCode, msg, data);
	}
	public static AjaxMessage success() {
		return success("操作成功!");
	}
	
	public static AjaxMessage success(String msg) {
		return create(GlobalConstant.DEFALUT_SUCCESS_CODE, msg);
	}
	public static AjaxMessage success(String msg, Object data) {
		return create(GlobalConstant.DEFALUT_SUCCESS_CODE, msg, data);
	}
	public static AjaxMessage success(Object data){
		return create(GlobalConstant.DEFALUT_SUCCESS_CODE, "", data);
	}
	public static AjaxMessage failure() {
		return failure("操作失败!");
	}
	
	public static AjaxMessage failure(String msg) {
		return create(GlobalConstant.DEFALUT_FAILURE_CODE, msg);
	}
	
	public static AjaxMessage failure(String msg, Object date) {
		return create(GlobalConstant.DEFALUT_FAILURE_CODE, msg, date);
	}
	
	public static AjaxMessage failure(Exception ex) {
		return failure("系统异常:"+ex.getMessage(),ex);
	}
	
	public static AjaxMessage failure(String message, Exception ex) {
		if(ex == null) {
			return failure();
		}
		AjaxMessage msg = failure(message);
		try {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			msg.setData(sw.toString());
		} catch (Exception e) {
		}
		return msg;
	}
	@JSONField(serialize = false)
	public boolean isSuccess() {
		return GlobalConstant.DEFALUT_SUCCESS_CODE == errCode;
	}
	public String toJsonString() {
		return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
	}
	/***/
	@JSONField(serialize = false)
	public String getNoDataJson() {
		return String.format(NO_DATA_JSON, errCode, msg);
	}

	public Object getExData() {
		return exData;
	}

	public void setExData(Object exData) {
		this.exData = exData;
	}
	
}
