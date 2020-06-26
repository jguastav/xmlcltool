package com.techstartingpoint.xmlcltool.commandparser;

public class SelectorItem {
	
	public static final int NO_INDEX = -1;
	String nodeName;
	int index;
	String attribute;
	
	public SelectorItem(String node) {
		this(node,NO_INDEX);
	}
	
	public SelectorItem(String node, int index) {
		this(node,index,null);
	}

	public SelectorItem(String node, int index,String attribute) {
		this.nodeName=node;
		this.index=index;
		this.attribute=attribute;
	}
	
	public SelectorItem(String node,String attribute) {
		this(node,NO_INDEX,attribute);
	}
	
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String node) {
		this.nodeName = node;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
}
