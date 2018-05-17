package com.jdbc4.util;

import java.util.List;
/**
 * 
 * <b>映射文件的实体类，携带着一个映射文件的所有信息<b>
 * @author 威 
 * <br>2017年12月12日 下午5:58:05 
 *
 */
public class MappingConf {
	/**
	 * 映射文件所对应的实体类的类路径
	 */
	private String classPath ;
	
	/**
	 * sql表名
	 */
	private String tableName ;
	
	/**
	 * Property实体类集
	 */
	private List<Property> propertys ;
	public String getClassPath() {
		return classPath;
	}
	
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public List<Property> getPropertys() {
		return propertys;
	}
	
	public void setPropertys(List<Property> propertys) {
		this.propertys = propertys;
	}
}
