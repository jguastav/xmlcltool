package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.ArrayList;
import java.util.List;



public abstract class XmlNodeEntry {

	@Override
	public String toString() {
		return "XmlNodeEntry [start=" + start + ", sourceValue=" + sourceValue + "]";
	}


	private int identifier;
	protected XmlElementType type; // tag / attribute / blank
	
	/**
	 * All children . In this grammar this include blanks
	 * 
	 * 	 
	 */
	private List<XmlNodeEntry> children;
	private int parentIndex;
	/**
	 * Start position in source document
	 */
	private int start;
	/**
	 * Total length of element 
	 * In the case of attributes it includes name and value
	 * In the case of tags it includes tag / content and closing tag
	 */
	private int length;
	
	/**
	 * Value as it is in the source document. 
	 * This could be with variation of lower / upper case or double / single quotes around values
	 * For the tag and prolog is the whole tag String
	 */
	private String sourceValue;
	
	public XmlNodeEntry(int start) {
		this.children= new ArrayList<XmlNodeEntry>();
		this.start=start;
	}
	
	
	public XmlNodeEntry(String sourceValue,int start) {
		this(start);
		this.sourceValue = sourceValue;
		this.length=sourceValue.length();
	}
	
	
	public void addChild(XmlNodeEntry nodeEntry) {
		this.children.add(nodeEntry);
	}

	public int getIdentifier() {
		return identifier;
	}


	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}


	public XmlElementType getType() {
		return type;
	}


	public void setType(XmlElementType type) {
		this.type = type;
	}


	public List<XmlNodeEntry> getChildren() {
		return children;
	}


	public void setChildren(List<XmlNodeEntry> children) {
		this.children = children;
	}


	public int getParentIndex() {
		return parentIndex;
	}


	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public String getSourceValue() {
		return sourceValue;
	}


	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}


	/**
	 * Each subclass could implement variations of this method
	 * @return
	 */
	public String generateSourceString() {
		return sourceValue;
	}
	
	/**
	 * Generates the String representation of this node and all of its children
	 * @return
	 */
	public String generateFullSourceString() {
		StringBuilder stringBuilder = new StringBuilder(generateSourceString());
		for (XmlNodeEntry child:this.children) {
			stringBuilder.append(child.generateFullSourceString());
		} 
		return stringBuilder.toString();
	}
	

	
}
