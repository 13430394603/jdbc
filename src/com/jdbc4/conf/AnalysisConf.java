package com.jdbc4.conf;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jdbc4.exception.MappingFileError;
import com.jdbc4.util.MappingConf;
import com.jdbc4.util.Property;

/**
 * 
 * <b>解析所有配置文件<b>
 * @author 威 
 * <br>2017年12月13日 下午5:17:47 
 *
 */
public class AnalysisConf {
	private String cfgPath ;
	private static AnalysisConf instance  = new AnalysisConf() ;
	public static AnalysisConf getInstance(){
		return instance ;
	}
	public void configure(){
		configure("cfg.xml") ;
	}
	public void configure(String cfgPath){
		this.cfgPath = cfgPath ;
	}
	/**
	 * 
	 * 通过class名称返回映射信息实体类Mapping
	 * @see<pre>此方法只是找出相应的映射文件path还需通过_getMapping方法处理返回mapping</pre>
	 * @param className
	 * @return
	 * Mapping
	 * @throws MappingFileError 
	 *
	 */
	@SuppressWarnings("unused")
	public MappingConf getMapping(String className) throws MappingFileError{
		AnalyXML analy = new AnalyXML(cfgPath) ;
		NodeList nodes = analy.getDocument().getElementsByTagName("mapping") ;
		int len = nodes.getLength() ; 
		//查找对应的映射文件并返回对应mapping对象
		for(int i = 0; i < len; i++){
			String attrVal = analy.attr(nodes.item(i), "resource") ;
			String tempAttrVal ;
			tempAttrVal = attrVal.substring(attrVal.lastIndexOf("/") + 1, attrVal.indexOf(".xml")) ;
			if(tempAttrVal.equals(className)) ;
				return _getMapping(attrVal) ;
		}
		//不存在配置文件或者配置文件名称有歧义
		//映射文件异常或者不存在
		throw new MappingFileError("映射文件异常或者不存在") ;
	}
	/**
	 * 
	 * 将class中的标签赋值到Mapping 对应的属性中 
	 * @see
	 * @param mapping
	 * @param analy
	 * void
	 *
	 */
	private void putClassAttr(MappingConf mapping, AnalyXML analy){
		Node nodeCalss = analy.getDocument().getElementsByTagName("class").item(0) ;
		mapping.setClassPath(analy.attr(nodeCalss, "name")) ;
		mapping.setTableName(analy.attr(nodeCalss, "table")) ;
	}
	/**
	 * 
	 * 得打mapping对象 
	 * @see
	 * @param path
	 * @return
	 * Mapping
	 *
	 */
	private MappingConf _getMapping(String path){
		List<Property> lists = new ArrayList<Property>() ;
		AnalyXML analy = new AnalyXML(path) ;
		MappingConf mapping = new MappingConf() ;
		
		//对class标签的属性进行解析
		putClassAttr(mapping, analy) ;
		
		//对主键进行解析
		Node node = analy.getDocument().getElementsByTagName("id").item(0) ;
		Property keyp = new Property() ;
		keyp.setKeyBool(true) ;
		keyp.setName(analy.attr(node, "name")) ;
		keyp.setColumn(analy.attr(node, "column")) ;
		keyp.setType(analy.attr(node, "type")) ;
		lists.add(keyp) ;
		
		//对普通键进行解析
		NodeList nodes = analy.getDocument().getElementsByTagName("property") ;
		int len = nodes.getLength() ;
		for(int i = 0; i < len; i++){
			Property p = new Property() ;
			p.setKeyBool(false) ;
			Node currentNode = nodes.item(i) ;
			p.setName(analy.attr(currentNode, "name")) ;
			p.setColumn(analy.attr(currentNode, "column")) ;
			p.setType(analy.attr(currentNode, "type")) ;
			lists.add(p) ;
 		}
		mapping.setPropertys(lists) ;
		return mapping ;
	}
	
}
