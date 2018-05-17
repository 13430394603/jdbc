package com.jdbc4.conf;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * <b>原始解析<b>
 * @author 威 
 * <br>2017年12月11日 下午8:44:26 
 *
 */
public class AnalyXML {
	private Document doc ;
	public AnalyXML(String cfgPath){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance() ;
		DocumentBuilder builder ;
		doc = null ;
		try {
			builder = dbFactory.newDocumentBuilder() ;
			doc = builder.parse(new File(getClass().getClassLoader().getResource(cfgPath).toURI())) ; 
		} catch (ParserConfigurationException|SAXException|IOException|URISyntaxException e) {
			e.printStackTrace() ;
		}
	}
	public NodeList getNodes(String tagName){
		return doc.getElementsByTagName(tagName) ;
	}
	/**
	 * 
	 * 在一个集合中找出特定的属性名为特定值的
	 * @see	用于读取数据库配置
	 * @param nodes           	NodeList node集合
	 * @param attrName				
	 * @param attrValue
	 * @return
	 * Element
	 *
	 */
	public Element getElementByAttr(NodeList nodes, String attrName, String attrValue){
		for(int i = 0; i < nodes.getLength(); i ++){
			if(nodes.item(i).getNodeType() == 1){
				Element currentElement = (Element) nodes.item(i) ;
				if(currentElement.getAttribute(attrName).equals(attrValue))
					return currentElement ;
			}
		}
		return null ;
	}
	public Document getDocument(){
		return doc ;
	}
	/**
	 * 
	 * 传入一个节点或者元素 和属性名    返回属性值 
	 * @see
	 * @param node			一个节点或者元素
	 * @param attrName		属性名
	 * @return
	 * String
	 *
	 */
	public String attr(Object node, String attrName){
		if(node instanceof Node){		
			if(((Node) node).getNodeType() == 1)		//找出节点中的元素，只有元才有属性
				return ((Element) node).getAttribute(attrName) ;
		}else if(node instanceof Element)
			return ((Element) node).getAttribute(attrName) ;
		return null ;
	}
	public String text(Object node){
		return ((Node) node).getTextContent() ;
	}
	public static void main(String[] args){
		AnalyXML a = new AnalyXML("cfg.xml") ;
		NodeList nodes = a.getNodes("property") ;
		NamedNodeMap m = nodes.item(0).getAttributes() ;
		for(int i = 0; i < m.getLength(); i ++){
			System.out.println(m.item(i)) ;
		}
		System.out.println(a.getElementByAttr(nodes, "name", "driver_class").getTextContent()) ;
	}
}
