package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.util.StringUtils;

public class XmlTagNodeEntry extends XmlNodeEntry {
	
	/**
	 * Name of tag as is in the original document
	 */
	String tagName;
	/**
	 * this information is redundant with children but makes easier to navigate between tags and attributes
	 * Children also contains blanks
	 * children of base class is most used to generate the final document, and this to navigate the elements
	 */
	List<XmlTagNodeEntry> tagsChildren;
	List<XmlAttributeNodeEntry> attributes;
	
	public XmlTagNodeEntry(int position) {
		super(position);
		this.setType(XmlElementType.TAG);
		this.tagsChildren= new ArrayList<XmlTagNodeEntry>();
		this.attributes = new ArrayList<XmlAttributeNodeEntry>();
	}

	
	public static List<XmlTagNodeEntry> getChildren(List<XmlTagNodeEntry> tagNodes,String tagNameFilter) {
		List<XmlTagNodeEntry> result=new ArrayList<XmlTagNodeEntry>();
		for (XmlTagNodeEntry tagNode:tagNodes) {
			for (XmlTagNodeEntry tagChild:tagNode.getTagsChildren()) {
				if (tagNameFilter.equals(tagChild.getTagName())) {
					result.add(tagChild);
				} 
			}
		} 
		return result;
	}	
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<XmlAttributeNodeEntry> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<XmlAttributeNodeEntry> attributes) {
		this.attributes = attributes;
	}

	public List<XmlTagNodeEntry> getTagsChildren() {
		return tagsChildren;
	}

	public void addAttribute(XmlAttributeNodeEntry attributeNodeEntry) {
		this.addChild(attributeNodeEntry);
		this.attributes.add(attributeNodeEntry);
	}	
	
	public void addTagChild(XmlTagNodeEntry tagChildNode) {
		this.addChild(tagChildNode);
		this.tagsChildren.add(tagChildNode);
	}
	
	@Override 
	public String generateSourceString() {
		return "<"+getSourceValue();
	}

	

	/**
	 * it returns the value corresponding to the attribute Key specified
	 * @param attributeKey
	 * 	the key of the attribute
	 * @return
	 * 	Returns the value corresponding to the attribute key
	 *  The value include enclosing quotes if they are present. 
	 */
	public DocumentPart getAttributeValueByName(String attributeKey,boolean excludeQuotes,SelectionType selectionType) {
		String resultString = null;
		DocumentPart documentPart=null;
		int start = -1;
		List<XmlAttributeNodeEntry> resultList = this.getAttributes().stream().filter(attributeNode -> attributeNode.getKey().equals(attributeKey)).collect(Collectors.toList());
		if (resultList.size()>0) {
			String fullAttributeString = resultList.get(0).generateSourceString();
			if (selectionType.equals(SelectionType.CONTENT)) {
				int equalPosition=fullAttributeString.indexOf('=');
				start = resultList.get(0).getStart()+equalPosition;
				resultString = resultList.get(0).getValue();
				documentPart = new DocumentPart(start,resultString);
				if (excludeQuotes) {
					documentPart.removeQuotes();
				}
			} else if (selectionType.equals(SelectionType.ELEMENT)) {
				documentPart = new DocumentPart(resultList.get(0).getStart(),fullAttributeString);
			}
		}

		return documentPart;
	}


	public DocumentPart getContent() {
		StringBuilder resultString = new StringBuilder();
		int start = -1;
		// content starts after the XmlEndTagCharNodeEntry child
		boolean contentStarted= false;
		for (XmlNodeEntry child:this.getChildren()) {
			if (child.getType().equals(XmlElementType.END_TAG_CHAR)) {
				contentStarted=true;
				start = child.getStart()+child.getSourceValue().length();
			};
			if (contentStarted && !(child instanceof XmlCloseTagNodeEntry) && !(child instanceof XmlEndTagCharNodeEntry)) {
				resultString.append(child.generateFullSourceString());
			}
			
		}
		DocumentPart result = new DocumentPart(start, resultString.toString());
		return result;
	}
	

}
