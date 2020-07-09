package com.techstartingpoint.xmlcltool.util;

import java.util.Arrays;

import com.techstartingpoint.xmlcltool.xmlparser.DocumentPart;

/**
 * Class to deal with special characters
 * The goal is to have a text string to manipulate 
 * It replace all special characters to "X" and then reconvert when it must be transformed again
 * 
 * @author Jose Alberto Guastavino
 *
 */
public class BinaryString {
	
	private static final byte AMPERSAND_BYTE = 38;
	private static final byte SEMI_COLON = 59;
	private static final byte LINE_FEED = 10;
	private static final byte CARRIAGE_RETURN = 13;
	/**
	 * Number of characters a name of an entity been could have when trying to detect invalid entities. 
	 * It is an arbitrary number to avoid the algorithm scan for each character the whole document when trying to find the next semi-colon.
	 * 
	 */
	private static final int MAX_ENTITY_LENGTH =30;
	
	private static final String HASH_STRING = "#";

	byte[] bytes;
	String string;
	/**
	 * All characters that were changed in String
	 */
	boolean[] changed;
	
	
	private boolean isInvalidEntity(byte[] completeSequence,int index) {
		
		boolean invalidEntity=false;
		if (index<completeSequence.length-1 && completeSequence[index]== AMPERSAND_BYTE) {
			
			String nextString=new String(Arrays.copyOfRange(this.bytes, index+1, index+6));
			if (nextString.startsWith(HASH_STRING)) {
				invalidEntity=true;
			}
			boolean finished=false;
			for (int i=index+1;i<completeSequence.length && !finished && !invalidEntity;i++) {
				finished = completeSequence[i]==SEMI_COLON;
				// 30 is an arbitrary number for unclosed & entities- This avoid to check all the document for each non concluded &
				invalidEntity= completeSequence[i]==CARRIAGE_RETURN || completeSequence[i]==LINE_FEED || i>index+MAX_ENTITY_LENGTH;
			}
			if (!invalidEntity) {
				invalidEntity = !finished;
			}
		}
		
		return invalidEntity;
	}
	
	
	public BinaryString(byte[] bytes) {
		this.bytes = bytes;
		this.changed = new boolean[bytes.length];
		char[] chars = new char[bytes.length];
		for (int i=0;i<this.bytes.length;i++) {
			boolean changeChar = false;
			if (this.bytes[i]<=127) {
				changeChar = isInvalidEntity(this.bytes,i);
			} else {
				changeChar = true;
			}
			if (changeChar) {
				chars[i]='X';
				this.changed[i]=true;
			} else {
				chars[i]=(char) this.bytes[i];
			}
		}
		this.string = new String(chars);
	}
	
	public void update(int endFirstPart, String newContent, int positionSecondPart) {
		int lengthSecondPart = this.bytes.length-positionSecondPart;
		byte[] updatedBytes=new byte[endFirstPart+newContent.length()+lengthSecondPart];
		System.arraycopy(this.bytes, 0, updatedBytes, 0, endFirstPart);
		for (int i=0;i<newContent.length();i++) {
			updatedBytes[endFirstPart+i]=(byte) newContent.charAt(i);
		};
		System.arraycopy(this.bytes, positionSecondPart, updatedBytes, endFirstPart+newContent.length(), lengthSecondPart);
		String initialString = this.getString();
		String updatedString = initialString.substring(0,endFirstPart)+newContent+initialString.substring(positionSecondPart);
		this.setString(updatedString);
		this.setBytes(updatedBytes);
	}




	public void delete(int endFirstPart, int startSecondPart) {
		int secondPartLength=this.bytes.length-startSecondPart;
		byte[] updatedBytes=new byte[this.bytes.length-(startSecondPart-endFirstPart)];
		System.arraycopy(this.bytes, 0, updatedBytes, 0, endFirstPart);
		System.arraycopy(this.bytes, startSecondPart, updatedBytes, endFirstPart, secondPartLength);
		String initialString = this.getString();
		String contentAfterDeletion = initialString.substring(0,endFirstPart)+initialString.substring(startSecondPart);
		this.setString(contentAfterDeletion);
		this.setBytes(updatedBytes);
	}




	public void insert(int endFirstPart, String newContent) {
		int lengthSecondPart = this.bytes.length-endFirstPart;
		byte[] updatedBytes=new byte[this.bytes.length+newContent.length()];
		System.arraycopy(this.bytes, 0, updatedBytes, 0, endFirstPart);
		for (int i=0;i<newContent.length();i++) {
			updatedBytes[endFirstPart+i]=(byte) newContent.charAt(i);
		};
		System.arraycopy(this.bytes, endFirstPart, updatedBytes, endFirstPart+newContent.length(), lengthSecondPart);
		String initialString = this.getString();
		String contentAfterInsert = initialString.substring(0,endFirstPart)+newContent+initialString.substring(endFirstPart);
		this.setString(contentAfterInsert);
		this.setBytes(updatedBytes);
	}


	
	
	// GETTERS AND SETTERS
	
	
	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public boolean[] getChanged() {
		return changed;
	}
	public void setChanged(boolean[] changed) {
		this.changed = changed;
	}

	
	public char[] getAsChars(DocumentPart documentPart) {
		char[] charResult = null;
		if (documentPart!=null && documentPart.getText()!=null) {
			if (documentPart.getText().length()>0) {
				charResult = new char[documentPart.getText().length()];
				for (int i=0;i<documentPart.getText().length();i++) {
					charResult[i]=(char) this.bytes[documentPart.getStart()+i];
				}
			} else {
				charResult= new char[0];
			}
		}
		return charResult;
	}

	public String get(DocumentPart documentPart) {
		String result = null;
		
		if (documentPart!=null && documentPart.getText()!=null) {
			if (documentPart.isCount()) {
				result = documentPart.getText();
			} else {
				if (documentPart.getText().length()>0) {
					result = new String(this.bytes,documentPart.getStart(),documentPart.getText().length());
				} else {
					result = "";
				}
			}
		}
		return result;
	}




	
	
}
