package com.techstartingpoint.xmlcltool.xmlparser.exceptions;

import com.techstartingpoint.xmlcltool.xmlparser.XmlAttributeNodeEntry;

public class AttributeOutOfTagException extends Exception {
	private static final long serialVersionUID = 1L;

	public AttributeOutOfTagException(XmlAttributeNodeEntry entry) {
        super("Attribute out of tag:"+entry.getSourceValue()+" Position:"+entry.getStart());
    }

}
