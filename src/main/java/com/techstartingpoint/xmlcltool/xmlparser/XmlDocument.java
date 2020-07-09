package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.techstartingpoint.xmlcltool.commandparser.SelectorItem;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.AttributeOutOfTagException;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.ClosingNotOpenedTagException;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.EndTagCharWithoutInitialTagException;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.SelfCloseTagWithoutInitialTagException;
import com.techstartingpoint.xmlcltool.xmlparser.generated.XMLLexer;
import com.techstartingpoint.xmlcltool.xmlparser.generated.XMLParser;

/**
 * Main Document
 * It represents the whole document
 * This is the entry point to any operation
 * @author Jose Alberto Guastavino
 *
 */
public class XmlDocument  {
	
	
	private static final int NO_PROLOG = -1;
	List<XmlNodeEntry> elements;
    /**
     * Stack of open tags waiting for being closed while adding elements to the document
     */
	XmlTagStack tagEntryStack;
	
	
	/**
	 * indicates the next index that identifies the id of the element to be added
	 * It begins with 0
	 */
	private int nextElementIndex;
	
	/**
	 * prolog id .If it does not exist should be 0. If not is the index of the prolog tag in the elements list
	 * It could be used also to avoid having more than one prolog
	 */
	private int prologElementId;

	/**
	 * Pointer to the root tag node to initiate walking the xml tree 
	 */
	private int rootNodeIndex;
	
	
	/**
	 * Original representation of all characters as they was in the former documents
	 * The main source of information is expressed as a Byte Array
	 */
	private BinaryString binaryString;
	
	
	/**
	 * Base64 representation of content
	 */
	private List<ContentItem> base64Content;

	
	public List<ContentItem> getBase64Content() {
		return base64Content;
	}



	public XmlDocument(BinaryString binaryString) {
		this.elements = new ArrayList<XmlNodeEntry>();
		this.nextElementIndex = 0;
		this.tagEntryStack = new XmlTagStack();
		this.prologElementId = NO_PROLOG;
		this.rootNodeIndex = SelectorItem.NO_INDEX;
		this.binaryString = binaryString;
	}
	
	
	
	private void calculateBase64Content() {
		this.base64Content= new ArrayList<>();
		for (XmlNodeEntry element: elements) {
			if (element instanceof XmlTextNodeEntry) {
				this.base64Content.add(
						new ContentItem(element.getStart(), 
						Arrays.copyOfRange(this.binaryString.getBytes(), element.getStart(), element.getStart()+element.getLength())));
			}
		} 
	}
	
	
	public static XmlDocument generateXmlDocument(BinaryString documentString,boolean verbose ) {
		XMLLexer xmlLexer = new XMLLexer(CharStreams.fromString(documentString.getString()));
		
		CommonTokenStream tokens = new CommonTokenStream(xmlLexer);
		XMLParser parser = new XMLParser(tokens);
		ParseTree tree = parser.document();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		CustomXmlListener listener= new CustomXmlListener(documentString,verbose);

		walker.walk(listener, tree);
		XmlDocument returnValue = listener.getDocument();
		returnValue.setBinaryString(documentString);
		
		returnValue.calculateBase64Content();
		
		return listener.getDocument();
	}	

	public List<XmlNodeEntry> getElements() {
		return elements;
	}

	public int getNextElementIndex() {
		return nextElementIndex;
	}

	
	/**
	 * Get the last open node that wasn't closed
	 * @return
	 */
	private XmlTagNodeEntry getLastTagNodeEntry() {
		XmlTagNodeEntry lastTagNodeEntry= this.tagEntryStack.peek();
		return lastTagNodeEntry;
	} 
	

	
	
	// ADDING NODES TO THE MODEL
	
	/**
	 * Add a close tag
	 * 
	 * @param closingTagNodeEntry
	 * The node to be added
	 * 
	 * @throws ClosingNotOpenedTagException
	 * It happens when there is no previous opening tag
	 * 
	 */
	public void add(XmlCloseTagNodeEntry closingTagNodeEntry) throws ClosingNotOpenedTagException {
		closingTagNodeEntry.setIdentifier(this.nextElementIndex++);
		
		XmlTagNodeEntry openingNodeEntry= this.tagEntryStack.peek();
		
		String closingTagName = closingTagNodeEntry.getTagName();
		String expectedTagName =openingNodeEntry.getTagName(); 
		int startPosition = closingTagNodeEntry.getStart();
		if (closingTagName.equals(expectedTagName)) {
			openingNodeEntry = this.tagEntryStack.pop();
			openingNodeEntry.addChild(closingTagNodeEntry);
			closingTagNodeEntry.setParentIndex(openingNodeEntry.getIdentifier());
		} else {
			throw new ClosingNotOpenedTagException(closingTagName, expectedTagName, startPosition);
		}
		this.elements.add(closingTagNodeEntry);
		
	}
	
	public void add(XmlAttributeNodeEntry attributeNodeEntry) throws AttributeOutOfTagException {
		XmlTagNodeEntry currentTagNodeEntry= this.tagEntryStack.peek();
		attributeNodeEntry.setIdentifier(this.nextElementIndex++);
		if (currentTagNodeEntry!=null) {

			attributeNodeEntry.setParentIndex(currentTagNodeEntry.getIdentifier());
			currentTagNodeEntry.addAttribute(attributeNodeEntry);
		} else if (attributeNodeEntry.isInProlog()) {
			XmlPrologNodeEntry currentPrologNodeEntry = (XmlPrologNodeEntry) this.elements.get(this.prologElementId);
			attributeNodeEntry.setParentIndex(currentPrologNodeEntry.getIdentifier());
			currentPrologNodeEntry.addAttribute(attributeNodeEntry);
		} else {
			
			throw new AttributeOutOfTagException(attributeNodeEntry);
		}	
		this.elements.add(attributeNodeEntry);
		// redundant information but facilitates navigation
	}

	
	public void add(XmlEndTagCharNodeEntry endTagCharNodeEntry) throws EndTagCharWithoutInitialTagException {
		XmlTagNodeEntry parentTagNodeEntry= getLastTagNodeEntry();
		endTagCharNodeEntry.setIdentifier(this.nextElementIndex++);
		if (parentTagNodeEntry!=null) {
			endTagCharNodeEntry.setParentIndex(parentTagNodeEntry.getIdentifier());
			// redundant information but facilitates navigation
			parentTagNodeEntry.addChild(endTagCharNodeEntry);
		} else {
			throw new EndTagCharWithoutInitialTagException(endTagCharNodeEntry);
		}
		this.elements.add(endTagCharNodeEntry);
	}
	

	public void add(XmlSelfCloseTagNodeEntry selfCloseTagNodeEntry) throws SelfCloseTagWithoutInitialTagException {
		selfCloseTagNodeEntry.setIdentifier(this.nextElementIndex++);
		XmlTagNodeEntry parentTagNodeEntry= this.tagEntryStack.pop();
		if (parentTagNodeEntry!=null) {
			selfCloseTagNodeEntry.setParentIndex(parentTagNodeEntry.getIdentifier());
			// redundant information but facilitates navigation
			parentTagNodeEntry.addChild(selfCloseTagNodeEntry);
			
		} else {
			throw new SelfCloseTagWithoutInitialTagException(selfCloseTagNodeEntry);
		}
		this.elements.add(selfCloseTagNodeEntry);
	}
	
	
	
	
	public void add(XmlPrologNodeEntry nodeEntry)  {
		nodeEntry.setIdentifier(this.nextElementIndex++);
		this.prologElementId = this.elements.size(); 
		this.elements.add(nodeEntry);
	}
	
	public void add(XmlTagNodeEntry nodeEntry) {
		boolean isRoot = this.tagEntryStack.size()==0;
		XmlTagNodeEntry parentTagNodeEntry= getLastTagNodeEntry();
		nodeEntry.setIdentifier(this.nextElementIndex++);
		this.tagEntryStack.push(nodeEntry);
		this.elements.add(nodeEntry);
		// it is expected by the grammar that exists one only root node
		if (isRoot) {
			this.rootNodeIndex=this.elements.size()-1;
		} else {
			nodeEntry.setParentIndex(parentTagNodeEntry.getIdentifier());
			parentTagNodeEntry.addTagChild(nodeEntry);
		}
	}
	
	public void add(XmlNodeEntry nodeEntry)  {
		nodeEntry.setIdentifier(this.nextElementIndex++);
		XmlTagNodeEntry parentTagNodeEntry= getLastTagNodeEntry();
		if (parentTagNodeEntry!=null) {
			nodeEntry.setParentIndex(parentTagNodeEntry.getIdentifier());
			parentTagNodeEntry.addChild(nodeEntry);
		} 
		this.elements.add(nodeEntry);
	}


	
	/**
	 * Generates a string corresponding to this document
	 * @return
	 */
	public String generateSourceString() {
		StringBuilder result= new StringBuilder();
		for (XmlNodeEntry node: elements) {
			result.append(node.generateSourceString());
		}
		return result.toString();
	}
	
	
	
	
	public XmlTagNodeEntry getRoot() {
		return (XmlTagNodeEntry) this.elements.get(this.rootNodeIndex);
	}
	
	
	public String getBase64DocumentString() {
		StringBuilder result = new StringBuilder();
		byte[] originalBytes=this.getBinaryString().getBytes();
		int j=0;
		int nextContentStart=originalBytes.length;
		ContentItem contentItem=null;
		if (j<this.getBase64Content().size()) {
			contentItem=this.getBase64Content().get(j);
			nextContentStart=contentItem.getStart();
		}
		int i=0;
		while (i<originalBytes.length) {
			String nextText = new String(Arrays.copyOfRange(originalBytes, i,nextContentStart));
			result.append(nextText);
			i= nextContentStart;
			if (j<this.getBase64Content().size()) {
				nextText = contentItem.getBase64String();
				result.append(nextText);
				i= nextContentStart+contentItem.getLength();
				j++;
				if (j<this.getBase64Content().size()) {
					nextContentStart=this.getBase64Content().get(j).getStart();
					contentItem=this.getBase64Content().get(j);
				} else {
					nextContentStart=originalBytes.length;
					contentItem=null;
				}
			}
			
		}
		return result.toString();
	}

	
	

	public BinaryString getBinaryString() {
		return binaryString;
	}



	public void setBinaryString(BinaryString binaryString) {
		this.binaryString = binaryString;
	}




	
	
}
