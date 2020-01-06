package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import display.Console;

public class FileExecutionTool {
	
	public static HashMap<String, String> userCommands = new HashMap<String, String>();
	
	public FileExecutionTool() {
		
		updateCommands();
		
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
	
	public static void executeFile(File file) {
		
		try {
			
			Scanner input = new Scanner(file);
			
			while(input.hasNext()) {
				
				String line = input.nextLine();
				
				for(HashMap.Entry<String, String> command : userCommands.entrySet()) {
					
					if(line.contains(" " + command.getValue() + " ")) {
						
						if(command.getKey().equals("print")) {
							
							printFunction(line.substring(line.indexOf(command.getValue())));
							
						} else if(command.getKey().equals("printl")) {
							
							
							
						} else if(command.getKey().equals("input")) {
							
							
							
						}
						
					}
					
				}
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private static void checkFunctionAvability(String command, String line) {
		
		
		
	}
	
	private static void printFunction(String text) {
		
		Console.consoleTextArea.setText(Console.consoleTextArea.getText() + text);
		
	}
	
	private static void printLineFunction(String text) {
		
		Console.consoleTextArea.setText(Console.consoleTextArea.getText() + "\n" + text);
		
	}

}
