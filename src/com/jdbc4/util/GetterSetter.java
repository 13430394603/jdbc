package com.jdbc4.util;

public class GetterSetter {
	private static GetterSetter instance = new GetterSetter() ;
	public static GetterSetter getInstance(){
		return instance ;
	}
	/**
	 * 
	 * 传入一个属性名称，并将传入的值转换成标准的setter方法 
	 * @see
	 * @param word
	 * @return
	 * String
	 *
	 */
	public String toSetter(String word){
		return "set" + word.substring(0, 1).toUpperCase() + word.substring(1) ; 
	}
	/**
	 * 
	 * 闯入一个属性名称 并将值转换成标准的getter方法 
	 * @see
	 * @param word
	 * @return
	 * String
	 *
	 */
	public String toGetter(String word){
		return "get" + word.substring(0, 1).toUpperCase() + word.substring(1) ; 
	}
}
