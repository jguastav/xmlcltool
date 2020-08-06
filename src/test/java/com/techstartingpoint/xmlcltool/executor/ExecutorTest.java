package com.techstartingpoint.xmlcltool.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.xml.sax.SAXException;

import com.techstartingpoint.xmlcltool.model.DocumentWrapper;
import com.techstartingpoint.xmlcltool.testutils.ResourceUtils;
import com.techstartingpoint.xmlcltool.xmlparser.BinaryString;

public class ExecutorTest {

	
	static final boolean VERBOSE_SETTING = false;
	static final String EOL="\r\n";
	/**
	 * it tests the selectTagContent 
	 * 
	 *  The equivalent command line commands are
	 *  java -jar  xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector "//testSQL/Sample[2]/queryString" -select 
	 *  java -jar  xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector "//testSQL/Sample[2]/responseH" -select
	 *  java -jar  xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector "//testSQL/Sample/java.net.SQL" -select
	 *  java -jar  xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector "//testSQL/Sample[2]/java.net.SQL" -select
	 *  java -jar  xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector "//testSQL/Sample[6]/Data" -select
	 *  
	 * @param selectorString
	 * @param expectedResult
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 */
	
	@ParameterizedTest
	@CsvSource(value = {"1.xml,//testSQL/Sample[3]/queryString,''",
			"1.xml,//testSQL/Sample[3]/responseH,'Content-Type: image/svg+xml\nContent-Length: 6698\n'",
			"1.xml,//testSQL/Sample/java.net.SQL,<count>21</count>",
			"1.xml,//testSQL/Sample[3]/java.net.SQL,//oginAppLogo.svg",
			"1j.example,//TestPlan/hashTree/hashTree/hashTree,<count>3</count>",
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/HTTPSamplerProxy[@enabled='true'][1],<count>0</count>", // HTTPSamplerProxy does not exist
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'][1],'\\n          <elementProp name=\"sampler.Arguments\" elementType=\"Arguments\" guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\\n            <collectionProp name=\"Arguments.arguments\"/>\\n          </elementProp>\\n          <stringProp name=\"Sampler.domain\">g.de</stringProp>\\n          <stringProp name=\"Sampler.protocol\">sql</stringProp>\\n          <stringProp name=\"Sampler.contentEncoding\">UTF-8</stringProp>\\n        '", 
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'][2],'\\n          <elementProp name=\"sampler.Arguments\" elementType=\"Arguments\" guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\\n            <collectionProp name=\"Arguments.arguments\">\\n              <elementProp name=\"inFrame\" elementType=\"HTTPArgument\">\\n                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\\n                <stringProp name=\"Argument.name\">inFrame</stringProp>\\n                <stringProp name=\"Argument.value\">scframe</stringProp>\\n                <stringProp name=\"Argument.metadata\">=</stringProp>\\n              </elementProp>\\n            </collectionProp>\\n          </elementProp>\\n          <stringProp name=\"Sampler.domain\">g.de</stringProp>\\n          <stringProp name=\"Sampler.port\">123</stringProp>\\n          <stringProp name=\"Sampler.protocol\">sql</stringProp>\\n          <stringProp name=\"Sampler.contentEncoding\">UTF-8</stringProp>\\n          <stringProp name=\"Sampler.path\">/c.do</stringProp>\\n        '",
			"1j.example,//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'],<count>4</count>"
	}) 
	public void testSelectTagContent(String fileName,String selectorString,String escapedExpectedResult) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {

		BinaryString documentString = ResourceUtils.getStringFromResourceFile(fileName);
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
			String charResult = Executor.select(document, selectorString,VERBOSE_SETTING);
			String expectedResult = escapedExpectedResult.replace("\\r","\r").replace("\\n","\n");
			assertEquals(expectedResult,charResult);
	}

	/*
	 * 	 *  java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample/java.net.SQL” -s -> get the node

java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -d -> delete node

java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -update “new text inside”


	 */
	/**
	 * it tests the select commands related to an attribute
	 *  java -jar  xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector "//testSQL/Sample[3]/responseH@class" -select 
	 *  java -jar  xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector "//testSQL/Sample[3]@lb" -select
	 *  java -jar  xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector "//testSQL/Sample[3]/queryString@lb" -select
	 * 
	 * @param selectorString
	 * @param expectedResult
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 */
	
	@ParameterizedTest
	@CsvSource(value = {"1.xml,//testSQL/Sample[4]/responseH/@class,java.lang.String",
			"1.xml,//testSQL/Sample[4]/@lb,/pc/resources/logos/AppLogo.svg-9",
			"1.xml,//testSQL/Sample[4]/queryString/@lb,<count>0</count>",
			"1j.example,//TestPlan/hashTree/hashTree/Arguments/collectionProp/@name,Arguments.arguments"}) 
	public void testSelectAttributeValue(String fileName,String selectorString,String expectedResult) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile(fileName);
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
		String result= Executor.select(document, selectorString, VERBOSE_SETTING);
		assertEquals(expectedResult,result);

	}
	
	
	/**
	 * it tests the update of a tag content
	 * 
	 * java -jar xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector “//testSQL/Sample[5]/java.net.SQL” -update “A change made in xml" -backup
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 * 
	 */
	@Test
	public void testPrepareTagContentUpdate() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-tag-updated.xml");
		String proposedChange = "A change made in xml";
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[6]/java.net.SQL";
		BinaryString updatedString = Executor.prepareUpdate(document, selectorString, proposedChange, VERBOSE_SETTING);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
	}
	
	/**
	 * Update attribute value
	 * 
	 * java -jar xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector “//testSQL/Sample[8]/requestH@class” -update "\"java.lang.Boolean\"" -backup
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 * 
	 */
	
	@Test
	public void testPrepareAttributeValueUpdate() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-attribute-updated.xml");
		// note that attribute value should include quotes if they correspond
		String proposedChange = "\"java.lang.Boolean\"";
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[9]/requestH/@class";
		BinaryString updatedString = Executor.prepareUpdate(document, selectorString, proposedChange, VERBOSE_SETTING);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
	}
	
	
	/**
	 * It can be noticed that the difference between the original xml file and the file which has deleted the line has a 
	 * blank line between the adjacents tags
	 * That's why the method is not deleting the \n after the closing tag as that line is outside the tag.
	 * 
	 * 	java -jar your-programmed-jar -file 1.xml -selector “//testSQL/Sample[7]” -delete -backup
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 * 
	 */
	@Test
	public void testPrepareTagDeletion() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-tag-deleted.xml");
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[8]";
		BinaryString updatedString = Executor.prepareDeletion(document, selectorString, VERBOSE_SETTING);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
	}
	
	
	/**
	 * Deletes an attribute and its value
	 * 
	 * 	java -jar your-programmed-jar -file 1.xml -selector “//testSQL/Sample[8]@ts” -delete -backup
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 * 
	 */
	@Test
	public void testPrepareAttributeDeletion() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-attribute-deleted.xml");
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[9]/@ts";
		BinaryString updatedString = Executor.prepareDeletion(document, selectorString, VERBOSE_SETTING);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
		
	}
	
	
	/**
	 * Inserts a String after an element on a xml
	 * 
	 * 
	 * java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[13]/requestH” -insert “<anInsertedTag att=\"12\">Something to be inserted</anInsertedTag>” -backup
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 * 
	 * 
	 */
	
	@Test
	public void testPrepareInsert() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-tag-inserted.xml");
		String textToInsert = "<anInsertedTag att=\"12\">Something to be inserted</anInsertedTag>";
		DocumentWrapper document = new DocumentWrapper(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[14]/requestH";
		BinaryString updatedString = Executor.prepareTagInsert(document, selectorString, textToInsert, VERBOSE_SETTING);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
	}

}
