package com.techstartingpoint.xmlcltool.commandparser;

import com.techstartingpoint.xmlcltool.commandparser.generated.CommandParser;
import com.techstartingpoint.xmlcltool.commandparser.generated.CommandParserBaseListener;
import com.techstartingpoint.xmlcltool.util.VerbosePrinter;

public class SelectorListener extends CommandParserBaseListener {

    VerbosePrinter printer;
    SelectorExpression selector;

    public SelectorListener(boolean verbose) {
    	this.printer=new VerbosePrinter(verbose);
    	this.selector = new SelectorExpression();
    }
	
	@Override 
	public void enterSelectorIndex(CommandParser.SelectorIndexContext ctx) {
		this.printer.println("enterSelectorIndex -----------");
		this.printer.println(ctx.getText());
		this.selector.setIndex(new Integer(ctx.getText()));
	}

	@Override 
	public void enterSelectorName(CommandParser.SelectorNameContext ctx) { 
		this.printer.println("enterSelectorName -----------");
		this.printer.println(ctx.getText());
		this.selector.add(ctx.getText());
	}
	
	@Override 
	public void enterAttributeName(CommandParser.AttributeNameContext ctx) { 
		this.printer.println("enterAttributeName -----------");
		this.printer.println(ctx.getText());
		this.selector.setAttribute(ctx.getText());
	}

	// GETTERS AND SETTERS
	public VerbosePrinter getPrinter() {
		return printer;
	}

	public SelectorExpression getSelector() {
		return selector;
	}
}
