package com.jdbc4.conf.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public abstract class AnalyXMLOfText extends AnalyXMLV {
	public Object getConfObject(){
		Map<String, String> maps = new HashMap<String, String>() ;
		List<Element> elements = targetElements() ;
		int len = elements.size() ;
		for(int i = 0; i < len; i ++){
			//解析attr将属性值返回并做为键值
			maps.put(dealAttrs(elements.get(i).getAttributes()),
					elements.get(i).getTextContent()) ; 
		}
		return putConfObject(putConfObject(maps)) ;
	}
	public String dealAttrs(NamedNodeMap attrMaps){
		String attr = attrMaps.item(0).toString() ;
		int sepIndex = attr.indexOf("=") ;
		String value = attr.substring(sepIndex + 1, attr.length()) ;
		return deal(value) ;
	}
	public String deal(String value){  //将属性值赋予相应的类型
		return value.replaceAll("\"", "") ;
	}
	/**
	 * 
	 * 重写此方法以实现将配置文件中的List<Map<String, Object>> e集合数据装进特定的对象并返回该特定对象
	 * @see
	 * @param e
	 * @return
	 * Object
	 *
	 */
	public abstract Object putConfObject(Map<String, String> map) ;
}
