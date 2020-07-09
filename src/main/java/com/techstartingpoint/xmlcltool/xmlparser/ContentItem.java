package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.Base64;

/**
 * Class to keep the base64 representation of all content
 * Each item has information for text contiguous data
 * @author Jose Alberto Guastavino
 *
 */
public class ContentItem {
	/**
	 * The start position of the element on the document
	 */
	int start;
	/**
	 * The length of the original data
	 */
	int length;
	/**
	 * The data as is in the original document
	 */
	byte[] original;
	/**
	 * The base64 String representation of the original data
	 */
	String base64String;
	
	
	
	
	public ContentItem(int start, byte[] original) {
		super();
		this.start = start;
		this.original = original;
		this.length=original.length;
		this.base64String=new String(Base64.getEncoder().encode(original));
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public byte[] getOriginal() {
		return original;
	}
	public void setOriginal(byte[] original) {
		this.original = original;
	}
	public String getBase64String() {
		return base64String;
	}
	
	

}
