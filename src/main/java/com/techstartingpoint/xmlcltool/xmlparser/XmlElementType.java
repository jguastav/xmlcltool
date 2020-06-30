package com.techstartingpoint.xmlcltool.xmlparser;

/**
 * Main types of elements in xml documents
 * Normally they should be only Tag and Attribute
 * But to respect original document here there are also blanks that are elements like space, \t, \n and so on
 * @author Jose Alberto Guastavino
 *
 */
public enum XmlElementType {
	TAG,ATTRIBUTE,BLANK,CONTENT,PROLOG,DOCUMENT,CLOSE_TAG, SELF_CLOSE_TAG,END_TAG_CHAR, CLOSE_PROLOG
}

