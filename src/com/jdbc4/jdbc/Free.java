package com.jdbc4.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * <b>释放资源<b>
 * @author 威 
 * <br>2017年6月15日 上午10:44:35 
 *
 */
public class Free {
	/**
	 * 
	 * 关闭资源 
	 * @see 
	 * @param args
	 * @throws SQLException
	 * void
	 *
	 */
	public static void free(Object... args) throws SQLException{
		for(int i=0;i<args.length;i++){
			if(args[i] instanceof ResultSet){
				((ResultSet)args[i]).close();
			}
			if(args[i] instanceof PreparedStatement){
				((PreparedStatement)args[i]).close() ;
			}
			if(args[i] instanceof Connection){
				((Connection)args[i]).close() ;
				
			}
		}
	}
}
