package commands;

import utils.FileExecutionTool;

public class While {
	
	public static void initialize(String condition) {
		
		toJava(condition.trim());
		
	}
	
	private static void toJava(String condition) {
		
		if(condition.equals(FileExecutionTool.userCommands.get("true")))
			
			FileExecutionTool.translatedCode += "\nwhile(true) {";
		
		else if(condition.equals(FileExecutionTool.userCommands.get("false")))

			FileExecutionTool.translatedCode += "\nwhile(false) {";
		
		else
			
			FileExecutionTool.translatedCode += "\nwhile(" + condition + ") {";
		
	}

}
