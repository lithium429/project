package com.xz.base.utils;

/**  
 * @author WangMengzhong
 * @date 2013-12-4
 * @QQ 821501431
 * @mail 821501431@qq.com
 * @version V1.0  
 */
import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.IXMLReader;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLException;
import net.n3.nanoxml.XMLParserFactory;
 
import java.util.Vector;

import javolution.context.ObjectFactory;

public class XmlHelper {

	/**
	 * 替换字符串
	 * 
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static final IXMLElement getXml(String s, String nodeName) {
		IXMLParser xmlParser = null;
		IXMLReader xmlReader = null;
		xmlParser = XmlHelper.XMLPARSER_FACTORY.object();
		xmlReader = StdXMLReader.stringReader(s);
		xmlParser.setReader(xmlReader);
		IXMLElement root = null, action = null;
		try {
			root = (IXMLElement) xmlParser.parse();
			@SuppressWarnings("rawtypes")
			Vector actions = root.getChildrenNamed(nodeName);
			action = (IXMLElement) actions.elementAt(0);
		} catch (XMLException e) {
			LogHelper.getLogger().error("获取指定节点", e);
		}
		return action;
	}

	/**
	 * 替换字符串
	 * 
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static final Vector getXmlList(String s, String tip) {
		Vector actions = null;
		IXMLParser xmlParser = null;
		IXMLReader xmlReader = null;
		xmlParser = XmlHelper.XMLPARSER_FACTORY.object();
		xmlReader = StdXMLReader.stringReader(s);
		xmlParser.setReader(xmlReader);
		IXMLElement root = null;
		try {
			root = (IXMLElement) xmlParser.parse();
			actions = root.getChildrenNamed(tip);
		} catch (XMLException e) {
			LogHelper.getLogger().error("获取指定节点列表出错", e);
		}
		return actions;
	}

	public static ObjectFactory<IXMLParser> XMLPARSER_FACTORY = new ObjectFactory<IXMLParser>() {
		protected IXMLParser create() {
			try {
				return XMLParserFactory.createDefaultXMLParser();
			} catch (Exception e) {
				LogHelper.getLogger().error("创建XMLParser时出错", e);
			}
			return null;
		}
	};
}
