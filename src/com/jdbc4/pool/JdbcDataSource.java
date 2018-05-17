package com.jdbc4.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import com.jdbc4.conf.ConfigurePool;

/**
 * 
 * <b>JDBC连接池，存储连接对象<b>
 * @author 威 
 * <br>2017年6月14日 上午10:31:11
 *
 */
public class JdbcDataSource extends AbstractDataSource{
	/**
	 * 本类方法描述：
	 * 
	 * 配置connection对象
	 * 
	 * addConn			当连接池剩余连接数等于一时，增加五个连接数量
	 * 
	 * length			在控制台中输出可连接数量以及可获取整形连接数量
	 * 
	 * getConnection	*获取Connection连接对象
	 * 					*当Connection对象执行close方法时将对象回
	 * 					   收至listConnection连接池中
	 */
	/**
	 * 实例化LinkedList对象，将用于存储连接对象
	 * @see 此对象便于插入和删除因此适合本类连接对象的存储
	 */
	private static LinkedList<Connection> listConnection = new LinkedList<Connection>() ;
	private static String url ;
	private static String driver ;
	private static Integer poolSize ;
	/**
	 * 私有构造方法 改为公开
	 * 池应该是可以多开的，为创建他的独立对象应该只有一个
	 */
	public JdbcDataSource(String url, String driver, Integer poolSize){
		JdbcDataSource.poolSize = poolSize ;
		JdbcDataSource.url = url ;
		JdbcDataSource.driver = driver ;
		init() ;
	}
	//配置connection对象
	public static void init(){
		System.out.println("连接池初始化...") ;
		try{
			Class.forName(driver) ;
			System.out.println(driver + " 驱动加载完成") ;
			for(int i = 0;i < poolSize; i++){
				Connection conn = DriverManager.getConnection(url) ;
				listConnection.add(conn) ;
			}
			System.out.println("连接池创建完毕（当前可供连接数量为" + poolSize + "）") ;
		}catch(Exception e){
			System.out.println("连接池创建失败") ;
			e.printStackTrace() ;
		}
	}
	
	/**
	 * 
	 * 增加连接池中的连接数量
	 * @see 当连接池剩余连接数等于一时，增加五个连接数量 <br>
	 * void
	 *
	 */
	public void addConn(){
		try {
			for(int i = 0;i < 5; i++){
				Connection conn = DriverManager.getConnection(url) ;
				
				listConnection.add(conn) ;
			}
		}catch (SQLException e) {e.printStackTrace();}
	}
	/**
	 * 
	 * 获取连接池中连接对象，并打印于控制台 
	 * @see
	 * @return
	 * int
	 *
	 */
	public int length(){
		return listConnection.size() ;
	}
	/**
	 * 获取Connection连接对象
	 * @see 当Connection对象执行close方法时将对象回收至listConnection连接池中
	 */
	@Override
	public Connection getConnection() throws SQLException {
		if(listConnection.size() > 0){
			//获取第一个对象后删除其在链表中的对象
			final Connection conn = listConnection.removeFirst() ;
			//连接数量不足时增加连接数量
			if(length() <= 1){
				System.out.println("连接数量不足，添加...") ;
				addConn() ;
				System.out.println("添加完毕（当前可供连接数量为" + length() + "）") ;
			}
			//返回代理对像
			return (Connection) Proxy.newProxyInstance(JdbcDataSource.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler(){
				public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
					if(!method.getName().equals("close")){
						//当执行
						return method.invoke(conn, args) ;
					}else{
						listConnection.add(conn) ;
						return null ;
					}
				}
			});
		}else{
			throw new RuntimeException("数据库忙") ;
		}
	}
}