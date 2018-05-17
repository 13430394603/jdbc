package com.jdbc4.conf.conf;

import java.util.List;

import org.w3c.dom.Element;
/**
 * 
 * <b>本程序中特定解析抽象类 作为父类/基类 继承于AnalyXML<b>
 * @author 威 
 * <br>2017年12月25日 下午4:54:13 
 *
 */
public abstract class AnalyXMLV extends AnalyXML {
	/**
	 * 
	 * 使用类路径加载配置文件
	 * @see
	 * @param confPath
	 * void
	 *
	 */
	public void configure(String confPath){
		configureOfClassPath(confPath) ;
	}
	public abstract List<Element> targetElements() ;
	public abstract Object putConfObject(Object obj) ;
	public void ConfigureOfFileSystem(String filePath) {}
}
