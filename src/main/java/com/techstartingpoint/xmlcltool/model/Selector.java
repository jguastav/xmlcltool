package com.techstartingpoint.xmlcltool.model;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.commandparser.SelectorListener;
import com.techstartingpoint.xmlcltool.commandparser.SelectorType;
import com.techstartingpoint.xmlcltool.commandparser.generated.CommandLexer;
import com.techstartingpoint.xmlcltool.commandparser.generated.CommandParser;

public class Selector {
	SelectorExpression data;
	SelectorType type;
	
	public Selector(String selectorString, boolean verbose) {
		CommandLexer xmlLexer = new CommandLexer(CharStreams.fromString(selectorString));
		
		CommonTokenStream tokens = new CommonTokenStream(xmlLexer);
		CommandParser parser = new CommandParser(tokens);
		ParseTree tree = parser.selector();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		SelectorListener listener= new SelectorListener(verbose);

		walker.walk(listener, tree);
		
		data = listener.getSelector();
		

	}

	public SelectorExpression getData() {
		return data;
	}
	
	

}
