package com.techstartingpoint.xmlcltool.model;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.executor.Operation;
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

	
	public DocumentPart selectValue(SelectorExpression selectExpression,boolean excludeQuotes, SelectionType selectionType,Operation operation) {
		DocumentPart documentPart = this.data.selectValue(selectExpression,excludeQuotes, selectionType,operation); 
		return documentPart;
	}
	
	public DocumentPart selectValue(SelectorExpression selectExpression) {
		return selectValue(selectExpression,false, SelectionType.CONTENT, Operation.SELECT);
	}

	public DocumentPart selectTagContentForUpdate(SelectorExpression selector) {
		DocumentPart result = selectValue(selector,false,SelectionType.CONTENT,null);
		return result;
	}


	public DocumentPart selectAttributeContentForUpdate(SelectorExpression selector) {
		DocumentPart result = selectValue(selector,false, SelectionType.CONTENT,null);
		return result;
	}

	public DocumentPart selectTagStringForDeletion(SelectorExpression selector) {
		DocumentPart result = selectValue(selector,false,SelectionType.ELEMENT,null);
		return result;
	}

	public DocumentPart selectAttributeStringForDeletion(SelectorExpression selector) {
		DocumentPart result = selectValue(selector,false,SelectionType.ELEMENT,null);
		return result;
	}

	public DocumentPart selectPositionToInsert(SelectorExpression previousElementSelector) {
		DocumentPart result = selectValue(previousElementSelector,false,SelectionType.AFTER,null);
		return result;
	}

	public BinaryString getBinaryString() {
		return binaryString;
	}

	public void setBinaryString(BinaryString binaryString) {
		this.binaryString = binaryString;
	}
	
	
	
	
}
