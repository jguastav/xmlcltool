package com.techstartingpoint.xmlcltool.xmlparser;

import com.techstartingpoint.xmlcltool.util.StringUtils;

/**
 * 
 * 
 * Part of document to be changed
 * 
 * 
 * @author Jose Alberto Guastavino
 *
 */
public class DocumentPart {
	
	
	final static String OPEN_COUNT="<count>";
	final static String CLOSE_COUNT="</count>";
	
	/**
	 * Initial position from where text is extracted from original document
	 */
	int start;
	/**
	 * The text to be extracted
	 */
	String text;
	
	/**
	 * There are 2 types of responses 
	 * count or content
	 * count property is count where the type of information is count
	 */
	boolean count;
	
	/**
	 * DocumentPart for content responses
	 * @param start
	 * @param text
	 */
	public DocumentPart(int start, String text) {
		super();
		this.start = start;
		this.text = text;
		this.count = false;
	}

	/**
	 * DocumentPart for count response
	 * @param size
	 */
	public DocumentPart(int size) {
		super();
		this.start = -1;
		this.text = OPEN_COUNT+size+CLOSE_COUNT;
		this.count = true;
	}

	
	
	public void removeQuotes() {
		if (this.text!=null) {
			String newText=StringUtils.removeQuotes(text);
			if (newText.length()!=this.text.length()) {
				this.text=newText;
				this.start++;
			}
		} 
	}
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public boolean isCount() {
		return count;
	}

	public void setCount(boolean count) {
		this.count = count;
	}
	


	
	
	
}
