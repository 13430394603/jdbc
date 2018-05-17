package com.jdbc4.tests;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc4.exception.MappingFileError;
import com.jdbc4.exception.NotConditionOfStatement;
import com.jdbc4.jdbc.JdbcExecute;
import com.jdbc4.test.mapping.FileType;
import com.jdbc4.session.Configuration;
import com.jdbc4.session.PgSessionFactory;
import com.jdbc4.session.Session;
import com.jdbc4.session.SessionFactory;
import com.jdbc4.util.Condition;
/**
 * 
 * <b>该库的调试类<b>
 * @see 测试derby数据库正常使用库操作数据库无异常
 * <br>
 * @author 威 
 * <br>2017年6月30日 下午6:05:32 
 *
 */
public class Test {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException, NotConditionOfStatement, MappingFileError{
		Session session = PgSessionFactory.openSession() ;
		FileType filetype = new FileType() ;
		filetype.setId(12) ;
		filetype.setType("随意") ;
		session.save(filetype) ;
		Condition cd = new Condition() ;
		cd.put("type", "随意") ;
		//session.delete(filetype) ;
		//JdbcExecute j = new JdbcExecute() ;
		//j.doExecute(com.chen.pool.SqlPool.getInstance().getConnection(), "IF EXISTS managefiletype") ;
		/*Configuration conf = new Configuration() ;
		conf.configure("cfg2.xml") ;
		SessionFactory sff = conf.buildSessionFactory() ;
		Session s1 = sff.openSession() ;*/
		
	}
}
