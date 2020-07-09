package com.techstartingpoint.xmlcltool.xmlparser;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.commandparser.SelectorListenerTest;
import com.techstartingpoint.xmlcltool.executor.Operation;
import com.techstartingpoint.xmlcltool.model.TinyXPath;
import com.techstartingpoint.xmlcltool.testutils.ResourceUtils;
import com.techstartingpoint.xmlcltool.util.BinaryString;

public class XmlDocumentTest {
	@ParameterizedTest
	@CsvSource(value = {"//testSQL/Sample[3]/queryString,''",
			"//testSQL/Sample[3]/responseH,'Content-Type: image/svg+xml\nContent-Length: 6698\n'",
			"//testSQL/Sample[4]/responseH@class,\"java.lang.String\"",
			"//testSQL/Sample/java.net.SQL,<count>21</count>",
			"//testSQL/Sample[3]/java.net.SQL,//oginAppLogo.svg",
			"//testSQL/Sample[4]@lb,\"/pc/resources/logos/AppLogo.svg-9\"",
			"//testSQL/Sample[4]/queryString@lb,"}) 
	public void testSelectValue(String selectorInputString,String expectedResult) {
		// Prepare the test
		final boolean VERBOSE = false;
		// Prepare Selector
		// Prepare Document
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		XmlDocument xmlDocument = CustomXmlListenerTest.prepareXmlDocument(documentString.getString());
		// Execute the Test
		String content = null;
		DocumentPart documentPart = TinyXPath.selectValue(xmlDocument,selectorInputString,VERBOSE,false,SelectionType.CONTENT,Operation.SELECT);
		if (documentPart!=null) {
			content = documentPart.getText();
		}
		
		// Check the result
		assertEquals(expectedResult,content);
		
	}

	
	
}
