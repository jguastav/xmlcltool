package com.techstartingpoint.xmlcltool.commandparser.generated;
// Generated from CommandParser.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CommandParser}.
 */
public interface CommandParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CommandParser#selectorIndex}.
	 * @param ctx the parse tree
	 */
	void enterSelectorIndex(CommandParser.SelectorIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#selectorIndex}.
	 * @param ctx the parse tree
	 */
	void exitSelectorIndex(CommandParser.SelectorIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommandParser#attributeName}.
	 * @param ctx the parse tree
	 */
	void enterAttributeName(CommandParser.AttributeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#attributeName}.
	 * @param ctx the parse tree
	 */
	void exitAttributeName(CommandParser.AttributeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommandParser#selectorName}.
	 * @param ctx the parse tree
	 */
	void enterSelectorName(CommandParser.SelectorNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#selectorName}.
	 * @param ctx the parse tree
	 */
	void exitSelectorName(CommandParser.SelectorNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommandParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(CommandParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(CommandParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommandParser#nodeSelector}.
	 * @param ctx the parse tree
	 */
	void enterNodeSelector(CommandParser.NodeSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#nodeSelector}.
	 * @param ctx the parse tree
	 */
	void exitNodeSelector(CommandParser.NodeSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommandParser#attributeSelector}.
	 * @param ctx the parse tree
	 */
	void enterAttributeSelector(CommandParser.AttributeSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#attributeSelector}.
	 * @param ctx the parse tree
	 */
	void exitAttributeSelector(CommandParser.AttributeSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommandParser#rootSelector}.
	 * @param ctx the parse tree
	 */
	void enterRootSelector(CommandParser.RootSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#rootSelector}.
	 * @param ctx the parse tree
	 */
	void exitRootSelector(CommandParser.RootSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommandParser#nodeSelectorItem}.
	 * @param ctx the parse tree
	 */
	void enterNodeSelectorItem(CommandParser.NodeSelectorItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#nodeSelectorItem}.
	 * @param ctx the parse tree
	 */
	void exitNodeSelectorItem(CommandParser.NodeSelectorItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommandParser#selectorItem}.
	 * @param ctx the parse tree
	 */
	void enterSelectorItem(CommandParser.SelectorItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommandParser#selectorItem}.
	 * @param ctx the parse tree
	 */
	void exitSelectorItem(CommandParser.SelectorItemContext ctx);
}