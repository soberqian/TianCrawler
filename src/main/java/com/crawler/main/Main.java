package com.crawler.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.crawler.db.MYSQLControl;
import com.crawler.model.InfoDatabaseModel;
import com.crawler.parse.Parse;
import com.crawler.util.HttpRequestUtil;

public class Main {
	static HttpRequestUtil httpRequest = new HttpRequestUtil();
	static Builder builder = new Builder();
	static MYSQLControl control = new MYSQLControl("node1");  //配置数据库节点
	static int pages = 94;  //配置需要爬到多少页
	public static void main(String[] args) throws ParseException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String  everypageurl = "https://tianchi.aliyun.com/dataset/?spm=5176.12282016.0.0.7bba15a2ZOO4VC" ;
		//调用HttpRequest中的方法获取网页内容
		String html = httpRequest.getContentByHttpGetMethod(everypageurl,"utf-8");
		//获取x-csrf-token,用于保持会话
		String xcsrftoken = html.split("x-csrf-token': '")[1].split("'")[0];
		HttpClient httpclient = httpRequest.getHttpClient();
		for (int i = 1; i < pages; i++) {
			//获取json数据
			String getJson = getPostJson(i, xcsrftoken, httpclient);
			//解析json数据
			List<InfoDatabaseModel> listInfo = new ArrayList<InfoDatabaseModel>();
			Parse.getParseData(getJson, listInfo);
			control.insertListData(listInfo, "od_dataset");
		}
	}
	/**
	 * post提交json格式的数据
	 * 
	 * @param pageNumber 所要请求的页码数字
	 * @param xcsrftoken xcsrftoken用于保持会话,否则请求不到数据
	 * @param httpclient 实例化的httpclient
	 * @return json 
	 * */
	public static String getPostJson(int pageNumber, String xcsrftoken, HttpClient httpclient) throws UnsupportedEncodingException {
		String renRenLoginURL = "https://tianchi.aliyun.com/dataset/api/notebook/getDatasetList"; //登陆的地址
		HttpPost httpost = new HttpPost(renRenLoginURL);  //采用post方法
		httpost.setHeader("referer", "https://tianchi.aliyun.com/dataset/?spm=5176.12282016.0.0.7bba15a2ZOO4VC");
		httpost.setHeader("origin", "https://tianchi.aliyun.com");
		//随机产生User-Agent
		httpost.setHeader("User-Agent", builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)) );
		httpost.addHeader("x-csrf-token",xcsrftoken); //必须添加的头
		httpost.addHeader("content-type","application/json; charset=utf-8"); //必须添加的头
		String jsonStr  = "{\"my\":false,\"official\":false,\"status\":1,\"pageSize\":10,\"pageNum\":" + pageNumber + "}";
		StringEntity se = new StringEntity(jsonStr);
		HttpResponse response = null;
		String res = "";
		try {  
			//表单参数提交
			httpost.setEntity(se);  
			response = httpclient.execute(httpost); 
			res = EntityUtils.toString(response.getEntity());
			System.out.println("res:" + res);
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			//释放连接
			httpost.abort();  
		}  
		return res;
	}
	/**
	 * 封装请求头信息的静态类
	 */
	static class Builder{
		//设置userAgent库;读者根据需求添加更多userAgent
		String[] userAgentStrs = {"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
		            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)"};
		List<String> userAgentList = Arrays.asList(userAgentStrs);
		int userAgentSize = userAgentList.size();
	}

}
