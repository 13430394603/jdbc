package com.jdbc4.conf;

import org.w3c.dom.NodeList;

/**
 * 
 * <b>解析配置文件向池传递所需的配置信息 该类只能读不能写，所有数据以配置文件为准<b>
 * @author 威 
 * <br>2017年12月11日 下午5:23:21 
 *
 */
public class ConfigurePool {
	private String driver ;
	private String url ;
	private Integer poolSize ;
	public ConfigurePool(){}
	/**
	 * 
	 * 自定义路径
	 * @see
	 * @param cfgPath
	 * void
	 *
	 */
	public void Configure(String cfgPath){
		AnalyXML analy = new AnalyXML(cfgPath) ;
		core(analy) ;
	}
	/**
	 * 
	 * 默认在src根目录
	 * @see
	 * void
	 *
	 */
	public void Configure(){
		Configure("cfg.xml") ;
	}
	/**
	 * 
	 * 处理配置文件的核心方法 
	 * @see
	 * @param doc
	 * void
	 *
	 */
	public void core(AnalyXML analy){
		NodeList nodes = analy.getDocument().getElementsByTagName("session-factory").item(0).getChildNodes() ;
		System.out.println(nodes.getLength()) ;
		//将xml一次性装入map集合中
		//从集合中取出合适的数据
		driver = analy.getElementByAttr(nodes, "name", "driver_class").getTextContent() ;
		url = analy.getElementByAttr(nodes, "name", "url").getTextContent()
				 + "?user=" + analy.getElementByAttr(nodes, "name", "username").getTextContent()
				 + "&password=" + analy.getElementByAttr(nodes, "name", "password").getTextContent() 
				 + "&useUnicode=true&characterEncoding=UTF-8" ;
		poolSize = Integer.valueOf(analy.getElementByAttr(nodes, "name", "pool_size").getTextContent()) ;
	}
	//几个外部调用方法
	public String getDriver(){
		return driver ;
	}
	public String getUrl(){
		return url ;
	}
	public Integer getPoolSize(){
		return poolSize ;
	}
}
