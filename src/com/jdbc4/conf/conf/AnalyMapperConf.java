package com.jdbc4.conf.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.jdbc4.util.MappingConf;
import com.jdbc4.util.Property;
/**
 * 
 * <b>负责解析映射文件<b>
 * <pre>
 * 覆盖以下三个方法：
 * 	targetElements() 集中信息的位置
 *  putConfObject(List<Map<String, Object>> e) 收集集中信息进特定对象
 *  putConfObject(Object obj) 利用重载继续收集零散信息进特定对象
 * 使用此父类生成特定功能的解析类根据什么写代码
 * 	根据映射文件
 *	根据用来装数据的特定对象
 *	
 * </pre>
 * @author 威 
 * <br>2017年12月25日 下午3:14:09 
 *
 */
public class AnalyMapperConf extends AnalyXMLOfAttr {
	
	/**
	 * 获取property标签 集中信息的位置
	 */
	@Override
	public List<Element> targetElements() {
		return getElementsByTagName("property") ;
	}
	/**
	 * 获取property标签的信息 收集集中信息
	 */
	@Override
	public Object putConfObject(List<Map<String, Object>> e) {
		MappingConf cf = new MappingConf() ;
		List<Property> propertys = new ArrayList<Property>() ;
		for(Map<String, Object> maps : e){
			Property p = new Property() ;
			p.setKeyBool(false) ;
			p.setColumn((String) maps.get("column")) ;
			p.setName((String) maps.get("name")) ;
			p.setType((String) maps.get("type")) ;
			propertys.add(p) ;
		}
		cf.setPropertys(propertys) ;
		return cf ;
	}
	/**
	 * 
	 * 重载进行附加信息的收集 - 附加信息有class标签和主键
	 * @see
	 * @param obj
	 * @return
	 * Object
	 *
	 */
	public Object putConfObject(Object obj){
		MappingConf object = (MappingConf) obj ;
		Element classTag = getElementsByTagName("class").get(0) ;
		object.setClassPath(classTag.getAttribute("name")) ;
		object.setTableName(classTag.getAttribute("table")) ;
		Element idTag = getElementsByTagName("id").get(0) ;
		Property p = new Property() ;
		p.setKeyBool(true) ;
		p.setColumn(idTag.getAttribute("column")) ;
		p.setName(idTag.getAttribute("name")) ;
		p.setType(idTag.getAttribute("type")) ;
		object.getPropertys().add(p) ;
		return object ;
	} 
	@Override
	public void ConfigureOfFileSystem(String filePath) {}
	public static void main(String[] args){
		AnalyMapperConf m = new AnalyMapperConf() ;
		m.configure("com/jdbc4/mapping/FileType.xml") ;
		
		MappingConf conf = (MappingConf) m.getConfObject() ;
		for(Property p : conf.getPropertys()){
			System.out.println("name-" + p.getName()) ;
			System.out.println("column-" + p.getColumn()) ;
			System.out.println("bool-" + p.getKeyBool()) ;
			System.out.println("type-" + p.getType()) ;
			
		}
		//System.out.println(conf.getTableName()) ;
	}
}
