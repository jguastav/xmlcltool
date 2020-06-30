package com.techstartingpoint.xmlcltool.executor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.commandparser.SelectorType;
import com.techstartingpoint.xmlcltool.model.Document;
import com.techstartingpoint.xmlcltool.model.Selector;
import com.techstartingpoint.xmlcltool.util.BinaryString;
import com.techstartingpoint.xmlcltool.util.FileUtils;
import com.techstartingpoint.xmlcltool.xmlparser.DocumentPart;

public class Executor {

	/*
	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample/java.net.SQL” -s -> get the node

	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -d -> delete node

	java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -update “new text inside”

	*/

	
	public static String selectTagContent(Document document, SelectorExpression selector) {
		String result =null;
		DocumentPart documentPart = document.selectValue(selector);
		if (documentPart!=null) {
			result =document.getBinaryString().get(documentPart);
		}
		return result;
	}
	
	/**
	 * It returns the value of the attribute
	 * It removes the quotes if they are present
	 * 
	 * @param document
	 * @param selector
	 * @return
	 */
	public static String selectAttributeValue(Document document, SelectorExpression selector) {
		String result=null;
		DocumentPart documentPart = document.selectValue(selector,true,SelectionType.CONTENT,Operation.SELECT);
		if (documentPart!=null) {
			result = document.getBinaryString().get(documentPart);
		} 
		return result;
	}
	
	/**
	 * Returns the new String after applying the update
	 */
	public static BinaryString prepareTagContentUpdate(
			Document document, 
			SelectorExpression selector, 
			String newContent) {
		DocumentPart documentPart = document.selectTagContentForUpdate(selector);
		// String textDocument = document.generateSourceString();
		BinaryString binaryTextDocument = document.getBinaryString();
		binaryTextDocument.update(documentPart.getStart(),newContent,documentPart.getStart()+documentPart.getText().length());
		return binaryTextDocument;
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
	 * 
	 */
	public static BinaryString prepareAttributeValueUpdate(Document document, SelectorExpression selector, String newContent) {
		DocumentPart documentPart = document.selectAttributeContentForUpdate(selector);
		// String textDocument = document.generateSourceString();
		BinaryString binaryTextDocument = document.getBinaryString();
		binaryTextDocument.update(documentPart.getStart(),newContent,documentPart.getStart()+documentPart.getText().length());
		return binaryTextDocument;
	}
	
	/**
	 * It returns the String to be deleted from the original 
	 * It does not check if the node is mandatory
	 * @return
	 */
	public static BinaryString prepareTagDeletion(Document document, SelectorExpression selector) {
		DocumentPart documentPart = document.selectTagStringForDeletion(selector);
		// String textDocument = document.generateSourceString();
		BinaryString binaryTextDocument = document.getBinaryString();
		binaryTextDocument.delete(documentPart.getStart(),documentPart.getStart()+documentPart.getText().length());
		return binaryTextDocument;
	}
	
	public static BinaryString prepareAttributeDeletion(Document document, SelectorExpression selector) {
		DocumentPart documentPart = document.selectAttributeStringForDeletion(selector);
//		String textDocument = document.generateSourceString();
		BinaryString binaryTextDocument = document.getBinaryString();
		binaryTextDocument.delete(documentPart.getStart(),documentPart.getStart()+documentPart.getText().length());
		return binaryTextDocument;
	}
	
	
	/**
	 * Gets the updated xml content after inserting newContent String AFTER the specified tag in the selector
	 * @param document
	 * 		The original document
	 * @param selector
	 * 		The selector of the node
	 * @param newContent
	 * 		The content to be inserted
	 */
	public static BinaryString prepareTagInsert(Document document, SelectorExpression previousElementSelector, String newContent) {
		DocumentPart documentPart = document.selectPositionToInsert(previousElementSelector);
//		String textDocument = document.generateSourceString();
		BinaryString binaryTextDocument = document.getBinaryString();
		binaryTextDocument.insert(documentPart.getStart(),newContent);
		return binaryTextDocument;
	}
	
	
	/**
	 * 
	 * 
		add attribute (in node) mayb not
	 	
	 	
	 	add node (in node)
		
		change node content
		change attribute value
		
		remove node
		remove attribute
	 * 
	 */
	public static void main(String[] args)  {
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
				SelectorExpression selector = new Selector(arguments.getSelector(),arguments.isVerbose()).getData();
				Document document = new Document(documentString, arguments.isVerbose());
				switch (arguments.getOperation()) {
				case SELECT:
					String result = null;
					if (selector.getType().equals(SelectorType.ATTRIBUTE)) {
						result  = selectAttributeValue(document,selector);
					} else if (selector.getType().equals(SelectorType.TAG)) {
						result = selectTagContent(document,selector);
					}
					System.out.println(result);
					break;
				case UPDATE:
					if (selector.getType().equals(SelectorType.ATTRIBUTE)) {
						updatedContent = prepareAttributeValueUpdate(document,selector,arguments.getNewContent());
					} else if (selector.getType().equals(SelectorType.TAG)) {
						updatedContent = prepareTagContentUpdate(document,selector,arguments.getNewContent());
					}
					break;
				case INSERT:
					updatedContent = prepareTagInsert(document,selector,arguments.getNewContent());
					break;
				case DELETE:
					if (selector.getType().equals(SelectorType.ATTRIBUTE)) {
						updatedContent = prepareAttributeDeletion(document,selector);
					} else if (selector.getType().equals(SelectorType.TAG)) {
						updatedContent = prepareTagDeletion(document,selector);
					}
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
		}

		
	} 
	

}
