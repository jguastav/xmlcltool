package com.techstartingpoint.xmlcltool.executor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.techstartingpoint.xmlcltool.model.DocumentWrapper;
import com.techstartingpoint.xmlcltool.util.FileUtils;
import com.techstartingpoint.xmlcltool.xmlparser.BinaryString;
import com.techstartingpoint.xmlcltool.xmlparser.DocumentPart;

public class Executor {

	/*
	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample/java.net.SQL” -s -> get the node

	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -d -> delete node

	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -update “new text inside”

	*/

	
	/**
	 * It returns the value of the attribute or the content of the tag
	 * It removes the quotes if they are present
	 * 
	 * @param document
	 * @param selector
	 * @return
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 */
	public static String select(DocumentWrapper document, String xPathQuery, boolean isVerbose) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		String result = null;
		DocumentPart documentPart = null;
		documentPart = document.selectValue(xPathQuery,isVerbose);
		if (documentPart!=null) {
			result =document.getPartialDocument(documentPart);
		}
		return result;
		
		
	}
	
	/**
	 * Calculates the resulting xml of applying an attribute change
	 * @param document
	 * @param selector
	 * 	Selector to identify the attribute which must be changed
	 * 
	 * @param newContent
	 *  The new content.
	 *  The content must include quotes if they are necessary
	 *  The previous quotes will be replaced. 
	 * 
	 * @return
	 * 	Returns the resulting xml of apply the proposed change
	 * Returns the new String after applying the update
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 */
	public static BinaryString prepareUpdate(
			DocumentWrapper document, 
			String xPathQuery, 
			String newContent,
			boolean isVerbose) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		
		DocumentPart documentPart=null;
		documentPart = document.selectForUpdate(xPathQuery, isVerbose);
		// String textDocument = document.generateSourceString();
		BinaryString binaryTextDocument = document.getUpdatedBinaryString(documentPart.getStart(),newContent,documentPart.getStart()+documentPart.getText().length());
		return binaryTextDocument;
	}
	

	
	/**
	 * It returns the String to be deleted from the original 
	 * It does not check if the node is mandatory
	 * @return
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 */
	public static BinaryString prepareDeletion(DocumentWrapper document, String xPathQuery,boolean isVerbose) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		DocumentPart documentPart=null;
		documentPart = document.selectForDeletion(xPathQuery,isVerbose);
		// String textDocument = document.generateSourceString();
		BinaryString binaryStringAfterDeletion = document.getBinaryStringAfterDeletion(documentPart.getStart(),documentPart.getStart()+documentPart.getText().length()); 
		return binaryStringAfterDeletion;
	}
	
	
	
	/**
	 * Gets the updated xml content after inserting newContent String AFTER the specified tag in the selector
	 * @param document
	 * 		The original document
	 * @param selector
	 * 		The selector of the node
	 * @param newContent
	 * 		The content to be inserted
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 */
	public static BinaryString prepareTagInsert(DocumentWrapper document, String xPathQuery, String newContent, boolean isVerbose) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		DocumentPart documentPart = document.selectPositionToInsert(xPathQuery,isVerbose);
//		String textDocument = document.generateSourceString();
		BinaryString binaryStringAfterInsert= document.getBinaryStringAfterInsert(documentPart.getStart(),newContent);
		return binaryStringAfterInsert;
	}
	
	
	/**
	 * 
	 * 
		add attribute (in node) maybe not
	 	
	 	
	 	add node (in node)
		
		change node content
		change attribute value
		
		remove node
		remove attribute
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws XPathExpressionException 
	 * 
	 */
	public static void main(String[] args) throws XPathExpressionException, TransformerFactoryConfigurationError, TransformerException  {
		BinaryString documentString;
		String fileName = null;
		try {
			Arguments arguments = Arguments.generate(args);
			boolean hasErrors = !arguments.validate();
			if (!hasErrors) {
				fileName = arguments.getFileName();
				documentString = FileUtils.readFromFile(fileName);
				BinaryString updatedContent=null;
				byte[] bytesSnapshot = documentString.getBytes();
				DocumentWrapper document = new DocumentWrapper(documentString, arguments.isVerbose());
				switch (arguments.getOperation()) {
				case SELECT:
					String result = select(document,arguments.getSelector(),arguments.isVerbose());
					System.out.println(result);
					break;
				case UPDATE:
					updatedContent = prepareUpdate(document,arguments.getSelector(),arguments.getNewContent(),arguments.isVerbose()); 
					break;
				case INSERT:
					updatedContent = prepareTagInsert(document,arguments.getSelector(),arguments.getNewContent(),arguments.isVerbose());
					break;
				case DELETE:
					updatedContent = prepareDeletion(document,arguments.getSelector(),arguments.isVerbose());
					break;
				}
				boolean updateFile = updatedContent!=null && !Arrays.equals(bytesSnapshot,updatedContent.getBytes());
				if (updateFile) {
					FileUtils.updateInFile(fileName,
							updatedContent,
							arguments.isBackup(),
							FileUtils.getBackupFileName(fileName, LocalDateTime.now()));
				}
			} else {
				if (arguments.isVerbose()) {
					System.out.println(arguments.errorMessages);
				} 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	} 
	

}
