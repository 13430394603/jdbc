package com.jdbc4.pool;

import java.sql.Connection;
import java.sql.SQLException;

import com.jdbc4.conf.ConfigurePool;
/**
 * 
 * <b>连接池对象，继承Pool接口<b>
 * @author 威 
 * <br>2017年12月12日 下午1:09:40 
 *
 */
public class SqlPool implements Pool{
	private JdbcDataSource dataSource ;
	public SqlPool(String url, String driver, Integer poolSize){
		dataSource = new JdbcDataSource(url, driver, poolSize) ;
	}
	@Override
	public Connection getConnection() {
		try {
			return dataSource.getConnection() ;
		} catch (SQLException e) {
			System.out.println("获取连接异常");
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public int length() {
		return dataSource.length() ;
	}

}
