package com.jdbc4.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc4.conf.AnalysisConf;
import com.jdbc4.exception.MappingFileError;
import com.jdbc4.util.Condition;
import com.jdbc4.util.GetterSetter;
import com.jdbc4.util.MappingConf;
import com.jdbc4.util.Property;

public class SqlSelect {
	private static SqlSelect instance = new SqlSelect() ;
	public static SqlSelect getInstance(){
		return instance ;
	}
	/**
	 * 
	 * 查询 根据主键查找返回一个实体对象    根据条件对象查找返回集合 
	 * @see
	 * @param cls
	 * @param condition
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * Object
	 * @throws MappingFileError 
	 *
	 */
	public Object select(Class<?> cls, Object id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, MappingFileError{
		//根据类名获取相应的映射文件实体类mapping对象
		MappingConf mapping = AnalysisConf.getInstance().getMapping(cls.getClass().getSimpleName()) ;
		//获取该类
		Class<?> clss = Class.forName(mapping.getClassPath()) ;
		//实例化该类
		Object obj = clss.newInstance() ;
		
		//将主键设置为查询的条件
		Condition cd ;
		cd = new Condition() ;
		for(Property p : mapping.getPropertys()){
			if(p.getKeyBool())
				cd.put(p.getColumn(), id) ;
		}	
		
		//获取对应sql语句
		String sql = StatementFactory.getInstance().getSelectStatement(mapping.getTableName(), _select(mapping), cd) ;
		//执行 
		ResultSet set = ExecuteFactory.doExeQuerys(sql) ;
		while(set.next()){
			//将查询到的结果装载到实体类中
			for(Property p : mapping.getPropertys()){
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
		//用完释放
		mapping = null ;
		return null ;
	} 
	/**
	 * 
	 * 查询 
	 * @see
	 * @param cls
	 * @param cd
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 * @throws MappingFileError
	 * List<Object>
	 *
	 */
	public List<Object> select(Class<?> cls, Condition cd) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException, MappingFileError{
		//根据类名获取相应的映射文件实体类mapping对象
		MappingConf mapping = AnalysisConf.getInstance().getMapping(cls.getClass().getSimpleName()) ;
		Class<?> clss = Class.forName(mapping.getClassPath()) ;
		
		//获取相应的SQL语句
		String sql = StatementFactory.getInstance().getSelectStatement(mapping.getTableName(), _select(mapping), cd) ;
		//执行
		ResultSet set = ExecuteFactory.doExeQuerys(sql) ;
		List<Object> lists = new ArrayList<Object>() ; 
		Object obj ;
		while(set.next()){
			//反射实例化
			obj = clss.newInstance() ;
			for(Property p : mapping.getPropertys()){
				//通过反射获取setter、getter方法    并通过setter的对应getter方法获取返回类型做为setter的参数类型
				Method metGet = clss.getDeclaredMethod(GetterSetter.getInstance().toGetter(p.getName())) ;
				Method metSet = clss.getDeclaredMethod(GetterSetter.getInstance().toSetter(p.getName()), metGet.getReturnType()) ;
				//通过反射调用
				if(p.getType().equals("string"))
					metSet.invoke(obj, set.getString(p.getColumn())) ;
				else
					metSet.invoke(obj, set.getInt(p.getColumn())) ;
			}
			//将所有查询结果实体类装到集合中
			lists.add(obj) ;
		}
		set.close() ;
		//用完释放
		mapping = null ; 
		return lists ;		
	}
	/**
	 * 
	 * 查询
	 * @see
	 * @param cls
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 * @throws MappingFileError
	 * List<Object>
	 *
	 */
	public List<Object> get(Class<?> cls) throws ClassNotFoundException,
		InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException,
				SQLException, MappingFileError{
		//根据类名获取相应的映射文件实体类mapping对象
		MappingConf mapping = AnalysisConf.getInstance().getMapping(cls.getClass().getSimpleName()) ;
		//载入该类
		Class<?> clss = Class.forName(mapping.getClassPath()) ;
		Object obj ;
		//获取对应的SQL语句
		String sql = StatementFactory.getInstance().getSelectStatement(mapping.getTableName(), _select(mapping)) ;
		//执行SQL操作
		ResultSet set = ExecuteFactory.doExeQuerys(sql) ;
		//装取SQL的集合对象，给定数量为30
		List<Object> lists = new ArrayList<Object>(30) ;
		while(set.next()){
			//反射实例化实体类对象
			obj = clss.newInstance() ;
			for(Property p : mapping.getPropertys()){
				Method metGet = clss.getDeclaredMethod(GetterSetter.getInstance().toGetter(p.getName())) ;
				Method metSet = clss.getDeclaredMethod(GetterSetter.getInstance().toSetter(p.getName()), metGet.getReturnType()) ;
				if(p.getType().equals("string"))
					metSet.invoke(obj, set.getString(p.getColumn())) ;
				else
					metSet.invoke(obj, set.getInt(p.getColumn())) ;
			}
			lists.add(obj) ;
		}
		set.close() ;
		//用完释放
		mapping = null ; 
		return lists ;
	}
	/**
	 * 
	 * 生成查询项 - 数据库查询操作的分支处理逻辑
	 * @see
	 * @param mapping
	 * @return
	 * String
	 *
	 */
	private String _select(MappingConf mapping){
		StringBuffer columnsTemp = new StringBuffer(30) ;
		boolean separatorBool = true ;
		for(Property p : mapping.getPropertys()){
			columnsTemp.append(separatorBool ? "" : ", ") ;
			separatorBool = false ;
			columnsTemp.append("`" + p.getColumn() + "`") ;
		}
		return columnsTemp.toString().trim() ;
	}
}
