package com.jdbc4.util;
/**
 * 
 * <b>自定义工具类<b>
 * <pre>
 * 	提供当前项目所需的快捷方法
 * </pre>
 * @author 威 
 * <br>2017年12月11日 下午6:34:08 
 *
 */
public class CustomUtil {
	//为了辨认输出的位置
	public static void print(Class<?> clazz, Object printObj){
		System.out.println(clazz.getName() + " to print:(" + printObj.toString() +")") ;
	}
}
