package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.ArrayList;
import java.util.List;

import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.commandparser.SelectorItem;
import com.techstartingpoint.xmlcltool.executor.Operation;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.AttributeOutOfTagException;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.ClosingNotOpenedTagException;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.EndTagCharWithoutInitialTagException;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.SelfCloseTagWithoutInitialTagException;

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
	
	public XmlDocument() {
		this.elements = new ArrayList<XmlNodeEntry>();
		this.nextElementIndex = 0;
		this.tagEntryStack = new XmlTagStack();
		this.prologElementId = NO_PROLOG;
		this.rootNodeIndex = SelectorItem.NO_INDEX;
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
	

	public DocumentPart selectValue(SelectorExpression selectExpression, boolean excludeQuotes, SelectionType selectionType, Operation operation) {
		DocumentPart documentPart = null; 
		boolean isRootNode = true;
		// temporal currentNode variable
		XmlTagNodeEntry currentNode = null;
		List<XmlTagNodeEntry> currentNodes= new ArrayList<XmlTagNodeEntry>();
		int index = 0;
		boolean errors = false;
		SelectorItem item = null;
		String result = null;
		while (index < selectExpression.getSelectors().size() && !errors && (currentNodes.size()>0 || isRootNode)) {
			item = selectExpression.getSelectors().get(index);
			String nodeName=item.getNodeName();
			if (isRootNode) {
				// get the root node
				currentNode = getRoot();
				if (currentNode!=null && nodeName.equals(currentNode.getTagName())) {
					currentNodes.add(currentNode);
				};
				isRootNode=false;
			} else {
				// choose the nodes between the childs of currentNodes
				currentNodes = XmlTagNodeEntry.getChildren(currentNodes,nodeName);
			}
			// select index
			int itemIndex = item.getIndex();
			if (itemIndex!=SelectorItem.NO_INDEX) {
				if (itemIndex<currentNodes.size()) {
					currentNode = currentNodes.get(itemIndex);
					currentNodes=new ArrayList<XmlTagNodeEntry>();
					currentNodes.add(currentNode);
				} else {
					currentNodes=new ArrayList<XmlTagNodeEntry>();
				}
			}
			index++;
		}
		// select attribute (only the last node according to grammar)
		if (item!=null && currentNodes.size()>0) {
			if (item.getAttribute()!=null) {
				// get the attribute
				if (Operation.SELECT.equals(operation) && currentNodes.size()!=1) {
					documentPart = generateSelectCount(currentNodes.size());
				} else {
					documentPart = currentNodes.get(0).getAttributeValueByName(item.getAttribute(),excludeQuotes,selectionType);
				}
			} else {
				if (selectionType.equals(SelectionType.CONTENT)) {
					if (Operation.SELECT.equals(operation) && currentNodes.size()!=1) {
						documentPart = generateSelectCount(currentNodes.size());
					} else {
						// get the content of the first element
						documentPart = currentNodes.get(0).getContent();
					}
				} else if (selectionType.equals(SelectionType.ELEMENT)) {
					documentPart = new DocumentPart(currentNodes.get(0).getStart(),currentNodes.get(0).generateFullSourceString());
				} else if (selectionType.equals(SelectionType.AFTER)) {
					String fullNodeText = currentNodes.get(0).generateFullSourceString();
					// in this case 2nd argument of DocumentPart is present only as a reference. The position is the position of the final of the string
					documentPart = new DocumentPart(currentNodes.get(0).getStart()+fullNodeText.length(),fullNodeText);
				}
			}
		} else {
			documentPart = generateSelectCount(0);
		}  
		return documentPart;
	}
	
	private DocumentPart generateSelectCount(int size) {
		DocumentPart result = new DocumentPart(size);
		return result;
	}
	

	public XmlTagNodeEntry getRoot() {
		return (XmlTagNodeEntry) this.elements.get(this.rootNodeIndex);
	}

	
	
}
