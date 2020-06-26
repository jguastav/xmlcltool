package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Stack implementation for XmlTagNodeEntry
 * @author Jose Alberto Guastavino
 *
 */
public class XmlTagStack {
	
    Deque<XmlTagNodeEntry> tagEntryStack;

    public XmlTagStack() {
        this.tagEntryStack = new ArrayDeque<XmlTagNodeEntry>();
    }
    
    public void push(XmlTagNodeEntry entry) {
    	tagEntryStack.addFirst(entry);
    }
    
    public XmlTagNodeEntry pop() {
    	return tagEntryStack.removeFirst();
    }
    
    public XmlTagNodeEntry peek() {
    	return tagEntryStack.peekFirst();
    }
    
    public int size() {
    	return tagEntryStack.size();
    }

}
