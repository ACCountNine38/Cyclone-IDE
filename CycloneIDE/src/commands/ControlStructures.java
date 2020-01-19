package commands;

import java.util.LinkedList;
import java.util.Queue;

import utils.FileExecutionTool;

/*
 * class that executes the function of a control structure
 * error checks the conditionLine conditions
 * if the condition is invalid, program is terminated and error custom message displays
 */
public class ControlStructures {
	
	// method that initializes the appropriate control structure given the condition and line number
	public static void initialize(String condition, String controlStatement, int lineNumber) {
		
		// translate code based on the control structure it is
		if(controlStatement.equals("if")) {
			
			FileExecutionTool.translatedCode += "\nif(" + breakDownCondition(new LinkedList<String>(), condition, lineNumber) + ") {";
			
		} else if(controlStatement.equals("else_if")) {
			
			FileExecutionTool.translatedCode += "\nelse if(" + breakDownCondition(new LinkedList<String>(), condition, lineNumber) + ") {";
			
		} else if(controlStatement.equals("else")) {
			
			FileExecutionTool.translatedCode += "\nelse {";
			
		}
	}
	
	// recursive method that breaks down a given condition line and checks for errors in the condition to be translated
	private static String breakDownCondition(Queue<String> conditionList,  String conditionLine, int lineNumber) {
		
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
			
			if(token.contains(" > ") || token.contains(" >= ") || token.contains(" < ") || token.contains(" <= ") 
					|| token.contains(" == ") || token.contains(" != ")) {
				
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
				
				// the left and right can be compared if its integer or double
				if((Variable.getDatatype(leftOperator, lineNumber).equals("int") || Variable.getDatatype(leftOperator, lineNumber).equals("double")) &&
						(Variable.getDatatype(rightOperator, lineNumber).equals("int") || Variable.getDatatype(rightOperator, lineNumber).equals("double"))) {
					
					// add the token to the valid condition list
					conditionList.add(token);
					
					// add the logical operator to the condition list
					conditionList.add(" " + logicalOperator + " ");
					
					// proceed with the next part of the condition by cutting the token from the conditionLine condition
					conditionLine = conditionLine.substring(operatorPosition + operatorSize + 1).trim();
					

					// if the new condition is empty, then it means that there are no more tokens to check
					// or else recurse to check the next part of the condition
					if(!conditionLine.equals("")) {
						
						return breakDownCondition(conditionList, conditionLine, lineNumber);
						
					} 
					
					// convert the condition tokens to java
					else {
						
						return toConditionString(conditionList);
						
					}
					
				}
				
				// if the data-type of the left and right side of the operator is the same, then this section of condition is valid 
				else if(Variable.getDatatype(leftOperator, lineNumber).equals(Variable.getDatatype(rightOperator, lineNumber))) {
					
					// checks if the left and right operators are strings
					if(Variable.getDatatype(leftOperator, lineNumber).equals("String") && Variable.getDatatype(rightOperator, lineNumber).equals("String") &&
							(token.contains(" == ") || token.contains(" != "))) {
						
						// translate code into .equals()
						if(token.contains("==")) {
							
							conditionList.add(token.substring(0, token.indexOf(" ")).trim() + 
									".equals(" + token.substring(token.indexOf(" ") + 2 + 1, token.length()).trim() + ")");
							
						} else if(token.contains("!=")) {
							
							conditionList.add("!" +token.substring(0, token.indexOf(" ")).trim() + 
									".equals(" + token.substring(token.indexOf(" ") + 2 + 1, token.length()).trim() + ")");
							
						} else {
							
							// other types of comparison operators are not allowed
							FileExecutionTool.terminate("Uncomparable Values: Line ", lineNumber);
							return "";
							
						}
						
						// add the logical operator to the condition list
						conditionList.add(" " + logicalOperator + " ");

						conditionLine = conditionLine.substring(operatorPosition + operatorSize + 1).trim();
						
						if(!conditionLine.equals("")) {
							
							return breakDownCondition(conditionList, conditionLine, lineNumber);
							
						} else {
							
							return toConditionString(conditionList);
							
						}
						
					} 
					
					// if the left and right side values are boolean, then they can only be compared with equal or not equal sign
					else if(Variable.getDatatype(leftOperator, lineNumber).equals("boolean") && Variable.getDatatype(rightOperator, lineNumber).equals("boolean") &&
							(token.contains(" == ") || token.contains(" != "))) {
						
						// add the token to the valid condition list
						conditionList.add(token);
						
						conditionList.add(" " + logicalOperator + " ");
						
						conditionLine = conditionLine.substring(operatorPosition + operatorSize + 1).trim();
						
						if(!conditionLine.equals("")) {
							
							return breakDownCondition(conditionList, conditionLine, lineNumber);
							
						} else {
							
							return toConditionString(conditionList);
							
						}
						
					} 
					
					// other cases of values cannot be compared
					else {
						
						FileExecutionTool.terminate("Uncomparable Values: Line ", lineNumber);
						return "";
						
					}
					
				} else {
					
					// terminate the program if the data-type of both sides of the operator does not match
					FileExecutionTool.terminate("Uncomparable Values: Line ", lineNumber);
					
					return "";
					
				}
				

			} else {
				
				FileExecutionTool.terminate("Unrecongnized Control Structure Statement\n"
						+ "Make Sure Spaces are Added Between Comparison Signs: Line ", lineNumber);
				
				return "";
				
			}
				
		} 
		
		// event for when there are no "and" and "or" in the input condition line
		else {
			
			// remove all leading and trailing spaces to validate the token
			String token = conditionLine.trim();
			
			// checks if the token contains any operators, if not, then output error message
			if(token.contains(" > ") || token.contains(" >= ") || token.contains(" < ") || token.contains(" <= ") 
					|| token.contains(" == ") || token.contains(" != ")) {
				
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
				
				// the left and right can be compared if its integer or double
				if((Variable.getDatatype(leftOperator, lineNumber).equals("int") || Variable.getDatatype(leftOperator, lineNumber).equals("double")) &&
						(Variable.getDatatype(rightOperator, lineNumber).equals("int") || Variable.getDatatype(rightOperator, lineNumber).equals("double"))) {
					
					// add the token to the valid condition list
					conditionList.add(token);
					
					return toConditionString(conditionList);
					
				}
				
				// if the left operator and the right operator have the same data-type, then translate the condition to java
				else if(Variable.getDatatype(leftOperator, lineNumber).equals(Variable.getDatatype(rightOperator, lineNumber))) {

					// checks if the left and right operators are strings, if it is, then translate into .equals()
					if(Variable.getDatatype(leftOperator, lineNumber).equals("String") && Variable.getDatatype(rightOperator, lineNumber).equals("String") &&
							(token.contains(" == ") || token.contains(" != "))) {
		
						// translate code into .equals()
						if(token.contains("==")) {
							
							conditionList.add(token.substring(0, token.indexOf(" ")).trim() + 
									".equals(" + token.substring(token.indexOf(" ") + 2 + 1, token.length()).trim() + ")");
							
						} else if(token.contains("!=")) {
							
							conditionList.add("!" +token.substring(0, token.indexOf(" ")).trim() + 
									".equals(" + token.substring(token.indexOf(" ") + 2 + 1, token.length()).trim() + ")");
							
						} else {
							// other types of comparison operators are not allowed
							FileExecutionTool.terminate("Uncomparable Values: Line ", lineNumber);
							return "";
							
						}
						
						return toConditionString(conditionList);
						
					} 
				
					// if the left and right side values are boolean, then they can only be compared with equal or not equal sign
					else if(Variable.getDatatype(leftOperator, lineNumber).equals("boolean") && Variable.getDatatype(rightOperator, lineNumber).equals("boolean") &&
							(token.contains(" == ") || token.contains(" != "))) {
						
						// add the token to the valid condition list
						conditionList.add(token);
						
						return toConditionString(conditionList);
						
					} 
					
					// other cases of values cannot be compared
					else {
						
						FileExecutionTool.terminate("Uncomparable Values: Line ", lineNumber);
						return "";
						
					}
					
				} 
				
				// terminate the program if the left and right operators does not match
				else {
					
					FileExecutionTool.terminate("Uncomparable Values: Line ", lineNumber);
					
					return "";
					
				}
				
			} else {
				
				FileExecutionTool.terminate("Unrecongnized Control Structure Statement\n"
						+ "Make Sure Spaces are Added Between Comparison Signs: Line ", lineNumber);
				
				return "";
				
			}
			
		}
		
	}
	
	// method that converts a given list of conditions into a String condition of a java loop
	private static String toConditionString(Queue<String> conditionList) {
		
		// variable that stores the overall condition
		String condition = "";
		
		// combine the values from the Queue in the order they are typed by the user and the order that they are split
		while(!conditionList.isEmpty()) {
			
			condition += conditionList.poll();
			
		}
		
		return condition;
		
	}

}
