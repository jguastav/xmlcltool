package com.techstartingpoint.xmlcltool.model;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;
import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.commandparser.SelectorType;
import com.techstartingpoint.xmlcltool.executor.Operation;
import com.techstartingpoint.xmlcltool.wellformed.WellFormedXmlDocument;
import com.techstartingpoint.xmlcltool.xmlparser.BinaryString;
import com.techstartingpoint.xmlcltool.xmlparser.ContentItem;
import com.techstartingpoint.xmlcltool.xmlparser.DocumentPart;
import com.techstartingpoint.xmlcltool.xmlparser.XmlDocument;

public class DocumentWrapper {
	
	
	/**
	 * Document in proprietary structure model 
	 */
	XmlDocument data;
	/**
	 * Document to apply xpath operation
	 */
	WellFormedXmlDocument wellFormedDocument;
	
	/**
	 * 
	 * @param documentString
	 * @param verbose
	 * 	indicates if it is written debug information while navigating the document
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public DocumentWrapper(BinaryString documentString,boolean verbose ) throws ParserConfigurationException, SAXException, IOException {
		this.data = XmlDocument.generateXmlDocument(documentString, verbose);
		this.wellFormedDocument = new WellFormedXmlDocument(this.data);
	}
	
	
	
	

	public String generateSourceString() {
		return data.generateSourceString();
	}


	/**
	 * Universal selector called for all selectors
	 * @param selectExpression
	 * 	xPath Selector
	 * @param excludeQuotes
	 * 	flag indicating if quotes should be excluded when getting attribute values
	 * @param selectionType
	 * 	Type of result
	 *  		CONTENT: For tags. It is expected the enclosed content to be returned
	 * 			ELEMENT: For tags. It includes the enclosing tags in the result
	 * 			AFTER: It returns the contiguous element . It is useful as the previous step to insert new nodes
	 * @param operation
	 * 		SELECT, UPDATE, INSERT or DELETE
	 * 		It reflets the intention of the call
	 * @return
	 * 		Returns the document part identifying the result
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 */
	private DocumentPart selectValue(String xPathQuery,boolean isVerbose,boolean excludeQuotes, SelectionType selectionType,Operation operation) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		String tinyXPathQuery = this.wellFormedDocument.getTinyQuery(xPathQuery);
		DocumentPart documentPart = TinyXPath.selectValue(this.data,tinyXPathQuery,isVerbose,excludeQuotes, selectionType,operation); 
		return documentPart;
	}
	
	
	
	// MAIN PUBLIC SELECTORS SECTION
	
	public DocumentPart selectValue(String xPathQuery,boolean verbose) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		SelectorType selectorType = SelectorType.getSelectorType(xPathQuery);
		SelectionType selectionType = SelectionType.ELEMENT;
		if (selectorType.equals(SelectorType.ATTRIBUTE)) {
			selectionType = SelectionType.CONTENT;
		}
		return selectValue(xPathQuery, verbose,true, selectionType, Operation.SELECT);
	}

	public DocumentPart selectForUpdate(String xPathQuery, boolean isVerbose) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {

		DocumentPart result = selectValue(xPathQuery,isVerbose,false,SelectionType.CONTENT,null);
		return result;
	}


	public DocumentPart selectForDeletion(String xPathQuery, boolean verbose) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		DocumentPart result = selectValue(xPathQuery, verbose,false,SelectionType.ELEMENT,null);
		return result;
	}


	public DocumentPart selectPositionToInsert(String xPathQuery, boolean verbose) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		DocumentPart result = selectValue(xPathQuery, verbose,false,SelectionType.AFTER,null);
		return result;
	}

	public String getPartialDocument(DocumentPart documentPart) {
		return this.data.getBinaryString().get(documentPart);
	}

	public BinaryString getUpdatedBinaryString(int start, String newContent, int length) {
		BinaryString binaryTextDocument = this.data.getBinaryString();
		binaryTextDocument.update(start,newContent,length);
		return binaryTextDocument;
	}

	public BinaryString getBinaryStringAfterDeletion(int start, int length) {
		BinaryString binaryTextDocument = this.data.getBinaryString();
		binaryTextDocument.delete(start,length);
		return binaryTextDocument;
	}

	public BinaryString getBinaryStringAfterInsert(int start, String newContent) {
		BinaryString binaryTextDocument = this.data.getBinaryString();
		binaryTextDocument.insert(start,newContent);
		return binaryTextDocument;
	}





	public WellFormedXmlDocument getWellFormedDocument() {
		return wellFormedDocument;
	}
	
	
	
	
}
