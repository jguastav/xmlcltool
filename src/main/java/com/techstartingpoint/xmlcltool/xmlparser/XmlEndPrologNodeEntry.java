package com.techstartingpoint.xmlcltool.xmlparser;

public class XmlEndPrologNodeEntry extends XmlNodeEntry {
	public XmlEndPrologNodeEntry(int position) {
		super("?>",position);
		this.setType(XmlElementType.CLOSE_PROLOG);
	}

}
