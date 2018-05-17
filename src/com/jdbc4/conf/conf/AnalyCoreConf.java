package com.jdbc4.conf.conf;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.jdbc4.util.CoreConf;
/**
 * 
 * <b>对主要配置文件的解析<b>
 * @author 威 
 * <br>2017年12月25日 下午5:05:32 
 *
 */
public class AnalyCoreConf extends AnalyXMLOfText{
	/**
	 * 指出集中收集property目标元素
	 */
	@Override
	public List<Element> targetElements() {
		return getElementsByTagName("property") ;
	}
	/**
	 * 集中收集property
	 */
	@Override
	public Object putConfObject(Map<String, String> map) {
		CoreConf object = new CoreConf() ;
		object.setDriverClass(map.get("driver_class")) ;
		object.setUrl(map.get("url")) ;
		object.setPoolSize(Integer.parseInt(map.get("pool_size"))) ;
		object.setUsername(map.get("username")) ;
		object.setPassword(map.get("password")) ;
		object.setAddUrl(map.get("add_url")) ;
		return object ;
	}
	/**
	 * 补充收集mapping标签
	 */
	@Override
	public Object putConfObject(Object obj) {
		CoreConf object = (CoreConf) obj ;
		//收集mapping标签中的信息
		List<Element> elements = getElementsByTagName("mapping") ;
		for(Element element : elements){
			String value = element.getAttribute("resource") ;
			String key = value.substring(value.lastIndexOf("/") + 1,
					value.indexOf(".xml")) ;
			object.getMappers().put(key, value) ;
		}
		return object ;
	}
	public static void main(String[] args){
		AnalyCoreConf m = new AnalyCoreConf() ;
		m.configure("cfg.xml") ;
		
		CoreConf conf = (CoreConf) m.getConfObject() ;
		System.out.println(conf.getDriverClass()) ;
		System.out.println(conf.getUrl()) ;
		System.out.println(conf.getPoolSize()) ;
		for(Map.Entry<String, String> item : conf.getMappers().entrySet()){
			System.out.println(item.getKey() + " - " + item.getValue()) ; 
		}
	}
}
