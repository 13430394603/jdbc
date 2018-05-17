package com.jdbc4.conf.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * 
 * <b>将配置文件中的配置信息装进相应的实体类中<b>
 * <pre>
 * 使用：
 *  重写以下两个方法
 *  targetElements
 *  	确定集中解析的目标元素
 *  putConfObject
 *  	将解析的集中数据装入特定的对象
 *  putConfObject(Object obj)
 *  	继续解析分散数据将其他信息也装进上面 的特定对象
 * </pre>
 * @author 威 
 * <br>2017年12月11日 下午8:44:26 
 *
 */
public abstract class AnalyXMLOfAttr extends AnalyXMLV {
	public AnalyXMLOfAttr(){}
	
	public Object getConfObject(){
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>() ;
		Map<String, Object> maps ;
		List<Element> elements = targetElements() ;
		int len = elements.size() ;
		for(int i = 0; i < len; i ++){
			maps = new HashMap<String, Object>() ;
			dealAttrs(elements.get(i).getAttributes(), maps) ; //解析attrs
			//if(elements.get(i).getTextContent().length() > 0) 解析text并装进集合
				//maps.put("#text#", elements.get(i).getTextContent()) ;
			lists.add(maps) ;
		}
		return putConfObject(putConfObject(lists)) ;
	}
	public void dealAttrs(NamedNodeMap attrMaps, Map<String, Object> maps){
		int attrLen = attrMaps.getLength() ;
		for(int i = 0; i < attrLen; i++){
			String attr = attrMaps.item(i).toString() ;
			int sepIndex = attr.indexOf("=") ;
			String value = attr.substring(sepIndex + 1, attr.length()) ;
			maps.put(attr.substring(0, sepIndex), toType(value)) ;
		}
	}
	public Object toType(String value){  //将属性值赋予相应的类型
		if(value.indexOf("\"") != -1)
			return value.replaceAll("\"", "") ;
		else if(value.equals("false") || value.equals("true"))
			return Boolean.parseBoolean(value) ;
		else
			return Integer.parseInt(value) ;
	}
	public abstract Object putConfObject(List<Map<String, Object>> e) ;
	
	
}
