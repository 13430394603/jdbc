package com.jdbc4.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc4.pool.SqlPool;
import com.mysql.jdbc.PreparedStatement;
/**
 * 
 * <b>数据库操作<b>
 * @author 威 
 * <br>2017年12月12日 下午6:01:29 
 *
 */
public class JdbcExecute {
	private static JdbcExecute exe = new JdbcExecute() ;
	public static JdbcExecute getInstance(){
		return exe ;
	}
	/**
	 * 
	 * @see <p>MethodName： 		doExecute 
	 * @see <br>Description: 	基本的数据库操作语 
	 * @param sqlStatement 		sql语句
	 * @return
	 * boolean
	 *
	 */
	public boolean doExecute(Connection conn, String sqlStatement){
		Statement stmt = null ;
		try {
			stmt = conn.createStatement() ;
			stmt.executeUpdate(sqlStatement) ;
			return true ;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			System.out.println(sqlStatement) ;
			try {
				Free.free(stmt) ;
			} catch (SQLException e) {
				e.printStackTrace() ;
			}
		}
		return false ;
	}
	/**
	 * 
	 * 执行特定的sql语句返回一个ResultSet对象  
	 * @see
	 * <pre>
	 * 	if ResultSet中的第一个next方法为false则返null
	 * </pre>
	 * @param sqlStatement
	 * @return
	 * ResultSet
	 *
	 */
	public ResultSet doExecuteQuery(Connection conn, String sqlStatement){
		Statement stmt = null ;
		try {
			stmt = conn.createStatement() ;
			return stmt.executeQuery(sqlStatement) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			System.out.println(sqlStatement) ;
			try {
				Free.free(stmt) ;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static void main(String[] args) throws SQLException{
		/*PreparedStatement ps = (PreparedStatement) SqlPool.getInstance().getConnection().prepareStatement("INSERT INTO managefiletype(id, type) VALUES(?, ?)") ;
		ps.setInt(1, 12) ;
		ps.setString(2, "suiyi") ;
		ps.executeUpdate() ;*/
		//PreparedStatement ps1 = (PreparedStatement) SqlPool.getInstance().getConnection().prepareStatement("UPDATE managefiletype SET type = ? WHERE id = ?") ;
		/*ps1.setString(1, "随意") ;
		ps1.setInt(2, 12) ;
		
		ps1.executeUpdate() ;*/
	}
	//为PreparedStatement 预编译执行做准备
	public PreparedStatement prepare(Object[] objArray, PreparedStatement ps) throws SQLException{
		int len = objArray.length ;
		for(int i = 0; i < len; i++){
			Object obj = objArray[i] ;
			if(obj == null)
				break ;
			if(obj instanceof Integer){
				ps.setInt(i + 1, (Integer) obj) ;
				continue ;
			}
			ps.setString(i + 1, (String) obj) ;
		}
		return ps ;
	}
}
