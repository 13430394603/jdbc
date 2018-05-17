package com.jdbc4.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;

import com.jdbc4.jdbc.JdbcExecute;
import com.jdbc4.pool.SqlPool;

/**
 * 
 * <b>数据库操作调度的中枢类<b>
 * @author 威 
 * <br>2017年6月15日 上午10:31:01 
 *
 */
public class ExecuteFactory {
	private static JdbcExecute exe = new JdbcExecute() ;
	public static boolean doExe(Connection conn, String sql){
		return exe.doExecute(conn, sql) ;
	}
	public static ResultSet doExeQuerys(Connection conn,String sql){
		return exe.doExecuteQuery(conn, sql) ;
	}
}
