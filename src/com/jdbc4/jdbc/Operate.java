package com.jdbc4.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.jdbc4.conf.conf.AnalyMapperConf;
import com.jdbc4.exception.NotConditionOfStatement;
import com.jdbc4.jdbc.sentence.Sentence;
import com.jdbc4.session.SessionFactory;
import com.jdbc4.util.Condition;
import com.jdbc4.util.GetterSetter;
import com.jdbc4.util.MappingConf;
import com.jdbc4.util.Property;

public class Operate {
	public MappingConf mapper ;
	public Sentence sentence ;
	public Operate(Sentence sentence){
		this.sentence = sentence ;
	}
	public Object doExe(Class<?> clss, Connection conn, SessionFactory sf, Condition cd){
		return getReturn (clss, conn, putSqlSentence(clss, sf, clss.getSimpleName(), cd)) ;
	}
	public Object doExe(Object entity, Connection conn, SessionFactory sf, Condition cd){
		return getReturn (conn, putSqlSentence(entity, sf, entity.getClass().getSimpleName(), cd)) ;
	}
	public String putSqlSentence(Object param1, SessionFactory sf, String className, Condition cd){
		String mapPath = sf.getMapperMap(className) ;
		AnalyMapperConf conf = new AnalyMapperConf() ;
		conf.configure(mapPath) ;
		mapper = (MappingConf) conf.getConfObject() ;
		Class<?> clss = null ;
		try {
			clss = Class.forName(mapper.getClassPath());
		} catch (ClassNotFoundException e) {
			e.printStackTrace() ;
		}
		if(cd == null)
			cd = putCondition(clss, param1, mapper.getPropertys()) ;
		Object maps = putParam(clss, param1, mapper.getPropertys()) ;
		String sql = sentence.getSqlSentence(mapper.getTableName(), maps, cd) ;
		return sql ;
	}
	public Object getReturn (Connection conn, String sql){
		return null ;
	}
	public Object getReturn (Class<?> clss, Connection conn, String sql){
		return null ;
	}
	public Condition putCondition(Class<?> clss, Object entity, List<Property> propertys){
		Condition cd = new Condition() ;
		try{
			for(Property p : propertys){
				if(p.getKeyBool()){
					Method met = clss.getDeclaredMethod(GetterSetter.getInstance().toGetter(p.getName())) ;
					Object value = met.invoke(entity) ;
					if(value != null){
						if(p.getType().equals("integer"))
							cd.put(p.getColumn(), (Integer) value) ;
						cd.put(p.getColumn(), value) ;
						break ;
					}
					throw new NotConditionOfStatement("执行delete操作时没有充足的条件") ;
				}
			}
		} catch ( IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NotConditionOfStatement | IllegalAccessException e) {
			e.printStackTrace();
		}
		return cd ;
	}
	public Object putParam(Class<?> clss, Object entity, List<Property> propertys){
		return null ;
	}
}
