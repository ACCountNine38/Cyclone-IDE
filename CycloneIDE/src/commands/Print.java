package commands;

import java.util.LinkedList;
import java.util.Queue;

import utils.FileExecutionTool;

/*
 * class that executes the function of a print or print line statement
 * error checks the conditionLine conditions
 * if the print value is invalid, program is terminated and error custom message displays
 */
public class Print {
	
	// method that translates a print to Java for execution
	public static void print(String output, int lineNumber) {
		
		FileExecutionTool.translatedCode += "\nSystem.out.print(" + breakDownOutput(new LinkedList<String>(), output, lineNumber) + ");";
		
	}
	
	// method that translates print line to Java for execution
	public static void printLine(String output, int lineNumber) {
		
		FileExecutionTool.translatedCode += "\nSystem.out.println(" + breakDownOutput(new LinkedList<String>(), output, lineNumber) + ");";
		
	}
	
	// recursive method that breaks down a given condition line and checks for errors in the condition to be translated
	private static String breakDownOutput(Queue<String> printList, String output, int lineNumber) {
		
		// trims the output line to remove leading and trailing spaces
		String outputLine = output.trim();
		
		// checks if output line contains a plus, if it does, then break it down into tokens
		if(outputLine.contains("+")) {
			
			String token = outputLine.substring(0, outputLine.indexOf("+")).trim();
			
			// check if the current token is a variable
			boolean variableFound = false;
			for(Variable variable: FileExecutionTool.userDeclaredVariables) {
				
				if(variable.getName().equals(token)) {
					
					// if it is, add the variable name to the print list
					printList.add(token);
					variableFound = true;
					
					break;
					
				}
				
			}
			
			// if the token is not a variable, then check if its a valid value
			if(!variableFound) {
				
				if(Variable.getDatatype(outputLine, lineNumber).equals("int") || Variable.getDatatype(outputLine, lineNumber).equals("double") 
						|| Variable.getDatatype(outputLine, lineNumber).equals("boolean") || Variable.getDatatype(outputLine, lineNumber).equals("String")) {
					
					printList.add(outputLine);
					
				} else {
					
					// terminate the program if the value is invalid and display error message
					FileExecutionTool.terminate("RuntimeException(print statement): Line " , lineNumber);
					return "";
					
				}
				
			} 
			
			// try to break the line into pieces to check the other half of the plus sign to see if its valid
			outputLine = outputLine.substring(outputLine.indexOf("+") + 1);
			
			// if the line is empty, then proceed to convert all the conditions to Java. If not, then recurse the method
			if(!outputLine.trim().equals("")) {
				
				return breakDownOutput(printList, outputLine, lineNumber);
				
			} else {
				
				return toOutput(printList);
				
			}
			
		} else {
			
			// if the print statement does not have a plus sign, then check the entire String
			boolean variableFound = false;
			
			// loop through all the user declared variables to see if the line is a variable
			for(Variable variable: FileExecutionTool.userDeclaredVariables) {
				
				if(variable.getName().equals(outputLine)) {
					
					// add the variable to the print list
					printList.add(outputLine);
					variableFound = true;
					
					break;
					
				}
				
			}
			
			// if variable is not found, then check if the line is valid for output
			if(!variableFound) {
				
				if(Variable.getDatatype(outputLine, lineNumber).equals("int") || Variable.getDatatype(outputLine, lineNumber).equals("double") 
						|| Variable.getDatatype(outputLine, lineNumber).equals("boolean") || Variable.getDatatype(outputLine, lineNumber).equals("String")) {
					
					printList.add(outputLine);
					
				} else {
					
					FileExecutionTool.terminate("RuntimeException(print statement): Line " , lineNumber);
					return "";
					
				}
				
			}
			
			// translate the print list to Java to execute
			return toOutput(printList);
			
		}
		
	}
	
	// method that converts a given list of conditions into a String condition of a print statement
	private static String toOutput(Queue<String> printList) {
		
		// initialize final output
		String output = "";
		
		// continue to add items to the output while the print Queue is not empty
		while(!printList.isEmpty()) {
			
			// take each token of the queue and merge them
			String token = printList.poll();
			
			// if the variable is not a String, then "" have to be added to output
			if(token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"') {
				
				// add a plus sign if the current element is not the last one in the Queue
				if(printList.size() >= 1) {
					
					output += token + " + ";
					
				} else {
					
					// add token to the output
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
