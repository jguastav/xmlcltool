package com.techstartingpoint.xmlcltool.xmlparser;

public class XmlEndTagCharNodeEntry extends XmlNodeEntry {
	
	public XmlEndTagCharNodeEntry(int position) {
		super(">",position);
		this.setType(XmlElementType.END_TAG_CHAR);
	}

}
