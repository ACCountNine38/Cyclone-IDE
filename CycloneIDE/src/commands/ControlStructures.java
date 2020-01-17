package commands;

import java.util.LinkedList;
import java.util.Queue;

import utils.FileExecutionTool;

/*
 * class that executes the function of a control structure
 * error checks the conditionLine conditions
 * if the conditionLine condition is invalid, program is terminated and error custom message displays
 */
public class ControlStructures {
	
	public static void initialize(String condition, String controlStatement, int lineNumber) {
		
		if(controlStatement.equals("if")) {
			
			FileExecutionTool.translatedCode += "\nif(" + breakDownCondition(new LinkedList<String>(), condition, lineNumber) + ") {";
			
		} else if(controlStatement.equals("else_if")) {
			
			FileExecutionTool.translatedCode += "\nelse if(" + breakDownCondition(new LinkedList<String>(), condition, lineNumber) + ") {";
			
		} else if(controlStatement.equals("else")) {
			
			FileExecutionTool.translatedCode += "\nelse {";
			
		}
	}
	
	private static String breakDownCondition(Queue<String> conditionList,  String input, int lineNumber) {
		
		if(input.contains(" " + FileExecutionTool.userCommands.get("and") + " ") || 
				input.contains(" " + FileExecutionTool.userCommands.get("or") + " ")) {
			
			int andPosition = input.indexOf(" " + FileExecutionTool.userCommands.get("and") + " ");
			int orPosition = input.indexOf(" " + FileExecutionTool.userCommands.get("or") + " ");
			int finalPosition = 0;
			int finalOperatorSize = 0;
			
			String logicalOperator = "";
			
			if(orPosition == -1) {
				
				finalPosition = andPosition;
				finalOperatorSize = FileExecutionTool.userCommands.get("and").length();
				logicalOperator = "&&";
				
			} else if(andPosition == -1) {
				
				finalPosition = orPosition;
				finalOperatorSize = FileExecutionTool.userCommands.get("or").length();
				logicalOperator = "||";
				
			} else if(andPosition < orPosition) {
				
				finalPosition = andPosition;
				finalOperatorSize = FileExecutionTool.userCommands.get("and").length();
				logicalOperator = "&&";
				
			} else {
				
				finalPosition = orPosition;
				finalOperatorSize = FileExecutionTool.userCommands.get("or").length();
				logicalOperator = "||";
				
			}
			//System.out.println(andPosition + " " + orPosition);
			String token = input.substring(0, finalPosition).trim();
			
			if(token.contains(" > ") || token.contains(" >= ") || token.contains(" < ") || token.contains(" <= ") || token.contains(" == ")) {
				
				String leftOperator = token.substring(0, token.indexOf(" ")).trim();
				
				for(Variable variable: FileExecutionTool.userDeclaredVariables) {
					
					if(variable.getName().equals(leftOperator)) {
						
						leftOperator = variable.getValue();
						
						break;
						
					}
					
				}
				
				String rightOperator = token.substring(token.indexOf(" ") + 2 + 1, token.length()).trim();
				
				for(Variable variable: FileExecutionTool.userDeclaredVariables) {
					
					if(variable.getName().equals(rightOperator)) {
						
						rightOperator = variable.getValue();
						
						break;
						
					}
					
				}
				
				if(Variable.getDatatype(leftOperator, lineNumber).equals(Variable.getDatatype(rightOperator, lineNumber))) {
					
					conditionList.add(token);
					conditionList.add(" " + logicalOperator + " ");
					
					input = input.substring(finalPosition + finalOperatorSize + 1).trim();
					
					if(!input.equals("")) {
						
						return breakDownCondition(conditionList, input, lineNumber);
						
					} else {
						
						return toCondition(conditionList);
						
					}
					
				} else {
					
					FileExecutionTool.terminate("Unrecongnized Control Structure Statement: Line " + lineNumber, lineNumber);
					
					return "";
					
				}
				
			} else {
				
				FileExecutionTool.terminate("Unrecongnized Control Structure Statement: Line " + lineNumber, lineNumber);
				
				return "";
				
			}
				
		} else {
			
			String token = input.trim();
			
			if(token.contains(" > ") || token.contains(" >= ") || token.contains(" < ") || token.contains(" <= ") || token.contains(" == ")) {
				
				String leftOperator = token.substring(0, token.indexOf(" ")).trim();
				
				for(Variable variable: FileExecutionTool.userDeclaredVariables) {
					
					if(variable.getName().equals(leftOperator)) {
						
						leftOperator = variable.getValue();
						
						break;
						
					}
					
				}
				
				String rightOperator = token.substring(token.indexOf(" ") + 2 + 1, token.length()).trim();
				
				for(Variable variable: FileExecutionTool.userDeclaredVariables) {
					
					if(variable.getName().equals(rightOperator)) {
						
						rightOperator = variable.getValue();
						
						break;
						
					}
					
				}
				System.out.println(leftOperator + " " + lineNumber);
				if(Variable.getDatatype(leftOperator, lineNumber).equals(Variable.getDatatype(rightOperator, lineNumber))) {
					
					conditionList.add(token);
					
					return toCondition(conditionList);
					
				} else {
					
					FileExecutionTool.terminate("Unrecongnized Control Structure Statement: Line " + lineNumber, lineNumber);
					
					return "";
					
				}
				
			} else {
				
				FileExecutionTool.terminate("Unrecongnized Control Structure Statement: Line " + lineNumber, lineNumber);
				
				return "";
				
			}
			
		}
		
	}
	
	private static String toCondition(Queue<String> conditionList) {
		//System.out.println(conditionList);
		String condition = "";
		
		while(!conditionList.isEmpty()) {
			
			condition += conditionList.poll();
			
		}
		
		return condition;
		
	}

}
