package commands;

import javax.swing.JOptionPane;

import utils.FileExecutionTool;

/*
 * class that allows the user to read an input
 * checks for input errors
 */
public class Input {
	
	// method that reads a variable and checks if it is a valid value, further error checks are in the Variable class
	public static void readVariable(String variable, int lineNumber) {
		
		// checks if the variable being read exist 
		boolean found = false;
		
		for(Variable currentVariable: FileExecutionTool.userDeclaredVariables) {
			
			// if the variable exist. then open user input and set the value of the variable
			if(currentVariable.getName().equals(variable)) {
				
				String inputValue = JOptionPane.showInputDialog("Enter input for variable " + variable);
				currentVariable.setValue(inputValue, lineNumber);
				found = true;
				
			}
			
		}
		
		// if the variable is not found, then terminate the program 
		if(!found) {
			
			FileExecutionTool.terminate("No Variables Found for Input. Variable Must First be Declared: Line ", lineNumber);
			
		}
		
	}

}
