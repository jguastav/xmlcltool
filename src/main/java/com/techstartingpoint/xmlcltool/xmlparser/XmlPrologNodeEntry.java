package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.ArrayList;
import java.util.List;

public class XmlPrologNodeEntry extends XmlNodeEntry{
	
	
	List<XmlAttributeNodeEntry> attributes;
	
	public XmlPrologNodeEntry(String sourceValue,int position) {
		super(sourceValue,position); 
		this.type = XmlElementType.PROLOG;
		this.attributes= new ArrayList<>();
	}

	public void addAttribute(XmlAttributeNodeEntry attributeNodeEntry) {
		this.addChild(attributeNodeEntry);
		this.attributes.add(attributeNodeEntry);
	}	
	
	@Override 
	public String generateSourceString() {
		String text = getSourceValue();
		
		if (text.substring(text.length()-1).equals(" ")) {
			text = text.substring(0,text.length()-1);
		} 
		return text;
	}
	
	
}
