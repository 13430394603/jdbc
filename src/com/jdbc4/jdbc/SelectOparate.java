package com.jdbc4.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jdbc4.jdbc.sentence.Sentence;
import com.jdbc4.util.GetterSetter;
import com.jdbc4.util.Property;

public class SelectOparate extends Operate {

	public SelectOparate(Sentence sentence) {
		super(sentence) ; 
	}
	public Object putParam(Class<?> clss, Object entity, List<Property> propertys){
		return null ;
	}
	public Object getReturn (Class<?> clss, Connection conn, String sql){
		Object obj ;
		try {
			obj = clss.newInstance() ;
			ResultSet set = JdbcExecute.getInstance().doExecuteQuery(conn, sql) ;
			while(set.next()){
				//将查询到的结果装载到实体类中
				for(Property p : mapper.getPropertys()){
					Method metGet = clss.getDeclaredMethod(GetterSetter.getInstance().toGetter(p.getName())) ;
					Method metSet = clss.getDeclaredMethod(GetterSetter.getInstance().toSetter(p.getName()), metGet.getReturnType()) ;
					if(p.getType().equals("string"))
						metSet.invoke(obj, set.getString(p.getColumn())) ;
					else
						metSet.invoke(obj, set.getInt(p.getColumn())) ;
				}
				//主键的唯一性，因此只返回一个结果
				return obj ;
			}
			
			set.close() ;
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException e) {
			e.printStackTrace();
		}
		return null ;
	}
}
