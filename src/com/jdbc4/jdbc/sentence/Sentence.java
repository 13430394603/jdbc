package com.jdbc4.jdbc.sentence;

import java.util.HashMap;
import java.util.Map;

import com.jdbc4.util.Condition;

public class Sentence {
	private SentenceImpl impl ;
	public Sentence(SentenceImpl impl){
		this.impl = impl ;
	}
	public String getSqlSentence(String tableName, Object param, Condition condition){
		impl.setInit(tableName, param, condition) ;
		return impl.head() + impl.con() + impl.bom() ;
	}
	public static void main(String[] args){
		Map<String, Object> maps = new HashMap<String, Object>() ;
		maps.put("id", 12) ;
		maps.put("name", "cjw") ;
		Condition cd = new Condition() ;
		cd.put("id", 12) ;  
		
		SentenceImpl impl = new InsertImpl() ;
		Sentence s = new Sentence(impl) ;
		System.out.println(s.getSqlSentence("student", maps, null)) ;
		impl = new InsertImpl() ;
		s = new Sentence(impl) ;
		System.out.println(s.getSqlSentence("student", maps, cd)) ;
		
		impl = new UpdateImpl() ;
		s = new Sentence(impl) ;
		System.out.println(s.getSqlSentence("student", maps, null)) ;
		Condition cd1 = new Condition() ;
		cd1.put("id", 12) ; 
		impl = new UpdateImpl() ;
		s = new Sentence(impl) ;
		System.out.println(s.getSqlSentence("student", maps, cd1)) ;
		
		impl = new DeleteImpl() ;
		s = new Sentence(impl) ;
		System.out.println(s.getSqlSentence("student", maps, null)) ;
		Condition cd2 = new Condition() ;
		cd2.put("id", 12) ; 
		impl = new DeleteImpl() ;
		s = new Sentence(impl) ;
		System.out.println(s.getSqlSentence("student", maps, cd2)) ;
		
	}
}
