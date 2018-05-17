package com.jdbc4.jdbc.sentence;

import java.util.HashMap;
import java.util.Map;

public class SenetentManage {
	private Map<String, SentenceImpl> maps = new HashMap<String, SentenceImpl>() ;
	private static SenetentManage instance = new SenetentManage() ;
	public static SenetentManage getInstance(){
		return instance ;
	}
	public SentenceImpl create(String name){
		return maps.get(name) ;
	}
	public void register(String name, SentenceImpl impl){
		maps.put(name, impl) ;
	}
}
