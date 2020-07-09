package com.techstartingpoint.xmlcltool.xmlparser;

public class XmlMiscNodeEntry extends XmlNodeEntry {
	
	public XmlMiscNodeEntry(String sourceValue,int position) {
		super(sourceValue,position);
		this.type=XmlElementType.BLANK;
	}
	

}
