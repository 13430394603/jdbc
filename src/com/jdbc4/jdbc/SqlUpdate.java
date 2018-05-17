package com.jdbc4.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.jdbc4.conf.AnalysisConf;
import com.jdbc4.exception.MappingFileError;
import com.jdbc4.exception.NotConditionOfStatement;
import com.jdbc4.util.Condition;
import com.jdbc4.util.MappingConf;
import com.jdbc4.util.Property;

public class SqlUpdate {
	private static SqlUpdate instance = new SqlUpdate() ;
	public static SqlUpdate getInstance(){
		return instance ;
	}
	/**
	 * 
	 * 修改       
	 * @see<pre>
	 * 未提供修改条件 
	 * 	执行此方法会查找当前传入的实体的类的id作为条件进行修改
	 * 	因此在对实体类实例化操作时记得调用id的setter方法
	 * </pre>
	 * @param entity
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * void
	 * @throws NotConditionOfStatement 
	 * @throws MappingFileError 
	 *
	 */
	public void update(Object entity) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotConditionOfStatement, MappingFileError{
		//根据类名获取相应的映射文件实体类mapping对象
		MappingConf mapping = AnalysisConf.getInstance().getMapping(entity.getClass().getSimpleName()) ;
		//加载该类
		Class<?> clss = Class.forName(mapping.getClassPath()) ;
		//生成以主键为条件的Condition对象
		Condition cd = new Condition() ;
		for(Property p : mapping.getPropertys()){
			if(p.getKeyBool()){
				Method met = clss.getDeclaredMethod(com.jdbc4.util.GetterSetter.getInstance().toGetter(p.getName())) ;
				Object value = met.invoke(entity) ;
				if(value != null){
					if(p.getType().equals("integer")){
						cd.put(p.getColumn(), (Integer) value) ;
					}
					cd.put(p.getColumn(), value) ;
					break ;
				}
				throw new NotConditionOfStatement("执行delete操作时没有充足的条件") ;
			}
		}
		update(entity, cd) ;
	}
	/**
	 * 
	 * 自定义修改操作的条件
	 * @see
	 * @param entity
	 * @param cdt
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * void
	 * @throws MappingFileError 
	 *
	 */
	public void update(Object entity, Condition cdt) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MappingFileError{
		//根据类名获取相应的映射文件实体类mapping对象
		MappingConf mapping = AnalysisConf.getInstance().getMapping(entity.getClass().getSimpleName()) ;
		//加载该类
		Class<?> clss = Class.forName(mapping.getClassPath()) ;
		StringBuffer updatesStr = new StringBuffer(40) ; 
		boolean separatorBool = true ;
		
		//拼接SQL语句片段
		for(Property p : mapping.getPropertys()){
			if(!p.getKeyBool()){
				Method met = clss.getDeclaredMethod(com.jdbc4.util.GetterSetter.getInstance().toGetter(p.getName())) ;
				Object value = met.invoke(entity) ;
				if(value != null){
					updatesStr.append(separatorBool ? "" : ",") ;
					updatesStr.append("`" + p.getName() + "` = ") ;
					updatesStr.append(p.getType().equals("string") ? "'" : "") ;
					updatesStr.append(value) ;
					updatesStr.append(p.getType().equals("string") ? "'" : "") ;
					separatorBool = false ;
				}
			}	
		}
		//获取完整的SQL语句
		String sql = StatementFactory.getInstance().getUpdateStatement(mapping.getTableName(), updatesStr.toString().trim(), cdt) ;
		//执行
		ExecuteFactory.doExe(sql) ;
		//用完释放
		mapping = null ; 
	}
}
