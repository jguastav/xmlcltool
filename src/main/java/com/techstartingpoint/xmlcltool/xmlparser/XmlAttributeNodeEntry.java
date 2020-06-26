package com.techstartingpoint.xmlcltool.xmlparser;

public class XmlAttributeNodeEntry extends XmlNodeEntry{
	/**
	 * name of attribute as is in original document
	 */
	String key;
	String value;
	boolean inProlog;
	
	public XmlAttributeNodeEntry(String text, int position,boolean inProlog) {
		super(text,position);
		this.type=XmlElementType.ATTRIBUTE;
		this.inProlog=inProlog;
	}

	public XmlAttributeNodeEntry(String text, int position) {
		this(text,position,false);
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
	public boolean isInProlog() {
		return inProlog;
	}

	public void setInProlog(boolean inProlog) {
		this.inProlog = inProlog;
	}

	@Override 
	public String generateSourceString() {
		return " "+getSourceValue();
	}

	
}
