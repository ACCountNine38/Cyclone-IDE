package commands;

import utils.FileExecutionTool;

public class Print {
	
	public static String validateText(String line) {
		
		for(Variable variable: FileExecutionTool.userDeclaredVariables) {
			if(variable.getName().equals(line.trim())) {
				return variable.getValue();
			}
		}
		
		return line;
		
	}
	
	public static void print(String output, int lineNumber) {
		
		FileExecutionTool.translatedCode += "\n try {";
				
		FileExecutionTool.translatedCode += "\nSystem.out.print(" + output + ");";
		
		FileExecutionTool.translatedCode += "\n } catch(java.lang.RuntimeException error) { "
				+ "\nSystem.out.println(\"RuntimeException(print): \" + " + lineNumber + ");\n}";
		
	}
	
	public static void printLine(String output, int lineNumber) {
		
		FileExecutionTool.translatedCode += "\n try {";
		
		FileExecutionTool.translatedCode += "\nSystem.out.println(" + output + ");";
		
		FileExecutionTool.translatedCode += "\n } catch(java.lang.RuntimeException error) { "
				+ "\nSystem.out.println(\"RuntimeException(print line): \" + " + lineNumber + ");\n}";
		
	}
	/*
	 String text = "";
		
		int numQuotation = 0;
		
		int quotation1Index = 0;
		int quotation2Index = 0;
		
		for(int i = 0; i < line.length(); i++) {
			
			if(line.charAt(i) == '"') {
				
				numQuotation++;
				
				if(numQuotation == 1)
					quotation1Index = i;
				else if(numQuotation == 2)
					quotation2Index = i;
				
			}
			
			if(numQuotation == 2) {
				
				text += line.substring(quotation1Index+1, quotation2Index);
				numQuotation = 0;
				
			}
			
		}
		
		return text;
	 */

}
