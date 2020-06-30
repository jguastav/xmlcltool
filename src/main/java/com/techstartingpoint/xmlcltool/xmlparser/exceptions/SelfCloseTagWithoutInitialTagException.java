package com.techstartingpoint.xmlcltool.xmlparser.exceptions;

import com.techstartingpoint.xmlcltool.xmlparser.XmlSelfCloseTagNodeEntry;

public class SelfCloseTagWithoutInitialTagException extends Exception {
	private static final long serialVersionUID = 1L;

	public SelfCloseTagWithoutInitialTagException(XmlSelfCloseTagNodeEntry entry) {
        super("/> self closing tag out of tag definition:"+entry.getSourceValue()+" Position:"+entry.getStart());
    }

}
