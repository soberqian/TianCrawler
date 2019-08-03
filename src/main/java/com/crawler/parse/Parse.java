package com.crawler.parse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.crawler.model.InfoDatabaseModel;
import com.crawler.util.TimeUtils;

public class Parse {
	/**
	 * 解析数据
	 * 封装数据
	 * 这里使用fastjson工具进行解析
	 */
	public static void getParseData(String json, List<InfoDatabaseModel> listPro) {
		JSONObject jsonfile = JSONObject.parseObject(json
				, Feature.OrderedField);
		//解析collection下的所有的新闻id,这里需要调用parseArray()方法
		String jsonArray = 
				JSONObject.parseObject(jsonfile.get("data").toString()).get("list").toString();
		JSONArray jarr = JSONArray.parseArray(jsonArray);
		for (Iterator<?> iterator = jarr.iterator(); iterator.hasNext();) {
			JSONObject oneDataJson = (JSONObject)iterator.next();
			JSONObject datalabJ = JSONObject.parseObject(oneDataJson.get("datalab").toString()
					, Feature.OrderedField);
			String bussiness_key = UUID.randomUUID().toString();
			String cata_title = datalabJ.get("title").toString();
			String cata_url = "https://tianchi.aliyun.com/dataset/dataDetail?dataId=" + datalabJ.get("id").toString();
			Date create_time =  TimeUtils.parseTime(datalabJ.get("gmtCreate").toString());
			Date update_time =  TimeUtils.parseTime(datalabJ.get("gmtCreate").toString());
			String description =  datalabJ.get("description").toString();
			//封装数据入集合
			InfoDatabaseModel model = new InfoDatabaseModel();
			model.setBussiness_key(bussiness_key);
			model.setCata_title(cata_title);
			model.setCata_url(cata_url);
			model.setCreate_time(create_time);
			model.setUpdate_time(update_time);
			model.setDescription(description);
			model.setOrg_name("阿里云天池");
			model.setGroup_name("其他");
			model.setIndustry("人工智能");
			model.setOpen_type("需注册");
			model.setCata_tags("其他");
			model.setConf_catalog_format("其他");
			model.setData_count("/");
			model.setFile_count("/");
			model.setStatus(1);
			model.setData_from_dept("阿里云天池");
			listPro.add(model);
		}
	}
	 /**
     * 解析JsonArray数据
     * 
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> parseArray(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
