package com.techstartingpoint.xmlcltool.xmlparser;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import com.techstartingpoint.xmlcltool.testutils.ResourceUtils;
import com.techstartingpoint.xmlcltool.util.BinaryString;
import com.techstartingpoint.xmlcltool.xmlparser.CustomXmlListener;
import com.techstartingpoint.xmlcltool.xmlparser.generated.XMLLexer;
import com.techstartingpoint.xmlcltool.xmlparser.generated.XMLParser;

public class CustomXmlListenerTest {

	
	public static XmlDocument prepareXmlDocument(String documentString) {
		
		final boolean VERBOSE_SETTING = false;
		
		XMLLexer xmlLexer = new XMLLexer(CharStreams.fromString(documentString));
		
		CommonTokenStream tokens = new CommonTokenStream(xmlLexer);
		XMLParser parser = new XMLParser(tokens);
		ParseTree tree = parser.document();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		CustomXmlListener listener= new CustomXmlListener(VERBOSE_SETTING);
		walker.walk(listener, tree);
		XmlDocument result = listener.getDocument();
		return result;
	}
	
	@Test
	void testEnterDocument() {
			// prepare the environment
			BinaryString documentString = ResourceUtils.getStringFromResourceFile("1.xml");
			
			// execute the test
			XmlDocument xmlDocument = prepareXmlDocument(documentString.getString());
			
			// check the results
			assertEquals(documentString.getString(),xmlDocument.generateSourceString());
			
			// there are 21 Samples inside testSql
			assertEquals(21,xmlDocument.getRoot().getTagsChildren().size());
	}

}
