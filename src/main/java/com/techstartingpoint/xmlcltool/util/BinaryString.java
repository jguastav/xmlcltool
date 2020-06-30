package com.techstartingpoint.xmlcltool.util;

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

	byte[] bytes;
	String string;
	/**
	 * All characters that were changed in String
	 */
	boolean[] changed;
	
	
	public BinaryString(byte[] bytes) {
		this.bytes = bytes;
		this.changed = new boolean[bytes.length];
		char[] chars = new char[bytes.length];
		for (int i=0;i<this.bytes.length;i++) {
			if (this.bytes[i]<=127) {
				chars[i]=(char) this.bytes[i];
			} else {
				chars[i]='X';
				this.changed[i]=true;
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
