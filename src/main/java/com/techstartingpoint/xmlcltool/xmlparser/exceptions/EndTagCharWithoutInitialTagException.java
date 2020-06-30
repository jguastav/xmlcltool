package com.techstartingpoint.xmlcltool.xmlparser.exceptions;

import com.techstartingpoint.xmlcltool.xmlparser.XmlEndTagCharNodeEntry;

public class EndTagCharWithoutInitialTagException extends Exception {
	private static final long serialVersionUID = 1L;

	public EndTagCharWithoutInitialTagException(XmlEndTagCharNodeEntry entry) {
        super("> char without tag definition:"+entry.getSourceValue()+" Position:"+entry.getStart());
    }

}
