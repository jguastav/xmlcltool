package com.techstartingpoint.xmlcltool.commandparser;

import java.util.ArrayList;
import java.util.List;


public class SelectorExpression {

	
	List<SelectorItem> selectors;
	
	public SelectorExpression() {
		this.selectors=new ArrayList<SelectorItem>();
	}
	
	
	public SelectorType getType() {
		SelectorType result =null;
		if (this.selectors!=null && this.getSelectors().size()>0) {
			SelectorItem lastItem =  this.getSelectors().get(this.getSelectors().size()-1);
			if (lastItem.getAttribute()!=null) {
				result = SelectorType.ATTRIBUTE;
			} else {
				result = SelectorType.TAG;
			}
			
		}
		return result;
	}
	
	public Object generateSourceString() {
		StringBuilder result= new StringBuilder();
		result.append("/");
		for (SelectorItem item:this.selectors) {
			result.append("/"+item.getNodeName());
			if (item.getIndex()!=SelectorItem.NO_INDEX) {
				result.append("["+item.getIndex()+"]");
			} 
			if (item.getAttribute()!=null) {
				result.append("@"+item.getAttribute());
				
			} 
			
		}
		return result.toString();
	}


	public void add(String nodeName) {
		this.selectors.add(new SelectorItem(nodeName));
	}


	public void setIndex(int index) {
		this.selectors.get(this.selectors.size()-1).setIndex(index);
	}

	public void setAttribute(String attribute) {
		this.selectors.get(this.selectors.size()-1).setAttribute(attribute);
	}


	public List<SelectorItem> getSelectors() {
		return selectors;
	}
	
	
}
