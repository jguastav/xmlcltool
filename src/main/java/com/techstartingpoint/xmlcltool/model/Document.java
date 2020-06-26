package com.techstartingpoint.xmlcltool.model;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.util.BinaryString;
import com.techstartingpoint.xmlcltool.xmlparser.CustomXmlListener;
import com.techstartingpoint.xmlcltool.xmlparser.DocumentPart;
import com.techstartingpoint.xmlcltool.xmlparser.XmlDocument;
import com.techstartingpoint.xmlcltool.xmlparser.generated.XMLLexer;
import com.techstartingpoint.xmlcltool.xmlparser.generated.XMLParser;

public class Document {
	
	XmlDocument data;
	BinaryString binaryString;
	
	
	/**
	 * 
	 * @param documentString
	 * @param verbose
	 * 	indicates if it is written debug information while navigating the document
	 */
	public Document(BinaryString documentString,boolean verbose ) {
		this.binaryString = documentString;

		XMLLexer xmlLexer = new XMLLexer(CharStreams.fromString(documentString.getString()));
		
		CommonTokenStream tokens = new CommonTokenStream(xmlLexer);
		XMLParser parser = new XMLParser(tokens);
		ParseTree tree = parser.document();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		CustomXmlListener listener= new CustomXmlListener(verbose);

		walker.walk(listener, tree);
		
		data = listener.getDocument();
	}
	
	public String generateSourceString() {
		return data.generateSourceString();
	}

	
	public DocumentPart selectValue(SelectorExpression selectExpression,boolean excludeQuotes, SelectionType selectionType) {
		DocumentPart documentPart = this.data.selectValue(selectExpression,excludeQuotes, selectionType); 
		return documentPart;
	}
	
	public DocumentPart selectValue(SelectorExpression selectExpression) {
		return selectValue(selectExpression,false, SelectionType.CONTENT);
	}

	public DocumentPart selectTagContentForUpdate(SelectorExpression selector) {
		DocumentPart result = selectValue(selector,false,SelectionType.CONTENT);
		return result;
	}


	public DocumentPart selectAttributeContentForUpdate(SelectorExpression selector) {
		DocumentPart result = selectValue(selector,false, SelectionType.CONTENT);
		return result;
	}

	public DocumentPart selectTagString(SelectorExpression selector) {
		DocumentPart result = selectValue(selector,false,SelectionType.ELEMENT);
		return result;
	}

	public DocumentPart selectAttributeString(SelectorExpression selector) {
		DocumentPart result = selectValue(selector,false,SelectionType.ELEMENT);
		return result;
	}

	public DocumentPart selectPositionToInsert(SelectorExpression previousElementSelector) {
		DocumentPart result = selectValue(previousElementSelector,false,SelectionType.AFTER);
		return result;
	}

	public BinaryString getBinaryString() {
		return binaryString;
	}

	public void setBinaryString(BinaryString binaryString) {
		this.binaryString = binaryString;
	}
	
	
	
	
}
