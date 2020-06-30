package com.techstartingpoint.xmlcltool.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.model.Document;
import com.techstartingpoint.xmlcltool.model.Selector;
import com.techstartingpoint.xmlcltool.testutils.ResourceUtils;
import com.techstartingpoint.xmlcltool.util.BinaryString;

public class ExecutorTest {

	
	static final boolean VERBOSE_SETTING = false;
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
	 */
	
	@ParameterizedTest
	@CsvSource(value = {"1.xml,//testSQL/Sample[2]/queryString,''",
			"1.xml,//testSQL/Sample[2]/responseH,'Content-Type: image/svg+xml\nContent-Length: 6698\n'",
			"1.xml,//testSQL/Sample/java.net.SQL,<count>21</count>",
			"1.xml,//testSQL/Sample[2]/java.net.SQL,//oginAppLogo.svg",
			"1j.example,//TestPlan/hashTree/hashTree/hashTree,<count>3</count>"}) 
	public void testSelectTagContent(String fileName,String selectorString,String expectedResult) {

		BinaryString documentString = ResourceUtils.getStringFromResourceFile(fileName);
		SelectorExpression selector = new Selector(selectorString,VERBOSE_SETTING).getData();
		Document document = new Document(documentString, VERBOSE_SETTING);
		String result = null;
		String charResult = Executor.selectTagContent(document, selector);
		if (charResult!=null) {
			result = new String(Executor.selectTagContent(document, selector));
		}
		assertEquals(expectedResult,result);
		
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
	 */
	
	@ParameterizedTest
	@CsvSource(value = {"1.xml,//testSQL/Sample[3]/responseH@class,java.lang.String",
			"1.xml,//testSQL/Sample[3]@lb,/pc/resources/logos/AppLogo.svg-9",
			"1.xml,//testSQL/Sample[3]/queryString@lb,",
			"1j.example,//TestPlan/hashTree/hashTree/Arguments/collectionProp@name,Arguments.arguments"}) 
	public void testSelectAttributeValue(String fileName,String selectorString,String expectedResult) {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile(fileName);
		SelectorExpression selector = new Selector(selectorString,VERBOSE_SETTING).getData();
		Document document = new Document(documentString, VERBOSE_SETTING);
		String result= Executor.selectAttributeValue(document, selector);
		assertEquals(expectedResult,result);

	}
	
	
	/**
	 * it tests the update of a tag content
	 * 
	 * java -jar xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector “//testSQL/Sample[5]/java.net.SQL” -update “A change made in xml" -backup
	 * 
	 */
	@Test
	public void testPrepareTagContentUpdate() {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-tag-updated.xml");
		String proposedChange = "A change made in xml";
		Document document = new Document(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[5]/java.net.SQL";
		SelectorExpression selector = new Selector(selectorString,VERBOSE_SETTING).getData();
		BinaryString updatedString = Executor.prepareTagContentUpdate(document, selector, proposedChange);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
	}
	
	/**
	 * Update attribute value
	 * 
	 * java -jar xmlcltool-0.0.1-SNAPSHOT.jar -file 1.xml -selector “//testSQL/Sample[8]/requestH@class” -update "\"java.lang.Boolean\"" -backup
	 * 
	 */
	
	@Test
	public void testPrepareAttributeValueUpdate() {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-attribute-updated.xml");
		// note that attribute value should include quotes if they correspond
		String proposedChange = "\"java.lang.Boolean\"";
		Document document = new Document(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[8]/requestH@class";
		SelectorExpression selector = new Selector(selectorString,VERBOSE_SETTING).getData();
		BinaryString updatedString = Executor.prepareAttributeValueUpdate(document, selector, proposedChange);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
	}
	
	
	/**
	 * It can be noticed that the difference between the original xml file and the file which has deleted the line has a 
	 * blank line between the adjacents tags
	 * That's why the method is not deleting the \n after the closing tag as that line is outside the tag.
	 * 
	 * 	java -jar your-programmed-jar -file 1.xml -selector “//testSQL/Sample[7]” -delete -backup
	 * 
	 */
	@Test
	public void testPrepareTagDeletion() {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-tag-deleted.xml");
		Document document = new Document(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[7]";
		SelectorExpression selector = new Selector(selectorString,VERBOSE_SETTING).getData();
		BinaryString updatedString = Executor.prepareTagDeletion(document, selector);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
	}
	
	
	/**
	 * Deletes an attribute and its value
	 * 
	 * 	java -jar your-programmed-jar -file 1.xml -selector “//testSQL/Sample[8]@ts” -delete -backup
	 * 
	 */
	@Test
	public void testPrepareAttributeDeletion() {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-attribute-deleted.xml");
		Document document = new Document(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[8]@ts";
		SelectorExpression selector = new Selector(selectorString,VERBOSE_SETTING).getData();
		BinaryString updatedString = Executor.prepareAttributeDeletion(document, selector);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
		
	}
	
	
	/**
	 * Inserts a String after an element on a xml
	 * 
	 * 
	 * java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[13]/requestH” -insert “<anInsertedTag att=\"12\">Something to be inserted</anInsertedTag>” -backup
	 * 
	 * 
	 */
	
	@Test
	public void testPrepareInsert() {
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		BinaryString expectedDocumentString = ResourceUtils.getStringFromResourceFile("1-tag-inserted.xml");
		String textToInsert = "<anInsertedTag att=\"12\">Something to be inserted</anInsertedTag>";
		Document document = new Document(documentString, VERBOSE_SETTING);
		String selectorString = "//testSQL/Sample[13]/requestH";
		SelectorExpression selector = new Selector(selectorString,VERBOSE_SETTING).getData();
		BinaryString updatedString = Executor.prepareTagInsert(document, selector, textToInsert);
		assertEquals(expectedDocumentString.getString(),updatedString.getString());
	}
	
}
