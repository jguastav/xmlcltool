package com.techstartingpoint.xmlcltool.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.techstartingpoint.xmlcltool.xmlparser.BinaryString;

public class FileUtils {

	public static void updateInFile(String fileName,BinaryString content,boolean isBackup,String backupFileName) throws IOException {
		if (isBackup) {
			copyFile(fileName, backupFileName);
		};
		writeToFile(fileName, content);
	}
	
	public static void copyFile(String inputFileName, String outputFileName) throws IOException {
		byte[] allBytes= Files.readAllBytes(Paths.get(inputFileName));
		Files.write(Paths.get(outputFileName), allBytes);
	}

	
	private static BinaryString readFromInputStream(InputStream inputStream) throws IOException {
		final int BUFFER_SIZE = 65536;
        
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[BUFFER_SIZE];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
     
        buffer.flush();
        byte[] byteArray = buffer.toByteArray();
        BinaryString result = new BinaryString(byteArray);
		return result; 
	}

	public static BinaryString getInputStreamContent(InputStream inputStream) throws IOException {
		BinaryString documentString = readFromInputStream(inputStream);
		return documentString;
	}

	public static void writeToFile(String fileName, BinaryString content) throws IOException {
		Path path = Paths.get(fileName);
		byte[] strToBytes = content.getBytes();
		Files.write(path, strToBytes);
	}

	public static BinaryString readFromFile(String fileName) throws IOException {
		return new BinaryString(Files.readAllBytes(Paths.get(fileName)));
	}
	
	
	public static String getBackupFileName(String name,LocalDateTime localDateTime) {
		String timePart= localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String resultName = name+".bak."+timePart+".xml";
		return resultName;
	}
	
	
	
	
}
