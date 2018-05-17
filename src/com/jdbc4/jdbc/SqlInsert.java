package com.jdbc4.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.jdbc4.conf.AnalysisConf;
import com.jdbc4.exception.MappingFileError;
import com.jdbc4.util.MappingConf;
import com.jdbc4.util.Property;

public class SqlInsert {
	private static SqlInsert instance = new SqlInsert() ;
	public static SqlInsert getInstance(){
		return instance ;
	}
	/**
	 * 
	 * 插入
	 * @see
	 * @param entity
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * void
	 * @throws MappingFileError 
	 *
	 */
	public void insert(Object entity) throws NoSuchMethodException, 
			SecurityException, ClassNotFoundException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException, MappingFileError{
		//根据类名获取相应的映射文件实体类mapping对象
		MappingConf mapping = AnalysisConf.getInstance().getMapping(entity.getClass().getSimpleName()) ;
		StringBuffer columnsStr = new StringBuffer(40) ;
		StringBuffer ValuesStr = new StringBuffer(40) ;
		Class<?> cls = Class.forName(mapping.getClassPath()) ;
		boolean sepBool = true ;
		
		//同时解析property生成columns和values SQL语句所需片段
		List<Property> lists = mapping.getPropertys() ;
		int len = lists.size() ;
		for(int i = 0; i < len; i++){
			Property p = lists.get(i) ;
			//根据配置文件获取entity中的getter方法并获取值，不为空则生成插入sql语句字段
			Method met = cls.getDeclaredMethod(com.jdbc4.util.GetterSetter.getInstance().toGetter(p.getName())) ;
			Object value = met.invoke(entity) ;
			if(value != null){
				columnsStr.append(sepBool ? "" : ", ") ;
				ValuesStr.append(sepBool ? "" : ", ") ;
				columnsStr.append("`" + p.getColumn() + "`") ;
				boolean isString = p.getType().equals("string") ;
				ValuesStr.append(isString ? "'" : "") ;
				ValuesStr.append(value) ;
				ValuesStr.append(isString ? "'" : "") ;
				sepBool = false ; 
			}
		}
		//获取对应的sql语句
		String sql = StatementFactory.getInstance().getInsertStatement(mapping.getTableName(),
				columnsStr.toString().trim(),
				ValuesStr.toString().trim()) ;
		mapping = null ; //用完释放
		//执行
		ExecuteFactory.doExe(sql) ;
	}
}
