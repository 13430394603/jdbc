package com.jdbc4.session;

import java.util.Map;

import com.jdbc4.conf.conf.AnalyCoreConf;
import com.jdbc4.pool.SqlPool;
import com.jdbc4.util.CoreConf;

/**
 * 
 * <b>开启数据库session<b>
 * 一个SessionFactory等价于一个数据库池
 * @author 威 
 * <br>2017年12月12日 下午6:00:10 
 *
 */
public class SessionFactory {
	//负责开启session 会话
	//传递关于配置的信息
	private SqlPool pool ;
	private Map<String, String> mapperMaps = null ; 
	public SessionFactory(){
		
	}
	public void configure(String path){
		AnalyCoreConf cf = new AnalyCoreConf() ;
		cf.configure(path) ;
		CoreConf coreConf = (CoreConf) cf.getConfObject() ;
		mapperMaps = coreConf.getMappers() ;
		pool = new SqlPool(coreConf.getUrl(),
				coreConf.getDriverClass(), coreConf.getPoolSize()) ;
	}
	
	/**
	 * 
	 * 查找主配置中的映射路径
	 * @see
	 * @param key
	 * @return
	 * String
	 *
	 */
	public String getMapperMap(String key){
		for(String keyStr : mapperMaps.keySet()){
			if(key.equals(keyStr))
				return mapperMaps.get(keyStr) ;
		}
		return null ; 
	}
	public Session openSession(){
		return new Session(pool.getConnection(), this) ;
	}
	
}
