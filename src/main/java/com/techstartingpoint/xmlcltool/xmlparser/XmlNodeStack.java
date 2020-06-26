package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.ArrayDeque;
import java.util.Deque;

public class XmlNodeStack {
    Deque<XmlNodeEntry> tagEntryStack;

    public XmlNodeStack() {
        this.tagEntryStack = new ArrayDeque<XmlNodeEntry>();
    }
    
    public void push(XmlNodeEntry entry) {
    	tagEntryStack.addFirst(entry);
    }
    
    public XmlNodeEntry pop() {
    	return tagEntryStack.removeFirst();
    }
    
    public XmlNodeEntry peek() {
    	return tagEntryStack.peekFirst();
    }
    

}
