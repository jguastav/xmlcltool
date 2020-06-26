// Our grammar is called C3PO.
parser grammar CommandParser;

options { tokenVocab=CommandLexer; }


/*
java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample/java.net.SQL” -s -> get the node

java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -d -> delete node

java -jar your-programmed-jar -file testxstream.txt -selector “//testSQL/Sample[2]/queryString” -update “new text inside”

*/


// We define expression to be either a method call or a string.

selectorIndex
    : DIGITS
    ;


attributeName
    : Name
    ;
    
selectorName
	: Name
	;

selector
    : nodeSelector
    | attributeSelector
    ;

nodeSelector
    : rootSelector
    | rootSelector (nodeSelectorItem)*
    ;
    
attributeSelector
    : nodeSelector ATTRIBUTE_SELECTOR_CHAR attributeName
    ;
    
rootSelector    
    : ROOT_SELECTOR_TOKEN selectorName
    | ROOT_SELECTOR_TOKEN selectorName selectorItem
    ;
    
nodeSelectorItem
    : RELATIVE_SELECTOR_TOKEN selectorName
    | RELATIVE_SELECTOR_TOKEN selectorName selectorItem
    ;
    
    
selectorItem
	: OPEN_BRACKET selectorIndex CLOSE_BRACKET
	;
	
