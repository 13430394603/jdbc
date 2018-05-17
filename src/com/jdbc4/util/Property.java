package com.jdbc4.util;
/**
 * 
 * <b>此类相当于映射文件中的一个配置项property的实体类，携带着一个配置项的所有信息<b>
 * @author 威 
 * <br>2017年12月12日 下午5:55:22 
 *
 */
public class Property {
	/**
	 * 是否为主键
	 */
	private boolean keyBool ;
	
	/**
	 * 配置项的name属性
	 */
	private String name ;
	
	/**
	 * 配置项的column属性
	 */
	private String column ;
	
	/**
	 * 配置项的数据类型属性
	 */
	private String type ;
	public boolean getKeyBool() {
		return keyBool;
	}
	public void setKeyBool(boolean keyBool) {
		this.keyBool = keyBool;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
