
# 项目简介(Introduction)

本项目是为上海某公司所写，主要目的是采集可供使用的开源数据集(数据挖掘算法)，并用于构建数据搜索引擎。


工程下面，共有5个packages：

* (1) db:封装了数据库操作的一系列方法，有兴趣的读者可以学习我的另外一个数据库项目https://github.com/soberqian/MySQLUtils<br />
* (2) main:工程的主方法<br />
* (3)model：用于定义所要采集的数据 <br />
* (4)parse：针对请求的数据，该项目中的类负责解析，封装数据入集合。由于该网页使用的是JSON数据，所以这里选择fastjson进行解析<br />
* (5)util：HttpRequestUtil是一个可以通过的类，用户可以使用该类轻松构建一个网络爬虫，其类的作用是请求网页，并获取响应内容。TimeUtils也是一个通用的类，其功能对时间日期的进行转化<br />

# 项目特点
## POST提交JSON数据
在进行搜索/表单提交等时，网站使用的方法多为**POST方法**。而POST方法提交的数据的方式有两种：<br />
* (1) 采用键值对提交。<br />
* (2) 以JSON字符串的方式进行提交。<br />
这里可参考我的博客：https://qianyang-hfut.blog.csdn.net/article/details/98312856

采用键值对提交的例子如京东的模拟登陆：<br />
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190803103453425.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9xaWFueWFuZy1oZnV0LmJsb2cuY3Nkbi5uZXQ=,size_16,color_FFFFFF,t_70#pic_center)

采用POST提交的JSON字符串的案例便是本项目对应的网站：阿里天池。<br />
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190804075535389.png)

## x-csrf-token解决
使用网络抓包分析请求页面，发现**请求头**中，存在**x-csrf-token**字段。如下图所示：<br />
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190804075708139.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9xaWFueWFuZy1oZnV0LmJsb2cuY3Nkbi5uZXQ=,size_16,color_FFFFFF,t_70)

在编写程序时，如果**没有在请求头中添加该字段，会出现403状态码**。其x-csrf-token字段的功能是保持会话。<br />
为此，我们需要首先请求网页的首页，并解析得到x-csrf-token的字段值，之后添加到请求头中，请求下面的页面。<br />
主要对应的程序如下：<br />
```java
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
```
另外，在上述程序中，需要注意的是请求头中，还必须添加content-type字段，因为POST提交的数据类型为JSON。<br />
## 随机切换User Agent
为防止网络爬虫被封，这添加了User Agent，以便随机切换。如下程序所示：<br />
```java
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
```

## 简单的数据库操作
MYSQLControl类是我在DbUtils包上面，二次开发的，其主要目的是简化数据库操作。其支持数据表创建，数据插入，数据查询等一系列基本操作。<br />
读者可以通过本人的github项目进行学习该类的详细使用：https://github.com/soberqian/MySQLUtils。<br />
另外本人的博客，也有详细介绍：https://qianyang-hfut.blog.csdn.net/article/details/96821348。

由于MYSQLControl类支持XML配置多节点数据库，本项目也使用到了。即XML的第一个数据库节点：
```XML
		<node1>
			<nodeName>node1</nodeName>
			<url>jdbc:mysql://127.0.0.1:3306/csdncourse</url>
			<username>root</username>
			<password>112233</password>
		</node1>
```


