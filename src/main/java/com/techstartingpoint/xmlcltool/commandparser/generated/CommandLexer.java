package com.techstartingpoint.xmlcltool.commandparser.generated;
// Generated from CommandLexer.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CommandLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DIGITS=1, ROOT_SELECTOR_TOKEN=2, RELATIVE_SELECTOR_TOKEN=3, OPEN_BRACKET=4, 
		CLOSE_BRACKET=5, Name=6, ATTRIBUTE_SELECTOR_CHAR=7;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"DIGITS", "ROOT_SELECTOR_TOKEN", "RELATIVE_SELECTOR_TOKEN", "OPEN_BRACKET", 
			"CLOSE_BRACKET", "DIGIT", "NameChar", "NameStartChar", "Name", "ATTRIBUTE_SELECTOR_CHAR"
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


	public CommandLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CommandLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\t9\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\6\2\31\n\2\r\2\16\2\32\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7"+
		"\3\7\3\b\3\b\3\b\3\b\5\b,\n\b\3\t\5\t/\n\t\3\n\3\n\7\n\63\n\n\f\n\16\n"+
		"\66\13\n\3\13\3\13\2\2\f\3\3\5\4\7\5\t\6\13\7\r\2\17\2\21\2\23\b\25\t"+
		"\3\2\6\3\2\62;\4\2/\60aa\5\2\u00b9\u00b9\u0302\u0371\u2041\u2042\n\2<"+
		"<C\\c|\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\2:"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\3\30\3\2\2\2\5\34\3\2\2\2\7\37\3\2\2\2\t!\3\2\2\2\13"+
		"#\3\2\2\2\r%\3\2\2\2\17+\3\2\2\2\21.\3\2\2\2\23\60\3\2\2\2\25\67\3\2\2"+
		"\2\27\31\t\2\2\2\30\27\3\2\2\2\31\32\3\2\2\2\32\30\3\2\2\2\32\33\3\2\2"+
		"\2\33\4\3\2\2\2\34\35\7\61\2\2\35\36\7\61\2\2\36\6\3\2\2\2\37 \7\61\2"+
		"\2 \b\3\2\2\2!\"\7]\2\2\"\n\3\2\2\2#$\7_\2\2$\f\3\2\2\2%&\t\2\2\2&\16"+
		"\3\2\2\2\',\5\21\t\2(,\t\3\2\2),\5\r\7\2*,\t\4\2\2+\'\3\2\2\2+(\3\2\2"+
		"\2+)\3\2\2\2+*\3\2\2\2,\20\3\2\2\2-/\t\5\2\2.-\3\2\2\2/\22\3\2\2\2\60"+
		"\64\5\21\t\2\61\63\5\17\b\2\62\61\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2"+
		"\64\65\3\2\2\2\65\24\3\2\2\2\66\64\3\2\2\2\678\7B\2\28\26\3\2\2\2\7\2"+
		"\32+.\64\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}