package com.dhcc.ecm.business.util.date;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateFormate {
	
	public static String dateFormateFromString(String str){
		String dateFormate;
		SimpleDateFormat formatter;  
	    formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");  
		Date d = new Date(Long.parseLong(str));
		dateFormate = formatter.format(d);
		return dateFormate;
	}
	
	public static String dateForChineseFromString(String str){
		String replaceFirst = str.replaceFirst("-", "年");
		String replaceFirst2 = replaceFirst.replaceFirst("-", "月");
		String replaceFirst3 = replaceFirst2.replaceFirst(" ", "日 ");
		String replaceFirst4 = replaceFirst3.replaceFirst(":", "时");
		String replaceFirst5 = replaceFirst4.replaceFirst(":", "分");
		String replaceFirst6 = replaceFirst5.replaceFirst("\\.", "秒");
		String[] split = replaceFirst6.split("秒");
		String ss = split[0]+"秒";
		System.out.println("1  "+replaceFirst4+"  2  "+replaceFirst5+"  3  "+replaceFirst6+"  sub "+ss);
		return ss;
	}
	
	/**
	 * 判断文件夹是否为空，为空则创建
	 */
	public static void createFolder(String filePath) {
		try {
			if (!(new File(filePath).isDirectory())) {
				new File(filePath).mkdir();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	
	
}
