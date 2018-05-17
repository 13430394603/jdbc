package com.jdbc4.jdbc.sentence;

import java.util.Map;

import com.jdbc4.util.Condition;

public abstract class SentenceImpl implements Cloneable {
	private Condition condition ;
	public String tableName ;
	public Object param ;
	public abstract String head() ;
	public abstract String con() ;
	/**
	 * 
	 * 一句话描述该构造方法
	 * @param tableName 	表名
	 * @param param			列数据 有两种，分别是查询语句中的list集合类型和插入修改语句中的Map类型
	 * @param condition		代表sql的条件
	 *
	 */
	public SentenceImpl(){}
	public void setInit(String tableName, Object param, Condition condition){
		this.condition = condition ;
		this.param = param ;
		this.tableName  = tableName ;
	}
	public String bom() {
		if(condition == null)
			return "" ;
		StringBuffer temp = new StringBuffer(50) ;
		boolean separatorBool = true ; //分隔符的判断
		for (Map.Entry<String, Object> cdt : condition.getSet()){
			temp.append(separatorBool ? "" : " AND " ) ;
			separatorBool = false ;
			temp.append("`"+cdt.getKey() + "` = ") ;
			temp.append(cdt.getValue() instanceof Integer ? "" : "'") ;   //对字符串字符添加上引号
			temp.append(cdt.getValue()) ;
			temp.append(cdt.getValue() instanceof Integer ? "" : "'") ;
		}
		condition.release() ;
		return " WHERE " + temp.toString().trim() ;
	}
	public SentenceImpl createClone() {
		SentenceImpl impl = null ;
		try {
			impl = (SentenceImpl) clone() ;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return impl;
	}
}
