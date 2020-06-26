package com.techstartingpoint.xmlcltool.util;

public class VerbosePrinter {
	private final int MAX_VERBOSE_STRINGS_LENGTH=50;
    private boolean verbose;
    private int verboseLength=MAX_VERBOSE_STRINGS_LENGTH;
    private int tabSpaces =0;
	
    
    public VerbosePrinter(boolean verbose) {
    	this.verbose=verbose;
    }
    
    public void println(String stringToPrint) {
    	if (verbose) {
    		String spaces = new String(new char[this.tabSpaces]).replace("\0", "    ");
    		
	    	if (stringToPrint!=null) {
	    		if (stringToPrint.length()>verboseLength) {
	    			int remainingCharacters=stringToPrint.length()-verboseLength;
	    			stringToPrint.replace("\n", "\n"+spaces+spaces);
	    			System.out.println(spaces+stringToPrint.substring(0,verboseLength)+"... +"+remainingCharacters+" chars"); 
	    		} else {
	    			System.out.println(spaces+stringToPrint); 
	    		}
	    	} else {
	    		System.out.println();
	    	}
    		
    	}
    }
    
    public void println(int position,String stringToPrint, int additionalSpaces) {
		String spaces = new String(new char[additionalSpaces]).replace("\0", "    ");
    	println(spaces+"("+position+") "+stringToPrint);
    }

    
    public void println(int position,String stringToPrint) {
    	println("("+position+") "+stringToPrint);
    }
    
    public void println(String stringToPrint,int additionalSpaces) {
		String spaces = new String(new char[additionalSpaces]).replace("\0", "    ");
		println(spaces+stringToPrint);
    }

    public void incrementTab() {
    	this.tabSpaces++;
    }
    
    public void decrementTab() {
		this.tabSpaces-=1;
		if (tabSpaces<0) {
			// TODO: Check if it should throw an exception
			tabSpaces=0;
		}
    }
}
