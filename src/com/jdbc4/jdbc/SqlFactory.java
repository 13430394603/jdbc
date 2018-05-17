package com.jdbc4.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.jdbc4.exception.MappingFileError;
import com.jdbc4.exception.NotConditionOfStatement;
import com.jdbc4.util.Condition;

public class SqlFactory {
	public static Object get(Class<?> cls, Object id){
		try {
			return SqlSelect.getInstance().select(cls, id) ;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException | SQLException
				| MappingFileError e) {
			e.printStackTrace();
		}
		return null ;
	}
	public static List<Object> get(Class<?> cls){
		try {
			return SqlSelect.getInstance().get(cls) ;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException | SQLException
				| MappingFileError e) {
			e.printStackTrace();
		}
		return null ;
	}
	public static List<Object> get(Class<?> cls, Condition cd){
		try {
			return SqlSelect.getInstance().select(cls, cd) ;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException | SQLException
				| MappingFileError e) {
			e.printStackTrace();
		}
		return null ;
	}
	public static void save(Object entity) {
		try {
			SqlInsert.getInstance().insert(entity) ;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | MappingFileError e) {
			e.printStackTrace();
		}
	}
	public static void update(Object entity){
		try {
			SqlUpdate.getInstance().update(entity) ;
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NotConditionOfStatement | MappingFileError e) {
			e.printStackTrace();
		}
	}
	public static void update(Object entity, Condition cdt){
		try {
			SqlUpdate.getInstance().update(entity, cdt) ;
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | MappingFileError e) {
			e.printStackTrace();
		}
	}
	public static void delete(Object entity){
		try {
			SqlDelete.getInstance().delete(entity) ;
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NotConditionOfStatement | MappingFileError e) {
			e.printStackTrace();
		}
	}
	public static void delete(Object entity, Condition cdt){
		try {
			SqlDelete.getInstance().delete(entity, cdt) ;
		} catch (MappingFileError e) {
			e.printStackTrace();
		}
	}
}
