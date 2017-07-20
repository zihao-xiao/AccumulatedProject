package com.jacob.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelToo {
	
	public static String ADDRESS_PREFIX="";
	
	/**
	 * 下载文件到指定位置
	 * @param remoteFilePath
	 * @param localFilePath
	 */
	public static void downloadExcel(String remoteFilePath, String localFilePath){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try{
        	URL urlfile = new URL(remoteFilePath);
        	HttpURLConnection httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1){
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                bis.close();
                bos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
	
	/**
	 * 下载文件到指定位置
	 * @param remoteFilePath
	 * @param localFilePath
	 */
	public static void downloadFile(String remoteFilePath, String localFilePath){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try{
        	URL urlfile = new URL(remoteFilePath);
        	HttpURLConnection httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1){
                bos.write(b, 0, len);
            }
            bos.flush();
            httpUrl.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                bis.close();
                bos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
	
	/**
	 * 相对路径
	 * @param addr
	 * @param name
	 * @param cls
	 * @param list
	 */
	public static <E> File createExcel(String addr, String name, Class<E> cls,List<E> list) {
		String urlName=addr + name +".xls";
		File file = null;
		WritableWorkbook workbook=null;
		try {
			file = new File(urlName);
			workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("伊人笑财务记录表", 0);
			createLabel(sheet, getObjectFields(cls), 0);
			String[] strs = getObjectFields(cls);
			for (int i = 1; i <= list.size(); i++) {
				createLabel(sheet, getValues(list.get(i-1),strs), i);
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	
	private static <E> String[] getValues(E e,String[] strs) throws Exception{
		
		String[] st=new String[strs.length];
		Field [] fields = e.getClass().getDeclaredFields();
		for(int i=0; i< fields.length; i++){
            Field f = fields[i];
            f.setAccessible(true);
            for (int j = 0; j < strs.length; j++) {
            	if (strs[j].equals(f.getName())) {
            		st[i] = ""+f.get(e);//读取属性值
                }
           	}
        } 
		return st;
	}
	
	private static void createLabel(WritableSheet sheet,Object[] names,int lie)
			throws RowsExceededException, WriteException {
		for (int i = 0; i < names.length; i++) {
			Label label = new Label(i, lie, (String)names[i]);
			sheet.addCell(label);
		}
	}
	
	private static <E> String[] getObjectFields(Class<E> cls){
		Field[] f = cls.getDeclaredFields();
		String[] strs = new String[f.length];
		for(int i=0;i<f.length;i++){
			Field ft = f[i];
			ft.setAccessible(true);
			strs[i] = ft.getName();
		}
		return strs;
	}
}
