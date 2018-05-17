package com.jdbc4.conf.conf;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
 * <b>原始解析 基础父类<b>
 * @author 威 
 * <br>2017年12月11日 下午8:44:26 
 *
 */
public abstract class AnalyXML {
	private Document doc = null ;
	public AnalyXML(){}
	public void configureOfClassPath(String classPath) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance() ;
		DocumentBuilder builder ;
		try {
			builder = dbFactory.newDocumentBuilder() ;
			doc = builder.parse(new File(getClass().getClassLoader().getResource(classPath).toURI())) ; 
		} catch (ParserConfigurationException | IOException | URISyntaxException | SAXException e) {
			e.printStackTrace() ;
		}
	}
	public abstract void ConfigureOfFileSystem(String filePath) ;
	public List<Element> getElementsByTagName(String nodeName) {
		NodeList nodes = doc.getElementsByTagName(nodeName) ;
		List<Element> elements = new ArrayList<Element>() ;
		for(int i = 0; i < nodes.getLength(); i ++){
			Node node = nodes.item(i) ;
			if(node.getNodeType() == 1)
				elements.add((Element) node) ;
		}
		return elements ;
	}
}
