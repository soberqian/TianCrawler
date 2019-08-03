package com.crawler.model;

import java.util.Date;

/**
 * 数据表对应的字段
 * */
public class InfoDatabaseModel {
	private String id;
	private String bussiness_key;
	private String cata_title;
	private String cata_url;
	private String org_name;
	private String group_name;
	private String industry;
	private Date create_time;
	private Date update_time;
	private String description;
	private String open_type;
	private String cata_tags;
	private String conf_catalog_format;
	private String data_count;
	private String file_count;
	private int status;
	private String data_from_dept;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBussiness_key() {
		return bussiness_key;
	}
	public void setBussiness_key(String bussiness_key) {
		this.bussiness_key = bussiness_key;
	}
	public String getCata_title() {
		return cata_title;
	}
	public void setCata_title(String cata_title) {
		this.cata_title = cata_title;
	}
	public String getCata_url() {
		return cata_url;
	}
	public void setCata_url(String cata_url) {
		this.cata_url = cata_url;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOpen_type() {
		return open_type;
	}
	public void setOpen_type(String open_type) {
		this.open_type = open_type;
	}
	public String getCata_tags() {
		return cata_tags;
	}
	public void setCata_tags(String cata_tags) {
		this.cata_tags = cata_tags;
	}
	public String getConf_catalog_format() {
		return conf_catalog_format;
	}
	public void setConf_catalog_format(String conf_catalog_format) {
		this.conf_catalog_format = conf_catalog_format;
	}
	public String getData_count() {
		return data_count;
	}
	public void setData_count(String data_count) {
		this.data_count = data_count;
	}
	public String getFile_count() {
		return file_count;
	}
	public void setFile_count(String file_count) {
		this.file_count = file_count;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getData_from_dept() {
		return data_from_dept;
	}
	public void setData_from_dept(String data_from_dept) {
		this.data_from_dept = data_from_dept;
	}
	
	
}
