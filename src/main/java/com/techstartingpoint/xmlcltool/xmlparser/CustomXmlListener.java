package com.techstartingpoint.xmlcltool.xmlparser;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.techstartingpoint.xmlcltool.util.VerbosePrinter;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.AttributeOutOfTagException;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.ClosingNotOpenedTagException;
import com.techstartingpoint.xmlcltool.xmlparser.exceptions.EndTagCharWithoutInitialTag;
import com.techstartingpoint.xmlcltool.xmlparser.generated.XMLParser;
import com.techstartingpoint.xmlcltool.xmlparser.generated.XMLParserBaseListener;


/**
 * The main goal of this class is to build the Data Model while walking the Document
 * @author Jose Alberto Guastavino
 *
 */
public class CustomXmlListener extends XMLParserBaseListener{

		
		private final String OPEN_TAG_CHAR = "<";
		private final String CLOSE_TAG_CHAR = ">";
		private final String EXPECTING_NONE = "";
		private final String FINAL_TAG_CHAR = "/";
		private final String PROLOG_OPEN = "<?xml ";
		private final String PROLOG_CLOSE = "?>";
		
		private final String TAG_NAME_EXPECTED = "TAG-NAME-EXPECTED";
		
		private final String ATTRIBUTE_KEY = "KEY";
		private final String ATTRIBUTE_VALUE = "VALUE";
		private final String EQUALS_CHAR = "=";
		
		
	
	    private List<String> errors = new ArrayList<>();
	    XmlDocument document;
	    VerbosePrinter printer;
	    
	    /**
	     * element being processed being prepared to be added to the document
	     * It would be added as terminal nodes of the grammar are visited
	     * Not always 
	     */
	    XmlTagNodeEntry currentTagNode;
	    /**
	     * temporal closing tag node while terminal items are jointed
	     */
	    String currentClosingTagName=null;
	    
	    /**
	     * Stack of open tags waiting for being closed while adding elements to the document
	     * it is replicated here relative to xmlDocument but the sense is different
	     * Here Content will be an indication of child nodes . Instead in XmlDocument the importance is only about Tags
	     * XmlTagStack would have only Opening Tags
	     */
		private XmlNodeStack xmlNodeStack;
		
	    private XmlAttributeNodeEntry currentAttributeNode;
	    
	    
	    
	    
	    /**
	     * String being changed when a syntax element comes in more than one terminal elements
	     * e.g. a tag comes in the form of
	     * 1.- <
	     * 2.- tagName
	     * then it should be added the tagName after the visit of the 2nd terminal node
	     */
	    private String tagExpectedToken="";
	    
	    /**
	     * Flags indicating what is being expected while parsing the current tag
	     * the values will be in sequence
	     * ATTRIBUTE_KEY
	     * ATTRIBUTE_EQUALS
	     * ATTRIBUTE_VALUE
	     */
	    private String attributeExpectedToken = "";
	    
	    /**
	     * property to save text getting each token of the closing tag
	     */
	    String currentCompleteTagClosingString = null;

	    /**
	     * Position in which begins currentClosingString
	     */
	    int closingStringPosition = -1;
	    
	    /**
	     * It is setted to true when exitContent is reached
	     */
	    boolean expectingCloseTag = false;
	    
	    
	    boolean expectingProlog = false;
	    
	    private int getStartPosition(ParserRuleContext context) {
	    	return context.start.getStartIndex();
	    }
	    
	    
	    private int getStartPosition(TerminalNode node) {
			return node.getSymbol().getStartIndex();

	    }
	    
	    public CustomXmlListener(boolean verbose) {
	    	this.printer=new VerbosePrinter(verbose);
	    	this.xmlNodeStack = new XmlNodeStack();
	    }
	 
  
	    @Override
	    public void enterDocument(XMLParser.DocumentContext ctx) {
	    	this.document= new XmlDocument();
	    	this.xmlNodeStack= new XmlNodeStack();
	    	this.currentAttributeNode = null;
	    	this.currentTagNode = null;

	    	this.printer.println("enterDocument---------");
			this.printer.println(ctx.getText());
	    }

	    
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitDocument(XMLParser.DocumentContext ctx) { 
			// TODO: Check stack is empty. 
			// IF not throw exception
				this.printer.println("exitDocument");
				this.printer.println(ctx.getText());
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 * @throws ClosingNotOpenedTagException 
		 */

		@Override 
		public void enterProlog(XMLParser.PrologContext ctx)  {
			this.printer.println("enterProlog");
			int position = 	getStartPosition(ctx);
			this.printer.println(position,ctx.getText());
			
			this.expectingProlog=true;
			this.tagExpectedToken = PROLOG_OPEN;
			this.printer.println("enterProlog-------------");
			this.printer.println(position,ctx.getText());
		}
		
		
		

		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 * 
		 * Element identifies TAGS in this grammar
		 */
		@Override public void enterElement(XMLParser.ElementContext ctx) {
			this.printer.incrementTab();
			
			int position = getStartPosition(ctx);
			this.currentTagNode = new XmlTagNodeEntry(position);
			this.xmlNodeStack.push(this.currentTagNode);
			this.tagExpectedToken = OPEN_TAG_CHAR;

			this.printer.println("enterElement-------------");
			this.printer.println(position,ctx.getText());
		}
		
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitElement(XMLParser.ElementContext ctx) { 
			this.printer.println("exitElement-------------");
			this.printer.println(ctx.getText());
			this.printer.decrementTab();
			// only to ensure the element is what it was expected
			XmlTagNodeEntry lastElement=(XmlTagNodeEntry) this.xmlNodeStack.pop();
			
			
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void enterReference(XMLParser.ReferenceContext ctx) { 
			int position = getStartPosition(ctx);

			this.printer.println("enterReference-------------");
			this.printer.println(getStartPosition(ctx),ctx.getText());
			this.xmlNodeStack.push(new XmlTextNodeEntry(ctx.getText(),position));

			
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitReference(XMLParser.ReferenceContext ctx) {
			this.printer.println("exitReference-------------");
			this.printer.println(ctx.getText());
			XmlTextNodeEntry lastElement = (XmlTextNodeEntry) this.xmlNodeStack.pop();
			
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void enterAttribute(XMLParser.AttributeContext ctx) {
			int position = getStartPosition(ctx);
			String nodeText = ctx.getText();
			this.currentAttributeNode= new XmlAttributeNodeEntry(nodeText,position,this.expectingProlog);
			this.attributeExpectedToken = ATTRIBUTE_KEY;
			this.xmlNodeStack.push(this.currentAttributeNode);
			this.printer.println("enterAttribute-------------");
			this.printer.println(getStartPosition(ctx), ctx.getText());
			
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitAttribute(XMLParser.AttributeContext ctx) {
			if (this.currentTagNode!=null || this.expectingProlog) {
				// attribute is added here because it could be attributes without explicit = value part
				try {
					this.document.add(this.currentAttributeNode);
				} catch (AttributeOutOfTagException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				this.currentAttributeNode=null;
				this.attributeExpectedToken=EXPECTING_NONE;
				XmlAttributeNodeEntry lastElement = (XmlAttributeNodeEntry) this.xmlNodeStack.pop();
				
			} else {
				// TODO: Throw exception
			}
			this.printer.println("exitAttribute-------------");
			this.printer.println(ctx.getText());
			
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void enterChardata(XMLParser.ChardataContext ctx) {
			int position = getStartPosition(ctx);
			String nodeText = ctx.getText();
			this.printer.println("enterCharData-------------");
			this.printer.println(position,nodeText);

			XmlTextNodeEntry nodeEntry= new XmlTextNodeEntry(nodeText,position);
			this.document.add(nodeEntry);
			this.xmlNodeStack.push(nodeEntry);
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitChardata(XMLParser.ChardataContext ctx) { 
			this.printer.println("exitCharData-------------");
			this.printer.println(ctx.getText());
			XmlTextNodeEntry lastElement = (XmlTextNodeEntry) this.xmlNodeStack.pop();
		
			
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void enterMisc(XMLParser.MiscContext ctx) {
			this.printer.println("enterMisc-------------");
			int position = getStartPosition(ctx);
			this.printer.println(position,ctx.getText());
			XmlTextNodeEntry nodeEntry= new XmlTextNodeEntry(ctx.getText(),position);
			this.document.add(nodeEntry);
			this.xmlNodeStack.push(nodeEntry);
			
		}

		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void visitTerminal(TerminalNode node) {
			int position = getStartPosition(node);
			String nodeText = node.getText();
			// opening prolog
			if (this.expectingProlog && this.tagExpectedToken.equals(PROLOG_OPEN)) {
				XmlPrologNodeEntry nodeEntry= new XmlPrologNodeEntry(nodeText,position);
				this.document.add(nodeEntry);
				this.tagExpectedToken = PROLOG_CLOSE;
				// opening tag
			} else if (this.currentTagNode!=null && nodeText.equals(OPEN_TAG_CHAR) && this.tagExpectedToken.equals(OPEN_TAG_CHAR)) {
				this.tagExpectedToken = EXPECTING_NONE;
				
			// after opening Tag - set the name of the tag
			} else if (this.currentTagNode!=null && 
						this.tagExpectedToken.equals(EXPECTING_NONE)) {
					this.currentTagNode.setSourceValue(nodeText);
					this.currentTagNode.setTagName(nodeText);
					
					// It is only added to the document after knowing which specific tag is
					this.document.add(this.currentTagNode);
					this.tagExpectedToken = CLOSE_TAG_CHAR;
					// closing open tag
			} else if (this.currentTagNode!=null && 
					nodeText.equals(CLOSE_TAG_CHAR) && 
					this.tagExpectedToken.equals(CLOSE_TAG_CHAR)) {
				// no more attributes are being expected
				this.currentTagNode = null;
				this.tagExpectedToken=EXPECTING_NONE;
				try {
					this.document.add(new XmlEndTagCharNodeEntry(position));
				} catch (EndTagCharWithoutInitialTag e) {
					throw new RuntimeException(e);
				}
				// close prolog tag
			} else if (this.expectingProlog && this.tagExpectedToken.equals(PROLOG_CLOSE) && nodeText.equals(PROLOG_CLOSE)) {
				this.expectingProlog= false;
				this.tagExpectedToken= EXPECTING_NONE;
				this.document.add(new XmlEndPrologNodeEntry(position));
				// begin to receive new attribute
			} else if ((this.tagExpectedToken.equals(CLOSE_TAG_CHAR) || 
					this.tagExpectedToken.equals(PROLOG_CLOSE)) 
					&& this.attributeExpectedToken.equals(ATTRIBUTE_KEY)) {
				((XmlAttributeNodeEntry) this.currentAttributeNode).setKey(nodeText);
				this.attributeExpectedToken=EQUALS_CHAR;
				// receive equal of an attribute
			} else if ((this.tagExpectedToken.equals(CLOSE_TAG_CHAR) || 
					this.tagExpectedToken.equals(PROLOG_CLOSE)) && 
					this.attributeExpectedToken.equals(EQUALS_CHAR)) {
				this.attributeExpectedToken=ATTRIBUTE_VALUE;
				// receiving value of an attribute
			} else if ((this.tagExpectedToken.equals(CLOSE_TAG_CHAR) || 
					this.tagExpectedToken.equals(PROLOG_CLOSE)) && 
					this.attributeExpectedToken.equals(ATTRIBUTE_VALUE)) {
				((XmlAttributeNodeEntry) this.currentAttributeNode).setValue(nodeText);
				this.attributeExpectedToken=EXPECTING_NONE;
			} else {
				XmlNodeEntry currentEntry=this.xmlNodeStack.peek();
				// if it is a tag.. the values should be the closing tag
				if (this.expectingCloseTag) {
					if (this.tagExpectedToken.equals(OPEN_TAG_CHAR) && OPEN_TAG_CHAR.equals(nodeText)) {
						if (this.closingStringPosition == -1) {
							this.closingStringPosition = getStartPosition(node);
						}
						this.tagExpectedToken = FINAL_TAG_CHAR;
						this.currentCompleteTagClosingString = nodeText;
					} else if (this.tagExpectedToken.equals(FINAL_TAG_CHAR) && FINAL_TAG_CHAR.equals(nodeText)) {
						this.tagExpectedToken = TAG_NAME_EXPECTED;
						this.currentCompleteTagClosingString +=nodeText;
						// expecting name of closing tag
					} else if (this.tagExpectedToken.equals(TAG_NAME_EXPECTED)) {
						// it is expected tha current tag name be equals to the las tag name in the stack
						this.currentCompleteTagClosingString +=nodeText;
						this.currentClosingTagName = nodeText;
						this.tagExpectedToken = CLOSE_TAG_CHAR;
						// finishing close tag
					} else if (this.tagExpectedToken.equals(CLOSE_TAG_CHAR) && CLOSE_TAG_CHAR.equals(nodeText)) {
						this.currentCompleteTagClosingString += nodeText;
						this.tagExpectedToken=EXPECTING_NONE;
						XmlCloseTagNodeEntry newEndTag = new XmlCloseTagNodeEntry(this.currentCompleteTagClosingString,this.currentClosingTagName,this.closingStringPosition);
						
						try {
							this.document.add(newEndTag);
						} catch (ClosingNotOpenedTagException e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						} 
						
						this.closingStringPosition=-1;
						this.expectingCloseTag=false;
						
					} 
				}
			}
			
			// TODO: remove from stack tag when ending correspoding closing tag 
			
			
			this.printer.println("visitTerminal-------------",2);
			this.printer.println(position,node.getText(),2);
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void visitErrorNode(ErrorNode node) { 
			this.printer.println("visitErrorNode-------------");
			this.printer.println(node.getText());
			
		}
	    

		// ------ getters and setters
	    
	    
	    
		public List<String> getErrors() {
			return errors;
		}


		public XmlDocument getDocument() {
			return document;
		}


		// NOT BEING USED
		
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitProlog(XMLParser.PrologContext ctx) {
			
			this.printer.println("exitProlog-------------");
			this.printer.println(getStartPosition(ctx),ctx.getText());

		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void enterContent(XMLParser.ContentContext ctx) { 
			this.printer.println("enterContent-------------");
			this.printer.println(getStartPosition(ctx),ctx.getText());
			
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 * 
		 * When exitContent happens the next element is a closing tag
		 */
		@Override public void exitContent(XMLParser.ContentContext ctx) { 
			this.printer.println("exitContent-------------");
			this.printer.println(getStartPosition(ctx),ctx.getText());
			
			this.expectingCloseTag=true;
			
			this.tagExpectedToken = OPEN_TAG_CHAR;
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitMisc(XMLParser.MiscContext ctx) {
			this.printer.println("exitMisc-------------");
			this.printer.println(getStartPosition(ctx),ctx.getText());
			XmlTextNodeEntry lastElement = (XmlTextNodeEntry) this.xmlNodeStack.pop();
			
		}

		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void enterEveryRule(ParserRuleContext ctx) {
		}
		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitEveryRule(ParserRuleContext ctx) { 
			
		}
		
		
		
	    
	    
}
