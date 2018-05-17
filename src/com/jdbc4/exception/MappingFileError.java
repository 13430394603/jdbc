package com.jdbc4.exception;
/**
 * 
 * <b>映射文件异常或者不存在<b>
 * <pre>
 * 异常分析：
 * 	不存在配置文件
 * 	存在配置文件 
 * 	配置文件名没有和类名称对应
 * 	
 * </pre>
 * @author 威 
 * <br>2017年12月12日 下午9:53:24 
 *
 */
public class MappingFileError extends Exception {
	private static final long serialVersionUID = 1L;
	public MappingFileError(String msg){
		super(msg) ;
	}
}
