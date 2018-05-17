package com.jdbc4.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jdbc4.exception.NotConditionOfStatement;
import com.jdbc4.jdbc.sentence.Sentence;
import com.jdbc4.util.Condition;
import com.jdbc4.util.GetterSetter;
import com.jdbc4.util.Property;

public class UpdateAndInsertOparate extends Operate {
	public UpdateAndInsertOparate(Sentence sentence) {
		super(sentence);
	}
	
	public Map<String, Object> putParam(Class<?> clss, Object entity, List<Property> propertys){
		Map<String, Object> maps = new HashMap<String, Object>() ;
		try {
			for(Property p : propertys){ 
			if(!p.getKeyBool()){
				Method met;
					met = clss.getDeclaredMethod(GetterSetter.getInstance().toGetter(p.getName()));
				
					Object value = met.invoke(entity) ;
					maps.put(p.getColumn(), value) ;
				}	
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return maps ;
	}
	public Object getReturn (Connection conn, String sql){
		return JdbcExecute.getInstance().doExecute(conn, sql) ;
	}
}
