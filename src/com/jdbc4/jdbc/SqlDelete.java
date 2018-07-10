package com.jdbc4.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.jdbc4.conf.AnalysisConf;
import com.jdbc4.exception.MappingFileError;
import com.jdbc4.exception.NotConditionOfStatement;
import com.jdbc4.util.Condition;
import com.jdbc4.util.GetterSetter;
import com.jdbc4.util.MappingConf;
import com.jdbc4.util.Property;

public class SqlDelete {
	private static SqlDelete instance = new SqlDelete() ;
	public static SqlDelete getInstance(){
		return instance ;
	}
	/**
	 * 
	 * 删除 
	 * @see
	 * @param entity
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NotConditionOfStatement
	 * @throws MappingFileError
	 * void
	 *
	 */
	public void delete (Object entity) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotConditionOfStatement, MappingFileError{
		//根据类名获取相应的映射文件实体类mapping对象
		MappingConf mapping = AnalysisConf.getInstance().getMapping(entity.getClass().getSimpleName()) ;
		//载入该类
		Class<?> clss = Class.forName(mapping.getClassPath()) ;
		
		//生成以id为条件的Condition对象
		Condition cd = new Condition() ;
		for(Property p : mapping.getPropertys()){
			if(p.getKeyBool()){
				Method met = clss.getDeclaredMethod(GetterSetter.getInstance().toGetter(p.getName())) ;
				Object value = met.invoke(entity) ;
				if(value != null){
					cd.put(p.getColumn(), value) ;
					break ;
				}
				//抛出delete没有条件的修改异常	
				throw new NotConditionOfStatement("执行delete操作时没有充足的条件") ;
			}
		}
		delete (entity, cd) ;
	}
	/**
	 * 
	 * 删除
	 * @see
	 * @param entity
	 * @param cdt
	 * @throws MappingFileError
	 * void
	 *
	 */
	public void delete (Object entity, Condition cdt) throws MappingFileError{
		//根据类名获取相应的映射文件实体类mapping对象
		MappingConf mapping = AnalysisConf.getInstance().getMapping(entity.getClass().getSimpleName()) ;
		//获取完整的SQL语句
		String sql = StatementFactory.getInstance().getDeleteStatement(mapping.getTableName(), cdt) ;
		//执行
		ExecuteFactory.doExe(null, sql) ;
		//用完释放
		mapping = null ; 
	}
}
