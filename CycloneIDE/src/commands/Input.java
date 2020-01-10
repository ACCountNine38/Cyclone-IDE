package commands;

import javax.swing.JOptionPane;

import utils.FileExecutionTool;
import utils.Variable;

public class Input {
	
	public static String validateText(String line) {
		
		for(Variable variable: FileExecutionTool.userDeclaredVariables) {
			if(variable.getName().equals(line.trim())) {
				return variable.getName();
			}
		}
		
		return line;
		
	}
	
	public static void readVariable(String variable) {
		
		for(Variable currentVariable: FileExecutionTool.userDeclaredVariables) {
			
			if(currentVariable.getName().equals(variable)) {
				
				String inputValue = JOptionPane.showInputDialog("Enter input for variable " + variable);
				currentVariable.setValue(inputValue);
				
			}
			
		}
		
	}

}
