package com.techstartingpoint.xmlcltool.model;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;

import com.techstartingpoint.xmlcltool.commandparser.SelectionType;
import com.techstartingpoint.xmlcltool.commandparser.SelectorExpression;
import com.techstartingpoint.xmlcltool.commandparser.SelectorItem;
import com.techstartingpoint.xmlcltool.commandparser.SelectorListener;
import com.techstartingpoint.xmlcltool.commandparser.generated.CommandLexer;
import com.techstartingpoint.xmlcltool.commandparser.generated.CommandParser;
import com.techstartingpoint.xmlcltool.executor.Operation;
import com.techstartingpoint.xmlcltool.xmlparser.DocumentPart;
import com.techstartingpoint.xmlcltool.xmlparser.XmlDocument;
import com.techstartingpoint.xmlcltool.xmlparser.XmlTagNodeEntry;

public class TinyXPath {
	SelectorExpression data;
	String selectorString;
	
	/**
	 * text label for text elements in node hierarchy
	 */
	private static final String TEXT_NODE_NAME = "#text"; 
	/**
	 * document label for text elements in node hierarchy
	 */
	private static final String DOCUMENT_NODE_NAME = "#document"; 
	private static final String SLASH_SEPARATOR = "/"; 
	
	public TinyXPath(String selectorString, boolean verbose) {
		
		
		this.selectorString=selectorString;
		CommandLexer xmlLexer = new CommandLexer(CharStreams.fromString(selectorString));
		
		CommonTokenStream tokens = new CommonTokenStream(xmlLexer);
		CommandParser parser = new CommandParser(tokens);
		ParseTree tree = parser.selector();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		SelectorListener listener= new SelectorListener(verbose);

		walker.walk(listener, tree);
		
		data = listener.getSelector();
		

	}

	public SelectorExpression getData() {
		return data;
	}

	public String getSelectorString() {
		return selectorString;
	}

	private static DocumentPart generateSelectCount(int size) {
		DocumentPart result = new DocumentPart(size);
		return result;
	}
	

	
	public DocumentPart selectValue(String xPathSelectExpression, boolean excludeQuotes, SelectionType selectionType, Operation operation) {
		// TODO: Implement
		return null;
	} 
	
	
	
	/**
	 * Generate a tiny xpath selector to find the specified node
	 * @param node
	 * 	The node to be found. 
	 * 	The result of applying the selector must be the node
	 * @return
	 * 	The tiny selector string of the form
	 * 	//<tag>[index]/<tag>[index-2]/<tag>[index-n]/<tag>@<attribute>
	 * or 
	 * 	//<tag>[index]/<tag>[index-2]/<tag>[index-n]/<tag>
	 */
	public static String getTinySelectorPath(Node node) {
		StringBuilder result = new StringBuilder();
		if (node!=null && !node.getNodeName().equals(TEXT_NODE_NAME)) {
			String nextNodeType = "main";
			Node lastNode = node;
			int sameTypeSiblings =1;
			String currentNodeType= node.getNodeName();
			String lastParentType = currentNodeType;
			while (lastNode!=null) {
				String nodeName = lastNode.getNodeName();
				if (!nodeName.equals(TEXT_NODE_NAME)) {
					// set sibling index
					if (nextNodeType.equals("sibling")) {
						if (nodeName.equals(currentNodeType)) {
							sameTypeSiblings++;
						}
					}
					if (nodeName.equals(DOCUMENT_NODE_NAME)) {
						String separator = SLASH_SEPARATOR;
						if (result.toString().startsWith("@")) {
							separator ="";
						}
						result=result.insert(0,SLASH_SEPARATOR+SLASH_SEPARATOR+lastParentType+separator);
					} else {
						if (nextNodeType.equals("main")) {
							if (lastNode.getNodeType()==Node.ATTRIBUTE_NODE) {
								result=new StringBuilder("@"+nodeName);
							}
						} else {
							if (nextNodeType.equals("parent")) {
								if (lastParentType!=null) {
									String separator = SLASH_SEPARATOR;
									if (result.toString().startsWith("@") || result.toString().length()==0) {
										separator ="";
									}
									result=result.insert(0,lastParentType+"["+(sameTypeSiblings)+"]"+separator);
									sameTypeSiblings =1;
								}
								lastParentType=nodeName;
								currentNodeType = nodeName;
							}
							if (nextNodeType.equals("attributeParent")) {
								lastParentType = nodeName;
								currentNodeType = nodeName;
							}
						}
					}
				}

				// Choose next node and next node type
				Node nextNode = lastNode.getPreviousSibling();
				nextNodeType = "sibling";
				if (nextNode==null) {
					if (lastNode.getNodeType()==Node.ATTRIBUTE_NODE) {
						nextNode = ((Attr) lastNode).getOwnerElement();
						nextNodeType = "attributeParent";
					} else {
						nextNode = lastNode.getParentNode();
						nextNodeType = "parent";
					}
				}
				
				lastNode = nextNode;
			}
		}
		return result.toString();
		
	}
	
	

	
	
	public static DocumentPart selectValue(XmlDocument document,String tinyXPathQuery, boolean isVerbose,boolean excludeQuotes, SelectionType selectionType, Operation operation) {
		
		DocumentPart documentPart = null; 
		if (tinyXPathQuery.startsWith("/")) {
			SelectorExpression selectorExpression = new TinyXPath(tinyXPathQuery,isVerbose).getData();

			boolean isRootNode = true;
			// temporal currentNode variable
			XmlTagNodeEntry currentNode = null;
			List<XmlTagNodeEntry> currentNodes= new ArrayList<XmlTagNodeEntry>();
			int index = 0;
			boolean errors = false;
			SelectorItem item = null;
			while (index < selectorExpression.getSelectors().size() && !errors && (currentNodes.size()>0 || isRootNode)) {
				item = selectorExpression.getSelectors().get(index);
				String nodeName=item.getNodeName();
				if (isRootNode) {
					// get the root node
					currentNode = document.getRoot();
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
					// itemIndex begins in 1. not in 0
					if (itemIndex<=currentNodes.size()) {
						currentNode = currentNodes.get(itemIndex-1);
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
			
		} else {
			// expected an integer indicating the number or elements
			documentPart = generateSelectCount(new Integer(tinyXPathQuery));
		}
		
		return documentPart;
	}
	

}
