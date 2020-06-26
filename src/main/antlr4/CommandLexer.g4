lexer grammar CommandLexer;


DIGITS
    : [0-9]+
    ;

ROOT_SELECTOR_TOKEN
    : '//'
    ;
    
RELATIVE_SELECTOR_TOKEN : '/' ;

OPEN_BRACKET: '[' ;
CLOSE_BRACKET: ']';

fragment
DIGIT       :   [0-9] ;

fragment
NameChar    :   NameStartChar
            |   '-' | '_' | '.' | DIGIT
            |   '\u00B7'
            |   '\u0300'..'\u036F'
            |   '\u203F'..'\u2040'
            ;

fragment
NameStartChar
            :   [:a-zA-Z]
            |   '\u2070'..'\u218F'
            |   '\u2C00'..'\u2FEF'
            |   '\u3001'..'\uD7FF'
            |   '\uF900'..'\uFDCF'
            |   '\uFDF0'..'\uFFFD'
            ;



Name        :   NameStartChar NameChar* ;

ATTRIBUTE_SELECTOR_CHAR : '@' ;
