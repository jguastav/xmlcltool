package com.techstartingpoint.xmlcltool.xmlparser;

public class XmlTextNodeEntry extends XmlNodeEntry {
	
	public XmlTextNodeEntry(String sourceValue,int position) {
		super(sourceValue,position);
		this.type=XmlElementType.TEXT;
	}
	

}
