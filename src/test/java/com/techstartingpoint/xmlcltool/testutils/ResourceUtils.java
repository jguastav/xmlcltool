package com.techstartingpoint.xmlcltool.testutils;

import java.io.IOException;
import java.io.InputStream;

import com.techstartingpoint.xmlcltool.util.BinaryString;
import com.techstartingpoint.xmlcltool.util.FileUtils;

public class ResourceUtils {
	public static BinaryString getStringFromResourceFile(String resourceFileName) {
	    Class clazz = ResourceUtils.class;
	    InputStream inputStream = clazz.getResourceAsStream(resourceFileName);
	    BinaryString result = null;
	    try {
			result = FileUtils.getInputStreamContent(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return result;
	}
}
