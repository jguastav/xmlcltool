/**
List of detected errors and index changes 
ERROR: 01
line 180:104 token recognition error at: '&quot\n'
line 181:0 mismatched input 'reÁm' expecting {COMMENT, CDATA, EntityRef, CharRef, '<', PI}



*/


parser grammar XMLParser;

options { tokenVocab=XMLLexer; }

document    :   prolog? misc* element misc*;

prolog      :   XMLDeclOpen attribute* SPECIAL_CLOSE ;


content     :   chardata?
               ((element | reference | CDATA | PI | COMMENT) chardata?)* ;


element     :   '<' Name attribute* '>' content '<' '/' Name '>'
            |   '<' Name attribute* '/>'
            ;

// change ERROR: 01 
// reference   :   EntityRef | CharRef ;
reference   :   CharRef ;

attribute   :   Name '=' STRING ; // Our STRING is AttValue in spec

/** ``All text that is not markup constitutes the character data of
 *  the document.''
 */
chardata    :   TEXT | SEA_WS ;

misc        :   COMMENT | PI | SEA_WS ;