package com.techstartingpoint.xmlcltool.commandparser.generated;
// Generated from CommandParser.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CommandParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DIGITS=1, ROOT_SELECTOR_TOKEN=2, RELATIVE_SELECTOR_TOKEN=3, OPEN_BRACKET=4, 
		CLOSE_BRACKET=5, Name=6, ATTRIBUTE_SELECTOR_CHAR=7;
	public static final int
		RULE_selectorIndex = 0, RULE_attributeName = 1, RULE_selectorName = 2, 
		RULE_selector = 3, RULE_nodeSelector = 4, RULE_attributeSelector = 5, 
		RULE_rootSelector = 6, RULE_nodeSelectorItem = 7, RULE_selectorItem = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"selectorIndex", "attributeName", "selectorName", "selector", "nodeSelector", 
			"attributeSelector", "rootSelector", "nodeSelectorItem", "selectorItem"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'//'", "'/'", "'['", "']'", null, "'@'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "DIGITS", "ROOT_SELECTOR_TOKEN", "RELATIVE_SELECTOR_TOKEN", "OPEN_BRACKET", 
			"CLOSE_BRACKET", "Name", "ATTRIBUTE_SELECTOR_CHAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CommandParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CommandParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class SelectorIndexContext extends ParserRuleContext {
		public TerminalNode DIGITS() { return getToken(CommandParser.DIGITS, 0); }
		public SelectorIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectorIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterSelectorIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitSelectorIndex(this);
		}
	}

	public final SelectorIndexContext selectorIndex() throws RecognitionException {
		SelectorIndexContext _localctx = new SelectorIndexContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_selectorIndex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			match(DIGITS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeNameContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(CommandParser.Name, 0); }
		public AttributeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterAttributeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitAttributeName(this);
		}
	}

	public final AttributeNameContext attributeName() throws RecognitionException {
		AttributeNameContext _localctx = new AttributeNameContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_attributeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(Name);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectorNameContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(CommandParser.Name, 0); }
		public SelectorNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectorName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterSelectorName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitSelectorName(this);
		}
	}

	public final SelectorNameContext selectorName() throws RecognitionException {
		SelectorNameContext _localctx = new SelectorNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_selectorName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			match(Name);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectorContext extends ParserRuleContext {
		public NodeSelectorContext nodeSelector() {
			return getRuleContext(NodeSelectorContext.class,0);
		}
		public AttributeSelectorContext attributeSelector() {
			return getRuleContext(AttributeSelectorContext.class,0);
		}
		public SelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitSelector(this);
		}
	}

	public final SelectorContext selector() throws RecognitionException {
		SelectorContext _localctx = new SelectorContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_selector);
		try {
			setState(26);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(24);
				nodeSelector();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(25);
				attributeSelector();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeSelectorContext extends ParserRuleContext {
		public RootSelectorContext rootSelector() {
			return getRuleContext(RootSelectorContext.class,0);
		}
		public List<NodeSelectorItemContext> nodeSelectorItem() {
			return getRuleContexts(NodeSelectorItemContext.class);
		}
		public NodeSelectorItemContext nodeSelectorItem(int i) {
			return getRuleContext(NodeSelectorItemContext.class,i);
		}
		public NodeSelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeSelector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterNodeSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitNodeSelector(this);
		}
	}

	public final NodeSelectorContext nodeSelector() throws RecognitionException {
		NodeSelectorContext _localctx = new NodeSelectorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_nodeSelector);
		int _la;
		try {
			setState(36);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(28);
				rootSelector();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(29);
				rootSelector();
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==RELATIVE_SELECTOR_TOKEN) {
					{
					{
					setState(30);
					nodeSelectorItem();
					}
					}
					setState(35);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeSelectorContext extends ParserRuleContext {
		public NodeSelectorContext nodeSelector() {
			return getRuleContext(NodeSelectorContext.class,0);
		}
		public TerminalNode ATTRIBUTE_SELECTOR_CHAR() { return getToken(CommandParser.ATTRIBUTE_SELECTOR_CHAR, 0); }
		public AttributeNameContext attributeName() {
			return getRuleContext(AttributeNameContext.class,0);
		}
		public AttributeSelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeSelector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterAttributeSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitAttributeSelector(this);
		}
	}

	public final AttributeSelectorContext attributeSelector() throws RecognitionException {
		AttributeSelectorContext _localctx = new AttributeSelectorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_attributeSelector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			nodeSelector();
			setState(39);
			match(ATTRIBUTE_SELECTOR_CHAR);
			setState(40);
			attributeName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RootSelectorContext extends ParserRuleContext {
		public TerminalNode ROOT_SELECTOR_TOKEN() { return getToken(CommandParser.ROOT_SELECTOR_TOKEN, 0); }
		public SelectorNameContext selectorName() {
			return getRuleContext(SelectorNameContext.class,0);
		}
		public SelectorItemContext selectorItem() {
			return getRuleContext(SelectorItemContext.class,0);
		}
		public RootSelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rootSelector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterRootSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitRootSelector(this);
		}
	}

	public final RootSelectorContext rootSelector() throws RecognitionException {
		RootSelectorContext _localctx = new RootSelectorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_rootSelector);
		try {
			setState(48);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				match(ROOT_SELECTOR_TOKEN);
				setState(43);
				selectorName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				match(ROOT_SELECTOR_TOKEN);
				setState(45);
				selectorName();
				setState(46);
				selectorItem();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeSelectorItemContext extends ParserRuleContext {
		public TerminalNode RELATIVE_SELECTOR_TOKEN() { return getToken(CommandParser.RELATIVE_SELECTOR_TOKEN, 0); }
		public SelectorNameContext selectorName() {
			return getRuleContext(SelectorNameContext.class,0);
		}
		public SelectorItemContext selectorItem() {
			return getRuleContext(SelectorItemContext.class,0);
		}
		public NodeSelectorItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeSelectorItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterNodeSelectorItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitNodeSelectorItem(this);
		}
	}

	public final NodeSelectorItemContext nodeSelectorItem() throws RecognitionException {
		NodeSelectorItemContext _localctx = new NodeSelectorItemContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_nodeSelectorItem);
		try {
			setState(56);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				match(RELATIVE_SELECTOR_TOKEN);
				setState(51);
				selectorName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				match(RELATIVE_SELECTOR_TOKEN);
				setState(53);
				selectorName();
				setState(54);
				selectorItem();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectorItemContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACKET() { return getToken(CommandParser.OPEN_BRACKET, 0); }
		public SelectorIndexContext selectorIndex() {
			return getRuleContext(SelectorIndexContext.class,0);
		}
		public TerminalNode CLOSE_BRACKET() { return getToken(CommandParser.CLOSE_BRACKET, 0); }
		public SelectorItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectorItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).enterSelectorItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommandParserListener ) ((CommandParserListener)listener).exitSelectorItem(this);
		}
	}

	public final SelectorItemContext selectorItem() throws RecognitionException {
		SelectorItemContext _localctx = new SelectorItemContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_selectorItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(OPEN_BRACKET);
			setState(59);
			selectorIndex();
			setState(60);
			match(CLOSE_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\tA\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\3"+
		"\3\3\3\4\3\4\3\5\3\5\5\5\35\n\5\3\6\3\6\3\6\7\6\"\n\6\f\6\16\6%\13\6\5"+
		"\6\'\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b\63\n\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\5\t;\n\t\3\n\3\n\3\n\3\n\3\n\2\2\13\2\4\6\b\n\f\16\20\22"+
		"\2\2\2<\2\24\3\2\2\2\4\26\3\2\2\2\6\30\3\2\2\2\b\34\3\2\2\2\n&\3\2\2\2"+
		"\f(\3\2\2\2\16\62\3\2\2\2\20:\3\2\2\2\22<\3\2\2\2\24\25\7\3\2\2\25\3\3"+
		"\2\2\2\26\27\7\b\2\2\27\5\3\2\2\2\30\31\7\b\2\2\31\7\3\2\2\2\32\35\5\n"+
		"\6\2\33\35\5\f\7\2\34\32\3\2\2\2\34\33\3\2\2\2\35\t\3\2\2\2\36\'\5\16"+
		"\b\2\37#\5\16\b\2 \"\5\20\t\2! \3\2\2\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2"+
		"$\'\3\2\2\2%#\3\2\2\2&\36\3\2\2\2&\37\3\2\2\2\'\13\3\2\2\2()\5\n\6\2)"+
		"*\7\t\2\2*+\5\4\3\2+\r\3\2\2\2,-\7\4\2\2-\63\5\6\4\2./\7\4\2\2/\60\5\6"+
		"\4\2\60\61\5\22\n\2\61\63\3\2\2\2\62,\3\2\2\2\62.\3\2\2\2\63\17\3\2\2"+
		"\2\64\65\7\5\2\2\65;\5\6\4\2\66\67\7\5\2\2\678\5\6\4\289\5\22\n\29;\3"+
		"\2\2\2:\64\3\2\2\2:\66\3\2\2\2;\21\3\2\2\2<=\7\6\2\2=>\5\2\2\2>?\7\7\2"+
		"\2?\23\3\2\2\2\7\34#&\62:";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}