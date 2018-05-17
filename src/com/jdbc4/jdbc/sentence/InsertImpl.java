package com.jdbc4.jdbc.sentence;

import java.util.HashMap;
import java.util.Map;

import com.jdbc4.util.Condition;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

public class InsertImpl extends SentenceImpl {
	public InsertImpl() {}

	@Override
	public String head() {
		return "INSERT INTO " + tableName ;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 输入Map<String, Object> maps
	 * 	输入数据来源 根据传入的实体类进行获取
	 * 输出字符串
	 */
	public String con() {
		StringBuffer columnsStr = new StringBuffer(40) ;
		StringBuffer ValuesStr = new StringBuffer(40) ;
		Map<String, Object> maps = (Map<String, Object>) param ;
		boolean sepBool = true ;
		for(Map.Entry<String, Object> item : maps.entrySet()){
			columnsStr.append(sepBool ? "" : ", ") ;
			ValuesStr.append(sepBool ? "" : ", ") ;
			sepBool = false ;
			columnsStr.append("`" + item.getKey() + "`") ;
			boolean isString = item.getValue() instanceof String ;
			ValuesStr.append(isString ? "'" : "") ;
			ValuesStr.append(item.getValue()) ;
			ValuesStr.append(isString ? "'" : "") ;
		}
		return "(" + columnsStr + ")" + " VALUES(" + ValuesStr + ")" ;
	}
	public static void main(String[] args){
		/*Map<String, Object> maps = new HashMap<String, Object>() ;
		maps.put("id", 12) ;
		maps.put("name", "cjw") ;
		
		InsertImpl impl = new InsertImpl("", maps, null) ;
		System.out.println(impl.con()) ;*/
		//测试数据无异常
	}
}
