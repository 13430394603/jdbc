package com.jdbc4.session;

import com.jdbc4.conf.AnalysisConf;
/**
 * 
 * <b>载入配置文件并解析<b>
 * @author 威 
 * <br>2017年12月12日 下午6:00:37 
 *
 */
public class Configuration {
	//载入映射文件 未解析 并向SessionFactory传递相关的配置信息
	//buildSessionFactory
	public void configure(){
		configure("cfg.xml") ;
	}
	public void configure(String cfgPath){
		AnalysisConf.getInstance().configure(cfgPath) ;
	}
	public Configuration(){}
	public SessionFactory buildSessionFactory(){
		return new SessionFactory() ;
	}
}
