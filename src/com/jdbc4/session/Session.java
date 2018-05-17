package com.jdbc4.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.jdbc4.jdbc.Free;
import com.jdbc4.jdbc.SqlFactory;
import com.jdbc4.util.Condition;

public class Session {
	private Connection conn ;
	private SessionFactory sf ;
	public Session(Connection conn, SessionFactory sf){
		this.conn = conn ;
		this.sf = sf ;
	}
	public void save(Object entity){
		SqlFactory.save(entity) ;
		//SqlFactory.save(entity, conn, sf) ;
	}
	public Object get(Class<?> cls, Object id){
		return SqlFactory.get(cls, id) ;
	} 
	public List<Object> get(Class<?> cls, Condition cd){
		return SqlFactory.get(cls, cd) ;
	}
	public List<Object> get(Class<?> cls) {
		return  SqlFactory.get(cls) ;
	}
	public void update(Object entity){
		SqlFactory.update(entity) ;
	}
	public void update(Object entity, Condition cdt){
		SqlFactory.update(entity, cdt) ;
	}
	public void delete (Object entity){
		SqlFactory.delete(entity) ;
	}
	public void delete (Object entity, Condition cdt){
		SqlFactory.delete(entity, cdt) ;
	}
	public void close (){
		try {
			Free.free(conn) ;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
