package com.jacob.tools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 模拟浏览器请求数据
 * @author Jacob
 * 2015-5-7 16:20:01
 */
public class GetHttpInfo{
	public static String UrlRequestString(String htmlUrl) throws MyException{
        URL url;
        HttpURLConnection connection;
        // 获取输入流
        BufferedReader br;
        String line;
        StringBuilder sb = new StringBuilder();
        try {
        	url = new URL(htmlUrl);// 把字符串转换为URL请求地址
        	connection = (HttpURLConnection)url.openConnection();// 打开连接
        	connection.connect();// 连接会话
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = br.readLine()) != null) {sb.append(line);}// 循环读取流
	        br.close();// 关闭流
	        connection.disconnect();//断开连接
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MyException(e, "Java模拟浏览器请求远程数据出错");
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		try {
			System.out.println(GetHttpInfo.UrlRequestString("http://localhost:8080"));
		} catch (MyException e) {
			System.out.println(e);
		}
	}
}
