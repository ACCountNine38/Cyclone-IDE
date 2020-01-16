package commands;

import java.util.LinkedList;
import java.util.Queue;

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
		
		FileExecutionTool.translatedCode += "\nSystem.out.print(" + breakDownOutput(new LinkedList<String>(), output, lineNumber) + ");";
		
	}
	
	public static void printLine(String output, int lineNumber) {
		
		FileExecutionTool.translatedCode += "\nSystem.out.println(" + breakDownOutput(new LinkedList<String>(), output, lineNumber) + ");";
		
	}
	
	private static String breakDownOutput(Queue<String> printList, String output, int lineNumber) {
		
		String outputLine = output.trim();
		
		if(outputLine.contains("+")) {
			
			String token = outputLine.substring(0, outputLine.indexOf("+")).trim();
			
			boolean variableFound = false;
			for(Variable variable: FileExecutionTool.userDeclaredVariables) {
				
				if(variable.getName().equals(token)) {
					
					printList.add(token);
					variableFound = true;
					
					break;
					
				}
				
			}
			
			if(!variableFound) {
				
				if(token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"') {

					printList.add(token);
					
				} else {
					
					FileExecutionTool.terminate("RuntimeException(print statement): Line " + lineNumber);
					return "";
					
				}
				
			} 
			
			outputLine = outputLine.substring(outputLine.indexOf("+") + 1);
			
			if(!outputLine.trim().equals("")) {
				
				return breakDownOutput(printList, outputLine, lineNumber);
				
			} else {
				
				return toOutput(printList);
				
			}
			
		} else {
			
			boolean variableFound = false;
			
			for(Variable variable: FileExecutionTool.userDeclaredVariables) {
				
				if(variable.getName().equals(outputLine)) {
					
					printList.add(outputLine);
					variableFound = true;
					
					break;
					
				}
				
			}
			
			if(!variableFound) {
				
				if(outputLine.charAt(0) == '"' && outputLine.charAt(outputLine.length() - 1) == '"') {
					
					printList.add(outputLine);
					
				} else {
					
					FileExecutionTool.terminate("RuntimeException(print statement): Line " + lineNumber);
					return "";
					
				}
				
			}
			
			return toOutput(printList);
			
		}
		
	}
	
	private static String toOutput(Queue<String> printList) {
		
		String output = "";
		
		while(!printList.isEmpty()) {
			
			String token = printList.poll();
			
			if(token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"') {
				
				if(printList.size() >= 1) {
					
					output += token + " + ";
					
				} else {
					
					output += token;
					
				}
				
			} else {
				
				if(printList.size() >= 1) {
					
					output += token + " + \"\" + ";
					
				} else {
					
					output += token;
					
				}
				
			}
			
		}
		
		return output;
		
	}
	
}
