package com.jdbc4.jdbc.sentence;

import java.util.HashMap;
import java.util.Map;

import com.jdbc4.util.Condition;

public class UpdateImpl extends SentenceImpl {
	
	public UpdateImpl() {}

	@Override
	public String head() {
		return "UPDATE " + tableName ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String con() {
		StringBuffer updatesStr = new StringBuffer(40) ; 
		Map<String, Object> maps = (Map<String, Object>) param ;
		boolean sepBool = true ;
		for(Map.Entry<String, Object> item : maps.entrySet()){
			boolean isString = item.getValue() instanceof String ;
			updatesStr.append(sepBool ? "" : ",") ;
			updatesStr.append("`" + item.getKey() + "` = ") ;
			updatesStr.append(isString ? "'" : "") ;
			updatesStr.append(item.getValue()) ;
			updatesStr.append(isString ? "'" : "") ;
			sepBool = false ;
		}
		return " SET " + updatesStr.toString().trim() ;
	}
	public static void main(String[] args){
		/*Map<String, Object> maps = new HashMap<String, Object>() ;
		maps.put("id", 12) ;
		maps.put("name", "cjw") ;
		
		UpdateImpl impl = new UpdateImpl("", maps, null) ;
		System.out.println(impl.con()) ;*/
		//测试数据无异常
	}
}
