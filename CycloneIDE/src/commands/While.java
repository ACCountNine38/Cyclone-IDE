package commands;

import java.util.LinkedList;
import java.util.Queue;

import utils.FileExecutionTool;

/*
 * class that executes the function of java while loop
 * error checks the conditionLine conditions
 * if the conditionLine condition is invalid, program is terminated and error custom message displays
 */
public class While {
	
	// method that initializes the loop given the condition and line number
	public static void initialize(String condition, int lineNumber) {
		
		// translate the code to Java after error checking
		FileExecutionTool.translatedCode += "\nwhile(" + breakDownCondition(new LinkedList<String>(), condition, lineNumber) + ") {";
		
	}
	
	// recursive method that breaks down a given condition line and checks for errors in the condition to be translated
	private static String breakDownCondition(Queue<String> conditionList, String conditionLine, int lineNumber) {
		
		// checks if the given condition contains the "and" and "or" function to break down the input
		if(conditionLine.contains(" " + FileExecutionTool.userCommands.get("and") + " ") || 
				conditionLine.contains(" " + FileExecutionTool.userCommands.get("or") + " ")) {
			
			// position of the and/or keywords
			int andPosition = conditionLine.indexOf(" " + FileExecutionTool.userCommands.get("and") + " ");
			int orPosition = conditionLine.indexOf(" " + FileExecutionTool.userCommands.get("or") + " ");
			int operatorPosition = 0;
			int operatorSize = 0;
			String logicalOperator = "";
			
			// sets the first operator position based on if and comes first or or comes first in a given condition
			if(orPosition == -1) {
				
				operatorPosition = andPosition;
				operatorSize = FileExecutionTool.userCommands.get("and").length();
				logicalOperator = "&&";
				
			} else if(andPosition == -1) {
				
				operatorPosition = orPosition;
				operatorSize = FileExecutionTool.userCommands.get("or").length();
				logicalOperator = "||";
				
			} else if(andPosition < orPosition) {
				
				operatorPosition = andPosition;
				operatorSize = FileExecutionTool.userCommands.get("and").length();
				logicalOperator = "&&";
				
			} else {
				
				operatorPosition = orPosition;
				operatorSize = FileExecutionTool.userCommands.get("or").length();
				logicalOperator = "||";
				
			}
			
			// splits the input condition into 2 sub categories to check if the code is valid
			String token = conditionLine.substring(0, operatorPosition).trim();
			
			// checks if the token contains any operators, if not, then output error message
			if(token.contains(" > ") || token.contains(" >= ") || token.contains(" < ") || token.contains(" <= ") || token.contains(" == ")) {
				
				// split the token into components on the left and right of the operator
				String leftOperator = token.substring(0, token.indexOf(" ")).trim();
				
				// check to see if the left operator is an user declared variable
				for(Variable variable: FileExecutionTool.userDeclaredVariables) {
					
					// if the left side of the operator is a variable, then set the value to the variable's, 
					if(variable.getName().equals(leftOperator)) {
						
						leftOperator = variable.getValue();
						
						break;
						
					}
					
				}
				
				// right component of the token
				// 2 is added to ignore the operator when checking if its a variable
				String rightOperator = token.substring(token.indexOf(" ") + 2 + 1, token.length()).trim();
				
				for(Variable variable: FileExecutionTool.userDeclaredVariables) {
					
					if(variable.getName().equals(rightOperator)) {
						
						rightOperator = variable.getValue();
						
						break;
						
					}
					
				}
				
				// if the data-type of the left and right side of the operator is the same, then this section of condition is valid 
				if(Variable.getDatatype(leftOperator, lineNumber).equals(Variable.getDatatype(rightOperator, lineNumber))) {
					
					// add the token to the valid condition list
					conditionList.add(token);
					// add the logical operator to the condition list
					conditionList.add(" " + logicalOperator + " ");
					
					// proceed with the next part of the condition by cutting the token from the input condition
					conditionLine = conditionLine.substring(operatorPosition + operatorSize + 1).trim();
					
					// if the new condition is empty, then it means that there are no more tokens to check
					// or else recurse to check the next part of the condition
					if(!conditionLine.equals("")) {
						
						return breakDownCondition(conditionList, conditionLine, lineNumber);
						
					} else {
						
						// convert the condition tokens to java
						return toConditionString(conditionList);
						
					}
					
				} else {
					
					// terminate the program if the data-type of both sides of the operator does not match
					FileExecutionTool.terminate("Unrecongnized Control Structure Statement: Line " + lineNumber, lineNumber);
					
					return "";
					
				}
				
			} else {
				
				// if there are no operators, then program terminates
				FileExecutionTool.terminate("Unrecongnized Control Structure Statement: Line " + lineNumber, lineNumber);
				
				return "";
				
			}
				
		} 
		
		// event for when there are no "and" and "or" in the input condition line
		else {
			
			// remove all leading and trailing spaces to validate the token
			String token = conditionLine.trim();
			
			// checks if the token contains any operators, if not, then output error message
			if(token.contains(" > ") || token.contains(" >= ") || token.contains(" < ") || token.contains(" <= ") || token.contains(" == ")) {
				
				// break down the left and right side of the operators to validate the condition
				String leftOperator = token.substring(0, token.indexOf(" ")).trim();
				
				// check if the left side is a variable, if it is, get its value
				for(Variable variable: FileExecutionTool.userDeclaredVariables) {
					
					if(variable.getName().equals(leftOperator)) {
						
						leftOperator = variable.getValue();
						
						break;
						
					}
					
				}
				
				String rightOperator = token.substring(token.indexOf(" ") + 2 + 1, token.length()).trim();
				
				// check if the right side is a variable, if it is, get its value
				for(Variable variable: FileExecutionTool.userDeclaredVariables) {
					
					if(variable.getName().equals(rightOperator)) {
						
						rightOperator = variable.getValue();
						
						break;
						
					}
					
				}
				
				// if the left operator and the right operator have the same data-type, then translate the condition to java
				if(Variable.getDatatype(leftOperator, lineNumber).equals(Variable.getDatatype(rightOperator, lineNumber))) {
					
					conditionList.add(token);
					
					return toConditionString(conditionList);
					
				} else {
					
					// terminate the program if the left and right operators does not match
					FileExecutionTool.terminate("Unrecongnized Control Structure Statement: Line " + lineNumber, lineNumber);
					
					return "";
					
				}
				
			} else {
				
				// terminate the program if operators(==, >=, <, etc.) are not found within the input condition
				FileExecutionTool.terminate("Unrecongnized Control Structure Statement: Line " + lineNumber, lineNumber);
				
				return "";
				
			}
			
		}
		
	}
	
	// method that converts a given list of conditions into a String condition of a java loop
	private static String toConditionString(Queue<String> conditionList) {
		
		// variable that stores the condition
		String condition = "";
		
		// combine the values from the Queue in the order they are typed by the user and the order that they are splitted
		while(!conditionList.isEmpty()) {
			
			condition += conditionList.poll();
			
		}
		
		return condition;
		
	}

}
