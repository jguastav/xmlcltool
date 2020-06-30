package com.techstartingpoint.xmlcltool.xmlparser;

/**
 * Class representing self closing tags "/>"
 * 
 * @author Jose Alberto Guastavino
 *
 */
public class XmlSelfCloseTagNodeEntry extends XmlNodeEntry {

	
	public XmlSelfCloseTagNodeEntry(int position) {
		super("/>",position);
		this.type=XmlElementType.SELF_CLOSE_TAG;
	}

	
	
	
}
