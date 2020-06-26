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
	
	/**
	 * Initial position from where text is extracted from original document
	 */
	int start;
	/**
	 * The text to be extracted
	 */
	String text;
	
	public DocumentPart(int start, String text) {
		super();
		this.start = start;
		this.text = text;
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

	public void removeQuotes() {
		if (this.text!=null) {
			String newText=StringUtils.removeQuotes(text);
			if (newText.length()!=this.text.length()) {
				this.text=newText;
				this.start++;
			}
		} 
	}
	
	
	
}
