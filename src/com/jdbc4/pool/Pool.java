package com.jdbc4.pool;

import java.sql.Connection;
/**
 * 
 * <b>连接池接口<b>
 * @author 威 
 * <br>2017年12月12日 下午12:50:29 
 *
 */
public interface Pool {
	public Connection getConnection() ;
	public int length() ;
}
