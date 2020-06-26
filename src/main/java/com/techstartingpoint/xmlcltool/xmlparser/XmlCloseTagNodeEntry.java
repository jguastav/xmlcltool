package com.techstartingpoint.xmlcltool.xmlparser;

/**
 * Class representing closing tags
 * 
 * @author Jose Alberto Guastavino
 *
 */
public class XmlCloseTagNodeEntry extends XmlNodeEntry {

	String tagName;
	
	public XmlCloseTagNodeEntry(String completeSourceValue,String tagName,int position) {
		super(completeSourceValue,position);
		this.setTagName(tagName);
		this.type=XmlElementType.CLOSE_TAG;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
	
	
}
