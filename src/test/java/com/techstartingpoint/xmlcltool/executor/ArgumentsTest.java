package com.techstartingpoint.xmlcltool.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ArgumentsTest {

	@Test
	public void testArgumentsSelect() {
		// Prepare the environment
		String fileName = "1.xml";
		String selectorString = "\"//testSQL/Sample[3]/queryString\"";
		String[] args = {"-file",fileName,"-selector",selectorString,"-select","-verbose"}; 
		// Execute the test
		Arguments arguments = Arguments.generate(args);
		
		// Check the result
		assertEquals(fileName,arguments.getFileName());
		assertEquals(selectorString,arguments.getSelector());
		assertEquals(Operation.SELECT,arguments.getOperation());
		assertTrue(arguments.isVerbose());
		assertTrue(arguments.validate());
	}
	

	
	// -file c:\projects\customer\robin\deploy\1.xml -selector "//testSQL/Sample[5]/java.net.SQL" -update "A change made in xml" -backup -verbose
	@Test
	public void testArgumentsUpdate() {
		// Prepare the environment
		String fileName = "1.xml";
		String selectorString = "//testSQL/Sample[6]/java.net.SQL";
		String[] args = {"-file",fileName,"-selector",selectorString,"-update","\"A change made in xml\"","-backup","-verbose"}; 
		// Execute the test
		Arguments arguments = Arguments.generate(args);
		
		// Check the result
		assertEquals(fileName,arguments.getFileName());
		assertEquals(selectorString,arguments.getSelector());
		assertEquals(Operation.UPDATE,arguments.getOperation());
		assertTrue(arguments.isVerbose());
		assertTrue(arguments.validate());
	}


}
