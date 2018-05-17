package com.jdbc4.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * <b>装载主配置文件信息<b>
 * @author 威 
 * <br>2017年12月25日 下午5:15:56 
 *
 */
public class CoreConf {
	/**
	 * key 是类名称
	 * value 是类对应的映射配置文件的路径
	 */
	private Map<String, String> mappers = new HashMap<String, String>() ;
	private String driverClass ;
	private String url ;
	private Integer poolSize ;
	private String username ;
	private String password ;
	private String addUrl ;
	public String getAddUrl() {
		return addUrl;
	}

	public void setAddUrl(String addUrl) {
		this.addUrl = addUrl;
	}

	public Map<String, String> getMappers() {
		return mappers;
	}

	public void setMappers(Map<String, String> mapper) {
		this.mappers = mapper;
	}
	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	//此方法返回的不是url而是完整的url即拼接username password addUrl
	public String getUrl() {
		String addUrlStr = addUrl == null ? "" : "&" + addUrl ;
		return url + "?user=" + username + "&password=" + password + addUrlStr ;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(Integer poolSize) {
		this.poolSize = poolSize;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
