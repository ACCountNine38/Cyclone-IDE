package commands;

import display.Console;
import objects.Variable;
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
	/*
	public static void print(String output) {
		
		Console.consoleTextPane.setText(Console.consoleTextPane.getText() + output);
		
	}
	
	public static void printLine(String output) {
		
		if(!Console.consoleTextPane.getText().equals(""))
			Console.consoleTextPane.setText(Console.consoleTextPane.getText() + "\n" + output);
		else
			Console.consoleTextPane.setText(output);
		
	}*/
	/*
	 * String text = "";
		
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
