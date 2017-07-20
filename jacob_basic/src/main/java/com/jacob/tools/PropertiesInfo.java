package com.jacob.tools;

import java.io.IOException;   
import java.io.InputStream;   
import java.util.Properties;
 
public class PropertiesInfo {
	
	/**
	 * @param keyName 键名
	 * @return 键值对的值
	 */
	public static String getValue(String keyName){
		return getValue(keyName,"file.properties");
	}
	
	/**
	 * @param keyName 键名
	 * @param fileName properties文件的名称
	 * @return 键值对的值
	 */
	public static String getValue(String keyName,String fileName){
		Properties pro = null;
		InputStream proIn =null;
		try {
			proIn = PropertiesInfo.class.getClassLoader().getResourceAsStream(fileName);   
			pro = new Properties();
			pro.load(proIn);
		} catch (IOException e) {
			System.out.println("获取数据失败");
		}
		return (String)pro.get(keyName);
	}
	
	/**
	 * @return 键值对对象
	 */
	public static Properties getProperties(String fileName){
		Properties pro = null;
		InputStream proIn =null;
		try {
			proIn = PropertiesInfo.class.getClassLoader().getResourceAsStream(fileName);   
			pro = new Properties();
			pro.load(proIn);
		} catch (IOException e) {
			System.out.println("获取数据失败");
		}
		return pro;
	}
} 
