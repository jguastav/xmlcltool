package com.techstartingpoint.xmlcltool.commandparser;

public enum SelectorType {
	ATTRIBUTE,TAG;

	public static SelectorType getSelectorType(String xPathQuery) {
		SelectorType returnValue = SelectorType.TAG;
		int posSlash = xPathQuery.lastIndexOf("/");
		if ("@".equals(xPathQuery.substring(posSlash+1,posSlash+2))) {
			returnValue = SelectorType.ATTRIBUTE;
		}
		return returnValue;
	}
}
