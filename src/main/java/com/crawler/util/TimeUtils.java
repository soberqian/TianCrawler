package com.crawler.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * 
 * 时间工具类
 * @author: Yang Qian
 * @url:https://qianyang-hfut.blog.csdn.net/article/details/80273740
 */
public class TimeUtils {

	public static void main( String[] args ) throws ParseException{
		List<String> monthlist = TimeUtils.YearMonth(2017,2018);
		for (int i = 0; i < monthlist.size(); i++) {
			System.out.println(monthlist.get(i));
		}
		String time = getMonth("2002-1-08 14:50:38");
		System.out.println(time);
		System.out.println(getDay("2002-1-08 14:50:38"));
		System.out.println(TimeUtils.parseTime("2016-05-19 19:17","yyyy-MM-dd HH:mm"));
		String data=getNowMonth();
		System.out.println(data);
	}
	//获取当前时间
	public static String GetNowDate(String formate){  
		String temp_str="";  
		Date dt = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat(formate);  
		temp_str=sdf.format(dt);  
		return temp_str;  
	}  
	//获取当前月
	public static String getMonth( String time ){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {

			date = sdf.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sdf.format(date);

	}
	//获取当前日期
	public static String getDay( String time ){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {

			date = sdf.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sdf.format(date);

	}
	//输入时间，解析成"yyyy-MM-dd HH:mm:ss"格式
	public static Date parseTime(String inputTime){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date = null;
		try {
			date = sdf.parse(inputTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return date;

	}
	//将时间转成成字符型
	public static String dateToString(Date date, String type) { 
		DateFormat df = new SimpleDateFormat(type);  
		return df.format(date);  
	}
	//将输入的时间，转化成指定格式
	public static Date parseTime(String inputTime, String timeFormat) throws ParseException{

		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);  
		Date date = sdf.parse(inputTime); 

		return date;

	}
	
	public static Calendar parseTimeToCal(String inputTime, String timeFormat) throws ParseException{

		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);  
		Date date = sdf.parse(inputTime); 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;

	}

	public static int getDaysBetweenCals(Calendar cal1, Calendar cal2) throws ParseException{

		return (int) ((cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*24*3600));

	}
	//长整型转化为时间
	public static Date parseTime(long inputTime){

		//  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date= new Date(inputTime);
		return date;

	}

	public static String parseTimeString(long inputTime){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date= new Date(inputTime);
		return sdf.format(date);

	}
	public static String parseStringTime(String inputTime){

		String date=null;
		try {
			Date date1 = new SimpleDateFormat("yyyyMMddHHmmss").parse(inputTime);
			date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}
	public static List<String> YearMonth(int year) {
		List<String> yearmouthlist=new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			DecimalFormat dfInt=new DecimalFormat("00");
			String sInt = dfInt.format(i);
			yearmouthlist.add(year+sInt);
		}

		return yearmouthlist;
	} 
	//获取从起始年份到目标年份所有的月
	public static List<String> YearMonth(int startyear,int finistyear) {
		List<String> yearmouthlist=new ArrayList<String>();
		for (int i = startyear; i < finistyear+1; i++) {
			for (int j = 1; j < 13; j++) {
				DecimalFormat dfInt=new DecimalFormat("00");
				String sInt = dfInt.format(j);
				yearmouthlist.add(i +""+sInt);
			}
		}
		return yearmouthlist;
	} 
	public static List<String> TOAllDay(int year){
		List<String> daylist=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		int m=1;//月份计数 
		while (m<13) 
		{ 
			int month=m; 
			Calendar cal=Calendar.getInstance();//获得当前日期对象 
			cal.clear();//清除信息 
			cal.set(Calendar.YEAR,year); 
			cal.set(Calendar.MONTH,month-1);//1月从0开始 
			cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天  

			System.out.println("##########___" + sdf.format(cal.getTime())); 

			int count=cal.getActualMaximum(Calendar.DAY_OF_MONTH); 

			System.out.println("$$$$$$$$$$________" + count); 

			for (int j=0;j<=(count - 2);) 
			{ 
				cal.add(Calendar.DAY_OF_MONTH,+1); 
				j++; 
				daylist.add(sdf.format(cal.getTime()));
			} 
			m++; 
		} 
		return daylist;
	}
	//获取昨天的日期
	public static String getyesterday(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		return yesterday;
	}
	//获取当前年份月份
	public static String getNowMonth(){
		Calendar   cal   =   Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);    
        int month = cal.get(Calendar.MONTH) + 1; 
        DecimalFormat dfInt=new DecimalFormat("00");
        String sInt = dfInt.format(month);
		return year+""+sInt;
	}
}
