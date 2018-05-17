package com.jdbc4.jdbc;

import java.util.Map;

import com.jdbc4.util.Condition;

public class StatementFactory {
	private String SELECT_STATEMENT = "SELECT #columns# FROM #tableName#" ;
	private String UPDATE_STATEMENT = "UPDATE #tableName# SET #updates#" ;
	private String DELETE_STATEMENT = "DELETE FROM #tableName#" ;	
	private String INSERT_STATEMENT = "INSERT INTO #tableName#(#column#) VALUES(#values#)" ;
	private static StatementFactory instance = new StatementFactory() ;
	public static StatementFactory getInstance(){
		return instance ;
	}
	//	生成sql语句 查询语句
	public String getSelectStatement(String tableName, String columns){
		String temp = SELECT_STATEMENT ;
		temp = temp.replace("#columns#", columns) ;
		temp = temp.replace("#tableName#", tableName) ;
		return temp ;
	}
	public String getSelectStatement(String tableName, String columns, Condition condition){
		return getSelectStatement(tableName, columns) + getConditionStatement(condition) ;
	}
	public String getConditionStatement(Condition condition){
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
	//用于PreparedStatement对象访问数据库时拼接条件语句
	public String getConditionStatement2(Condition condition){
		if(condition == null)
			return "" ;
		StringBuffer temp = new StringBuffer(50) ;
		boolean separatorBool = true ; //分隔符的判断
		for (String key : condition.getKeys()){
			temp.append(separatorBool ? "" : " AND " ) ;
			separatorBool = false ;
			temp.append("`"+ key + "` = ") ;
			temp.append("?") ;
		}
		condition.release() ;
		return " WHERE " + temp.toString().trim() ;
	}
	//获取修改语句  
	private String getUpdateStatement(String tableName, String updates){
		String temp = UPDATE_STATEMENT ;
		temp = temp.replace("#tableName#", tableName) ;
		temp = temp.replace("#updates#", updates) ;
		return temp ;
	}
	public String getUpdateStatement(String tableName, String updates, Condition condition){
		return getUpdateStatement(tableName, updates) + getConditionStatement(condition) ;
	}
	//获取删除语句
	private String getDeleteStatement(String tableName) {
		//DELETE_STATEMENT
		String temp = DELETE_STATEMENT ;
		temp = temp.replace("#tableName#", tableName) ;
		return temp ;
	}
	public String getDeleteStatement(String tableName, Condition condition) {
		return getDeleteStatement(tableName) + getConditionStatement(condition) ;
	}
	//获取插入语句
	public String getInsertStatement(String tableName, String columns, String values){
		String temp = INSERT_STATEMENT ;
		temp = temp.replace("#tableName#", tableName) ;
		temp = temp.replace("#column#", columns) ;
		temp = temp.replace("#values#", values) ;
		return temp ;
	}
	public static void main(String[] args){
		Condition cd = new Condition() ;
		cd.put("id", 1) ;
		cd.put("name", "xiaoming") ;
		//System.out.println(new StatementFactory().getSelectStatement("student", cd, "id", "name", "age")) ;
	}
	
	
}
