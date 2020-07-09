package com.techstartingpoint.xmlcltool.wellformed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.techstartingpoint.xmlcltool.model.TinyXPath;
import com.techstartingpoint.xmlcltool.xmlparser.XmlDocument;

public class WellFormedXmlDocument {

	// check XML starlet xml.exe. http://xmlstar.sourceforge.net/docs.php
	
	// check getParentNode()
	// check compareDocumentPosition(node)
	// getChildren()
	
	Document domDocument;
	
	public WellFormedXmlDocument(XmlDocument xmlDocument) throws ParserConfigurationException, SAXException, IOException {
		String base64String = xmlDocument.getBase64DocumentString();
		InputStream xmlContentAsinputStream = new ByteArrayInputStream(base64String.getBytes());
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
 		Document calculatedDomDocument = builder.parse(xmlContentAsinputStream);
		this.domDocument = calculatedDomDocument;
	}


	public NodeList findNodesUsingXPath(String xPathExpression) throws XPathExpressionException  {
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.compile(xPathExpression).evaluate(this.domDocument, XPathConstants.NODESET);
		return nodeList;
	}

	/**
	 * 
	 * Returns an equivalent tinyXPath query or the number of elements of applying the XPath query to a document
	 * 
	 * @param xPathQuery
	 * 		The xpath query
	 * @return
	 * If the result of the XPath Query is one element (attribute or tag)
	 * 		Generates a tinyXPath query which returns as result the
	 * If the result of the XPathQuery is zero or many elements 
	 * 		Generates the number of elements 
	 * @throws XPathExpressionException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	public String getTinyQuery(String xPathQuery) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		NodeList currentNodes = findNodesUsingXPath(xPathQuery);
		// select attribute (only the last node according to grammar)
		String tinyXPathQuery = null;
		if (currentNodes.getLength()>0) {
			if (currentNodes.getLength()!=1) {
				tinyXPathQuery = new Integer(currentNodes.getLength()).toString();
			} else {
				Node node = currentNodes.item(0);
				tinyXPathQuery = TinyXPath.getTinySelectorPath(node);
			}
		} else {
			tinyXPathQuery = "0";
		}  
		return tinyXPathQuery;
	}
}
