package com.techstartingpoint.xmlcltool.xmlparser.exceptions;

public class ClosingNotOpenedTagException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClosingNotOpenedTagException(String tagName,String expectedTagName,int position) {
        super("Closing Not Opened Tag:"+tagName+" Expected:"+expectedTagName +" Position:"+position);
    }

}
