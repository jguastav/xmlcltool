package com.techstartingpoint.xmlcltool.util;

public class StringUtils {

	public static String removeQuotes(String mainString) {
		String result = mainString;
		if (result!= null && 
				result.length()>=2 && 
				result.substring(0,1).equals(result.substring(result.length()-1)) && 
				(result.substring(result.length()-1).equals("\"") ||
						result.substring(result.length()-1).equals("\'")	)) {
			result = result.substring(1,result.length()-1);
		}
		return result;
		
	}
	
	
	
}
