package commands;

import utils.FileExecutionTool;

public class ControlStructures {
	
	public static void initialize(String condition, String controlStatement) {
		
		toJava(condition.trim(), controlStatement);
		
	}
	
	private static void toJava(String condition, String controlStatement) {
		
		if(controlStatement.equals("if")) {
			
			FileExecutionTool.translatedCode += "\nif(" + condition + ") {";
			
		} else if(controlStatement.equals("else_if")) {
			
			FileExecutionTool.translatedCode += "\nelse if(" + condition + ") {";
			
		} else if(controlStatement.equals("else")) {
			
			FileExecutionTool.translatedCode += "\nelse {";
			
		}
		
		
	}

}
