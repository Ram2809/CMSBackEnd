//package com.curriculum.util;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class DateValidator {
//	public static boolean validateDate(Date date)
//	{
//		boolean flag=false;
//		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-M-d");
//		String formattedDate=simpleDateFormat.format(date);
//		try {
//			simpleDateFormat.parse(formattedDate);
//			simpleDateFormat.setLenient(false);
//			flag=true;
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//}
