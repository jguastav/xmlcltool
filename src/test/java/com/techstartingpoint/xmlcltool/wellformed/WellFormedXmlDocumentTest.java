package com.techstartingpoint.xmlcltool.wellformed;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.techstartingpoint.xmlcltool.model.DocumentWrapper;
import com.techstartingpoint.xmlcltool.testutils.ResourceUtils;
import com.techstartingpoint.xmlcltool.xmlparser.BinaryString;


public class WellFormedXmlDocumentTest {
	
	// WISH
	//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/HTTPSamplerProxy[@enabled='true'][1]
	//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/HTTPSamplerProxy[@enabled='true'][2]
	
	
	@ParameterizedTest
	@CsvSource(value = {"1.xml,/testSQL/Sample/queryString,21",
			"1.xml,//testSQL/Sample[3]/queryString,'//testSQL/Sample[3]/queryString[1]'",
			"1.xml,//testSQL/Sample[3]/responseH,//testSQL/Sample[3]/responseH[1]",
			"1.xml,//testSQL/Sample[4]/responseH/@class,//testSQL/Sample[4]/responseH[1]@class",
			"1.xml,//testSQL/Sample/java.net.SQL,21",
			"1.xml,//testSQL/Sample[3]/java.net.SQL,//testSQL/Sample[3]/java.net.SQL[1]",
			"1.xml,//testSQL/Sample[4]/@lb,//testSQL/Sample[4]@lb",
			"1.xml,//testSQL/Sample[4]/queryString/@lb,0",
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/HTTPSamplerProxy[@enabled='true'][1],0", // HTTPSamplerProxy does not exist
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'][1],//TestPlan/hashTree[1]/hashTree[1]/hashTree[2]/SamplerProxy[1]", 
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'][2],//TestPlan/hashTree[1]/hashTree[1]/hashTree[2]/SamplerProxy[2]",
			"1.xml,//testSQL/Sample[8],//testSQL/Sample[8]"}) 
	public void testGetTinyQuery(String resourceFileName,String selectorInputString,String expectedResult) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		// Prepare the test
		// Prepare Document
		final boolean VERBOSE_SETTING = false;
		BinaryString documentString = ResourceUtils.getStringFromResourceFile(resourceFileName);
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
		WellFormedXmlDocument xmlDocument = document.getWellFormedDocument();
		// Execute the Test
		String tinyXPathQuery = xmlDocument.getTinyQuery(selectorInputString);
		// Check the result
		assertEquals(expectedResult,tinyXPathQuery);
		
	}


	
	/**
	 * Test xpath queries about nodes comparing the returned node from an xPath Query
	 * 
	 * @param resourceFileName
	 * @param xPathQuery
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	@ParameterizedTest
	@CsvSource(value = {"1.xml,//testSQL/Sample[3]/queryString",
			"1.xml,//testSQL/Sample[3]/responseH",
			"1.xml,//testSQL/Sample[3]/java.net.SQL,//testSQL/Sample[3]/java.net.SQL[1]",
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'][1]", 
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'][2]",
			"1.xml,//testSQL/Sample[8]"}) 
	public void testGetTinyQueryComparingTagNodes(String resourceFileName,String xPathQuery) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		// Prepare the test
		// Prepare Document
		final boolean VERBOSE_SETTING = false;
		BinaryString documentString = ResourceUtils.getStringFromResourceFile(resourceFileName);
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
		WellFormedXmlDocument xmlDocument = document.getWellFormedDocument();
		// Execute the Test
		String tinyXPathQuery = xmlDocument.getTinyQuery(xPathQuery);
		NodeList xPathNodeList = xmlDocument.findNodesUsingXPath(xPathQuery);
		// for tags the tinyXPath Query is a valid xPath Query
		NodeList tinyXPathNodeList = xmlDocument.findNodesUsingXPath(tinyXPathQuery);
		// Check the result
		assertTrue(xPathNodeList.item(0).isEqualNode(tinyXPathNodeList.item(0)));
		
	}
	
}
