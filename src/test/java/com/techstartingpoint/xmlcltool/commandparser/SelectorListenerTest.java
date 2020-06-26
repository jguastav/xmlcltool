package com.techstartingpoint.xmlcltool.commandparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.techstartingpoint.xmlcltool.commandparser.SelectorListener;
import com.techstartingpoint.xmlcltool.commandparser.generated.CommandLexer;
import com.techstartingpoint.xmlcltool.commandparser.generated.CommandParser;

public class SelectorListenerTest {

	
	
	/*
	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample/java.net.SQL” -s -> get the node

	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -d -> delete node

	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -update “new text inside”

	*/

	
	private static ParseTree prepareTree(String selectorString) {
		CommandLexer commandLexer = new CommandLexer(CharStreams.fromString(selectorString));
		
		CommonTokenStream tokens = new CommonTokenStream(commandLexer);
		CommandParser parser = new CommandParser(tokens);
		ParseTree tree = parser.selector();
		return tree;
	}
	
	public static SelectorExpression prepareSelectorExpression(String selectorInputString) {
		ParseTree tree = prepareTree(selectorInputString);

		ParseTreeWalker walker = new ParseTreeWalker();
		SelectorListener listener= new SelectorListener(false);
		walker.walk(listener, tree);
		return listener.getSelector();
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"//testSQL/Sample[2]/queryString",
			"//testSQL/Sample/java.net.SQL",
			"//testSQL/Sample[3]/queryString@myAttribute"}) 
	public void testEnterSelector(String selectorInputString) {
		SelectorExpression selector = prepareSelectorExpression(selectorInputString);
		assertEquals(selectorInputString,selector.generateSourceString());
		
	} 
	
}
