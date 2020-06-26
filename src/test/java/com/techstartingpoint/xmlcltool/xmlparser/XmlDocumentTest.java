package com.techstartingpoint.xmlcltool.xmlparser;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.commandparser.SelectorListenerTest;
import com.techstartingpoint.xmlcltool.testutils.ResourceUtils;
import com.techstartingpoint.xmlcltool.util.BinaryString;

public class XmlDocumentTest {
	// //host1:80/pc/PCxyz.do
	@ParameterizedTest
	@CsvSource(value = {"//testSQL/Sample[2]/queryString,''",
			"//testSQL/Sample[2]/responseH,'Content-Type: image/svg+xml\nContent-Length: 6698\n'",
			"//testSQL/Sample[3]/responseH@class,\"java.lang.String\"",
			"//testSQL/Sample/java.net.SQL,//host1:80/pc/PCxyz.do",
			"//testSQL/Sample[2]/java.net.SQL,//oginAppLogo.svg",
			"//testSQL/Sample[3]@lb,\"/pc/resources/logos/AppLogo.svg-9\"",
			"//testSQL/Sample[3]/queryString@lb,"}) 
	public void testSelectValue(String selectorInputString,String expectedResult) {
		// Prepare the test
		
		// Prepare Selector
		SelectorExpression selector = SelectorListenerTest.prepareSelectorExpression(selectorInputString);
		// Prepare Document
		BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
		XmlDocument xmlDocument = CustomXmlListenerTest.prepareXmlDocument(documentString.getString());
		// Execute the Test
		String content = null;
		DocumentPart documentPart = xmlDocument.selectValue(selector,false,SelectionType.CONTENT);
		if (documentPart!=null) {
			content = documentPart.getText();
		}
		
		// Check the result
		assertEquals(expectedResult,content);
		
	}

}