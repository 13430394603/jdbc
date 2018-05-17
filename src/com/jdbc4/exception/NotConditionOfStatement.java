package com.jdbc4.exception;
/**
 * 
 * <b>当一个语句必须有条件语句而当前程序因为某些原因却未能提供的时候此时会抛出此异常<b>
 * @author 威 
 * <br>2017年12月12日 下午5:43:50 
 *
 */
public class NotConditionOfStatement extends Exception {
	private static final long serialVersionUID = 1L;
	 public NotConditionOfStatement(String msg){  
	 	super(msg) ;  
	 }  
}
