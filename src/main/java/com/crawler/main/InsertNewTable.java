package com.crawler.main;

import com.crawler.db.MYSQLControl;

public class InsertNewTable {
	static MYSQLControl control = new MYSQLControl("node1");  //配置数据库节点
	public static void main(String[] args) {
		String sql = " insert ignore into od_datasource (id,url,bussiness_key,create_time) select id, cata_url,bussiness_key,create_time from od_dataset "; 
		control.executeUpdate(sql);
	}
}
