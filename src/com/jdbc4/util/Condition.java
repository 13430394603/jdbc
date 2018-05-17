package com.jdbc4.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Condition {
	private Map<String, Object> cdtMap = null ;
	public Condition(){
		cdtMap = new HashMap<String, Object>() ;
	}
	/**
	 * 
	 * 获取一个条件集   set
	 * @see
	 * @return
	 * Map<String,Object>
	 *
	 */
	public Set<Entry<String, Object>> getSet(){
		return cdtMap.entrySet() ;
	}
	public Set<String> getKeys(){
		return cdtMap.keySet() ;
	}
	public Collection<Object> getValues(){
		return cdtMap.values() ;
	}
	
	public void put (String key, Object value){
		cdtMap.put(key, value) ;
	}
	public void get (String key){
		cdtMap.get(key) ;
	}
	/**
	 * 
	 * 释放 
	 * @see
	 * void
	 *
	 */
	public void release(){
		cdtMap = null ;
	}
}
