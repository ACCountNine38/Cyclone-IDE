package utils;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import commands.Input;
import commands.Print;
import display.Console;
import display.Editor;
import main.Launcher;
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
		
		Console.consoleTextArea.setText("");
		
		System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.8.0_181");
		
//		PrintStream printStream = new PrintStream(new CustomOutputStream(Console.consoleTextArea));
//        System.setOut(printStream);
//        System.setErr(printStream);
        File jarFile = new File("src/JarRunFile.java");

        try {
            PrintWriter pr = new PrintWriter(jarFile);
            pr.print(String.format("public class JarRunFile {\n    "
            		+ "public static void main(String[] args) {\n        "
            		+ "System.out.println(\"hello world\");\n    }\n"
            		
            		+ "public static void execute() {\n" + 
            		"		\n" + 
            		"		main(null);\n" + 
            		"		\n" + 
            		"	}\n}"));
            
            pr.close();
        } catch (IOException e) {
            System.out.println("Class file was not created");
            e.printStackTrace();
        }
        
        System.out.println(jarFile.getAbsolutePath());
        
        String regex = "\\s*\\bsrc\\\\JarRunFile.java\\b\\s*";
        String binPath = jarFile.getAbsolutePath().replaceAll(regex, "bin");
        
        System.out.println(binPath);
        
		//SOURCE: https://stackoverflow.com/questions/2028193/specify-output-path-for-dynamic-compilation/7532171
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager sjfm = javaCompiler.getStandardFileManager(null, null, null); 

		String[] options = new String[] { "-d", binPath };
		File[] javaFiles = new File[] { new File("src/JarRunFile.java") };

		CompilationTask compilationTask = javaCompiler.getTask(null, null, null,
		        Arrays.asList(options),
		        null,
		        sjfm.getJavaFileObjects(javaFiles)
		);
		compilationTask.call();
		
		try {
			
			String[] params = null;
			Class<?> cls = Class.forName("JarRunFile");

			Method method;
			try {
				System.out.println("Executing x.main");
				method = cls.getMethod("main", String[].class);
				method.invoke(null, (Object) params);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch( ClassNotFoundException e ) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //JarRunFile.execute();
		
		/*
		Console.consoleTextArea.setText("");
		
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
				
				StyledDocument doc = Console.consoleTextArea.getStyledDocument();
				Style greenStyle = Console.consoleTextArea.addStyle("Green", null);
				Style blackStyle = Console.consoleTextArea.addStyle("Black", null);
			    StyleConstants.setForeground(greenStyle, Color.GREEN);

			    // Inherits from "Red"; makes text red and underlined
			    StyleConstants.setUnderline(greenStyle, true);
			    
			    try {
			    	
			    	if(!Console.consoleTextArea.getText().isEmpty()) {
			    		
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
		*/
		
	}
	
	public void splitMethod() {
		
		
		
	}
	
	public static void terminate(String message) {
		/*
		StyledDocument doc = Console.consoleTextArea.getStyledDocument();
		Style greenStyle = Console.consoleTextArea.addStyle("Red", null);
		Style blackStyle = Console.consoleTextArea.addStyle("Black", null);
	    StyleConstants.setForeground(greenStyle, Color.RED);

	    // Inherits from "Red"; makes text red and underlined
	    StyleConstants.setUnderline(greenStyle, true);
	    
	    try {
	    	if(!Console.consoleTextArea.getText().isEmpty()) {
	    		
	    		doc.insertString(doc.getLength(), "\n", greenStyle);
	    		
	    	} 
			doc.insertString(doc.getLength(), message + "\nBUILD FAILED", greenStyle);
			StyleConstants.setForeground(blackStyle, Color.black);
			StyleConstants.setUnderline(blackStyle, false);
			doc.insertString(doc.getLength(), " ", blackStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		*/
	}
	
	/*
	 * JOptionPane.showMessageDialog(null,
						"File Creation Failed! \n\n"
								+ "click 'ok' to continue...",
						"FAILURE", JOptionPane.WARNING_MESSAGE);
				
				return;
	 */
	
}
