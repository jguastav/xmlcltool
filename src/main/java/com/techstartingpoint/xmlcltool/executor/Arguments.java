package com.techstartingpoint.xmlcltool.executor;


public class Arguments {
	String fileName;
	boolean verbose;
	Operation operation;
	String selector;
	String errorMessages;
	boolean hasErrors=false;
	String newContent = null;
	
	boolean backup = false;
	
	
	public boolean validate() {
		if (fileName==null) {
			errorMessages+="No specified file name\n";
			hasErrors = true;
		}
		if (selector==null) {
			errorMessages+="No specified Selector\n";
			hasErrors = true;
		}
		if (operation==null) {
			errorMessages+="No specified Operation\n";
			hasErrors = true;
		}
		if ((operation.equals(Operation.UPDATE) || operation.equals(Operation.INSERT)) && newContent==null) {
			errorMessages+="New Content Not Specified\n";
			hasErrors = true;
		}
		return !this.hasErrors;
	} 
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isVerbose() {
		return verbose;
	}
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	public String getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public String getNewContent() {
		return newContent;
	}


	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}
	
	


	public boolean isHasErrors() {
		return hasErrors;
	}


	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
	
	


	public boolean isBackup() {
		return backup;
	}


	public void setBackup(boolean backup) {
		this.backup = backup;
	}


	/**
	 * Understand the asked task in command line based on parameters
	 * @param args
	 */
	public static Arguments generate(String[] args) {
		Arguments result = new Arguments();
		int index = 0;
		boolean error=false;
		String errorMessage = "";
		while (index < args.length) {
			switch (args[index]) {
			case "-file": 
				index++;
				if (index<args.length) {
					result.setFileName(args[index]);
				} else {
					error=true;
					errorMessage += "No File Specification after -f\n";
				}
				break;
			case "-v":
				result.setVerbose(true);
				break;
			case "-selector":
				index++;
				if (index<args.length) {
					result.setSelector(args[index]);
				} else {
					error=true;
					errorMessage += "No Selector Specification after -selector\n";
				}
				break;
			case "-select":
				result.setOperation(Operation.SELECT);
				break;
			case "-verbose":
				result.setVerbose(true);
				break;
			case "-backup":
				result.setBackup(true);
				break;
			case "-delete":
				result.setOperation(Operation.DELETE);
				break;
			case "-update":
				result.setOperation(Operation.UPDATE);
				index++;
				if (index<args.length) {
					String text=args[index];
					result.setNewContent(args[index]);
				} else {
					error=true;
					errorMessage += "No Content Specified after -update modifier \n";
				}
				break;
			case "-insert":
				result.setOperation(Operation.INSERT);
				index++;
				if (index<args.length) {
					String text=args[index];
					result.setNewContent(args[index]);
				} else {
					error=true;
					errorMessage += "No Content Specified after -insertedupdate modifier \n";
				}
				break;
			}
			index++;
		}
		result.setErrorMessages(errorMessage);
		result.setHasErrors(error);
		return result;
	}
	
	
	
}
