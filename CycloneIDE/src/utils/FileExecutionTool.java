package utils;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import commands.Input;
import commands.Print;
import display.Console;
import display.Editor;
import objects.Variable;

public class FileExecutionTool {
	
	public static HashMap<String, String> userCommands = new HashMap<String, String>();
	public static HashMap<String, String> userDatatypes = new HashMap<String, String>();
	
	public static ArrayList<Variable> userDeclaredVariables = new ArrayList<Variable>();
	
	public static boolean executeSuccessful;
	
	public FileExecutionTool() {
		
		updateCommands();
		updateDatatypes();
		
	}
	
	public static void updateCommands() {
		
		userCommands.clear();
		
		try {
			
			Scanner command = new Scanner(new File("commands/userCommands"));
			
			while(command.hasNext()) {
				
				userCommands.put(command.next(), command.next());
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void updateDatatypes() {
		
		userDatatypes.clear();
		
		try {
			
			Scanner command = new Scanner(new File("commands/datatypes"));
			
			while(command.hasNext()) {
				
				userCommands.put(command.next(), command.next());
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void executeFile(File file) {
		
		Console.consoleTextPane.setText("");
		
		userDeclaredVariables.clear();
		
		try {
			
			Scanner input = new Scanner(file);
			
			executeSuccessful = true;
			
			while(input.hasNext()) {
				
				if(!executeSuccessful) {
					return;
				}
				
				String line = input.nextLine();
				
				for(int i = 0; i < line.length(); i++) {
					
					if(line.charAt(i) == ':') {
						
						String key = line.substring(0, i).trim();
						
						boolean action = false;
						
						for(HashMap.Entry<String, String> command : userCommands.entrySet()) {
							
							if(key.equals(command.getValue()) && command.getKey().equals("print")) {
								
								line = Print.validateText(line.substring(line.indexOf(key) + key.length() + 1));
								Print.print(line);
								
								action = true;
								break;
								
							} else if(key.equals(command.getValue()) && command.getKey().equals("printl")) {
								
								line = Print.validateText(line.substring(line.indexOf(key) + key.length() + 1));
								Print.printLine(line);
								
								action = true;
								break;
								
							} else if(key.equals(command.getValue()) && command.getKey().equals("input")) {
								
								String inputVariable = Input.validateText(line.substring(line.indexOf(key) + key.length() + 1));
								Input.readVariable(inputVariable);
								
								action = true;
								break;
								
							} else if(key.equals(command.getValue()) && command.getKey().equals("for")) {
								
								String inputVariable = Input.validateText(line.substring(line.indexOf(key) + key.length() + 1));
								
								
								action = true;
								break;
								
							}
							
						}
						
						if(action) {
							break;
						}
						
					} else if(line.charAt(i) == '=') {
						
						String variable = line.substring(0, i).trim();
						String value = line.substring(i+1, line.length()).trim();
						
						boolean found = false;
						
						for(Variable var: userDeclaredVariables) {
							
							if(var.getName().equals(variable)) {
								
								found = true;
								
								var.setValue(value);
								
								break;
								
							}
							
						}
						
						if(!found) {
							
							userDeclaredVariables.add(new Variable(variable, value));
							
						}
						
					} else if(line.charAt(i) == '+' || line.charAt(i) == '-' || line.charAt(i) == '*'
							|| line.charAt(i) == '/' || line.charAt(i) == '^' || line.charAt(i) == '%' 
							|| line.charAt(i) == '~') {
						
						char operator = line.charAt(i);
						String variable = line.substring(0, i).trim();
						String value = line.substring(i+1, line.length()).trim();
						
						Variable operatingVariable = null;
						
						for(Variable var: userDeclaredVariables) {
							
							if(var.getName().equals(variable)) {
								
								operatingVariable = var;
								
								break;
								
							}
							
						}
						
						if(operatingVariable == null) {
							
							executeSuccessful = false;
							terminate("Variable Not Found Exception");
							
							return;
							
						}
						
						boolean valueFound = false;
						
						for(Variable var: userDeclaredVariables) {
							
							if(var.getName().equals(value)) {
								
								valueFound = true;
								
								operatingVariable.calculate(operator, var.getValue());
								
								break;
								
							}
							
						}
						
						if(!valueFound) {
							
							operatingVariable.calculate(operator, value);
							
						}
						
					}
					
				}
				
			}
			
			if(executeSuccessful) {
				
				StyledDocument doc = Console.consoleTextPane.getStyledDocument();
				Style greenStyle = Console.consoleTextPane.addStyle("Green", null);
				Style blackStyle = Console.consoleTextPane.addStyle("Black", null);
			    StyleConstants.setForeground(greenStyle, Color.GREEN);

			    // Inherits from "Red"; makes text red and underlined
			    StyleConstants.setUnderline(greenStyle, true);
			    
			    try {
			    	
			    	if(!Console.consoleTextPane.getText().isEmpty()) {
			    		
			    		doc.insertString(doc.getLength(), "\n", greenStyle);
			    		
			    	} 
			    		
			    	doc.insertString(doc.getLength(), "BUILD SUCCESSFUL", greenStyle);
					StyleConstants.setForeground(blackStyle, Color.black);
					StyleConstants.setUnderline(blackStyle, false);
					doc.insertString(doc.getLength(), " ", blackStyle);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			    
			} 
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void splitMethod() {
		
		
		
	}
	
	public static void terminate(String message) {
		
		StyledDocument doc = Console.consoleTextPane.getStyledDocument();
		Style greenStyle = Console.consoleTextPane.addStyle("Red", null);
		Style blackStyle = Console.consoleTextPane.addStyle("Black", null);
	    StyleConstants.setForeground(greenStyle, Color.RED);

	    // Inherits from "Red"; makes text red and underlined
	    StyleConstants.setUnderline(greenStyle, true);
	    
	    try {
	    	if(!Console.consoleTextPane.getText().isEmpty()) {
	    		
	    		doc.insertString(doc.getLength(), "\n", greenStyle);
	    		
	    	} 
			doc.insertString(doc.getLength(), message + "\nBUILD FAILED", greenStyle);
			StyleConstants.setForeground(blackStyle, Color.black);
			StyleConstants.setUnderline(blackStyle, false);
			doc.insertString(doc.getLength(), " ", blackStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * JOptionPane.showMessageDialog(null,
						"File Creation Failed! \n\n"
								+ "click 'ok' to continue...",
						"FAILURE", JOptionPane.WARNING_MESSAGE);
				
				return;
	 */
	
}
