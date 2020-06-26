package com.techstartingpoint.xmlcltool.commandparser;

/**
 * 
 * 
 * Indicates the type of selection to be retrieved
 * CONTENT: 
 * 		for Tags is the characters between the open and close tag
 * 		for Attributes is the Value
 * ELEMENT
 * 		for Tags is the String including the opening and closing chars with < and >
 * 		for Attributes is the name the equal sign and the value of the attribute including the spaces between them
 * AFTER
 * 		it gets the position of the next element . It is used as an insertion point  
 * @author Jose Alberto Guastavino
 *
 */
public enum SelectionType {
	CONTENT,ELEMENT, AFTER
}
