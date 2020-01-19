package commands;

import utils.FileExecutionTool;

/*
 * object class that stores an user declared variable
 * detects the data-type of the input value, checks for validation of the variables
 * translates data-type to Java
 */
public class Variable {
	
	// required fields of a variable
	private String datatype;
	private String name;
	private String value;
	
	// characters that cannot be contained in a variable
	private String invalidCharacters = "~!@#$%^&*()_+`-= {}|[]\\:\";\'<>?,./";
	
	// constructor of variable checks if the input name and values are valid and sets the attributes
	public Variable(String name, String value, boolean notCounterVariable, int lineNumber) {
		
		// checks if the variable starts with a number
		if((int)name.charAt(0) >= 48 && (int)name.charAt(0) <= 57) {
			
			// terminate the program and display error
			FileExecutionTool.terminate("Invalid Variable Name, Cannot Start With a Number: Line ", lineNumber);
			return;
			
		}
		
		// checks if the variable name is the user declared boolean values
		if(name.equals(FileExecutionTool.userCommands.get("true")) || name.equals(FileExecutionTool.userCommands.get("false"))) {
			
			// terminate the program and display error
			FileExecutionTool.terminate("Invalid Variable Name, Cannot be a Boolean Value: Line ", lineNumber);
			return;
			
		}
		
		// checks if the variable contains a symbol
		for(char character: invalidCharacters.toCharArray()) {
			
			if(name.contains(character + "")) {
				
				FileExecutionTool.terminate("Invalid Variable Name: Line ", lineNumber);
				return;
				
			}
			
		}
		
		// sets the fields and validates the data-type
		this.name = name;
		this.value = value;
		datatype = getDatatype(value, lineNumber);
		
		// if the variable is not a counter variable, then translate it to java
		if(notCounterVariable) {
			
			toJava(lineNumber);
			
		}
		
	}
	
	// method that set a random value to the variable
	public void getRandom(String input, int lineNumber) {
		
		// loop through the variable list and sees if the input matches a variable already declared
		for(Variable var: FileExecutionTool.userDeclaredVariables) {
			
			if(input.equals(var.name)) {
				
				// tests if the variable can be can be converted to integer
				if(datatype.equals("int")) {
					
					try {
						
						// try and catch to see if a integer value can be made
						int randomizer = Integer.parseInt(var.getValue());
						
						// translate the code to Java and set the value to the randomized value
						value = var.getValue() + "";
						FileExecutionTool.translatedCode += "\n" + name + " = (int)(Math.random()*" + var.getName() + ");";
						return;
						
					} catch (NumberFormatException error) { }
					
				}
				
				// tests if the variable can be can be converted to double
				if(datatype.equals("double")) {
					try {
						
						// try and catch to see if a double value can be made
						double randomizer = Double.parseDouble(var.getValue());
						
						// translate the code to Java and set the value to the randomized value
						value = var.getValue() + "";
						FileExecutionTool.translatedCode += "\n" + name + " = Math.random()*" + var.getName() + ";";
						return;
						
					} catch (NumberFormatException error) { }
				}
				
			}
			
		}
		
		// tests if the variable can be can be converted to integer
		if(datatype.equals("int")) {
			
			try {
				
				int randomizer = Integer.parseInt(input);
				
				// translate the code to Java and set the value to the randomized value
				value = randomizer + "";
				FileExecutionTool.translatedCode += "\n" + name + " = (int)(Math.random()*" + randomizer + ");";
				return;
				
			} catch (NumberFormatException error) { }
			
		}
		
		// tests if the variable can be can be converted to double
		if(datatype.equals("double")) {
			try {
				
				double randomizer = Double.parseDouble(input);
				
				// translate the code to Java and set the value to the randomized value
				value = randomizer + "";
				FileExecutionTool.translatedCode += "\n" + name + " = Math.random()*" + randomizer + ";";
				return;
				
			} catch (NumberFormatException error) { }
		}
		
		// output error message if value cannot be used to randomize
		FileExecutionTool.terminate("Invalid Input to Randomize. Must be Integer: Line ", lineNumber);
		
	}
	
	// method that set a random value to the variable
	public void parse(String input, int lineNumber) {
		
		for(Variable var: FileExecutionTool.userDeclaredVariables) {
			
			if(input.equals(var.name)) {
				
				// checks to see if the variable can be parsed by the input
				if(var.getDatatype().equals("String")) {
					
					if(datatype.equals("int")) {
						
						try {
							
							// tests to see if the number can be converted into an integer
							int integerValue = Integer.parseInt(var.getValue().substring(1, var.getValue().length() - 1));
							value = var.getValue();
							FileExecutionTool.translatedCode += "\n" + name + " = Integer.parseInt(" + var.getName() + ");";
							return;
							
						} catch (NumberFormatException error) {
							
							// output error message if it cannot
							FileExecutionTool.terminate("Invalid Variable/Input to Parse(keyword: int/double variable: String value): Line ", lineNumber);
							return;
							
						}
						
					} else if(datatype.equals("double")) {
						
						try {
							
							double doubleValue = Double.parseDouble(var.getValue().substring(1, var.getValue().length() - 1));
							value = var.getValue();
							FileExecutionTool.translatedCode += "\n" + name + " = Double.parseDouble(" + var.getName() + ");";
							return;
							
						} catch (NumberFormatException error) {
							
							FileExecutionTool.terminate("Invalid Variable/Input to Parse(keyword: int/double variable: String value): Line ", lineNumber);
							return;
							
						}
						
					} else {
						
						FileExecutionTool.terminate("Invalid Variable/Input to Parse(keyword: int/double variable: String value): Line ", lineNumber);
						return;
						
					}
										
				} else {
					
					FileExecutionTool.terminate("Invalid Variable/Input to Parse(keyword: int/double variable: String value): Line ", lineNumber);
					return;
					
				}
				
			}
			
		}
		
		// tests if the variable can be can be converted to integer if its not an already declared variable
		if(getDatatype(input, lineNumber).equals("String")) {
			
			// checks to see if the variable can be parsed by the input
			if(datatype.equals("int")) {
				
				try {

					// tests to see if the number can be converted into an integer
					int integerValue = Integer.parseInt(input.substring(1, input.length() - 1));
					value = input;
					FileExecutionTool.translatedCode += "\n" + name + " = Integer.parseInt(" + input + ");";
					return;
					
				} catch (NumberFormatException error) {
					
					FileExecutionTool.terminate("Invalid Variable/Input to Parse(keyword: int/double variable: String value): Line ", lineNumber);
					return;
					
				}
				
			} else if(datatype.equals("double")) {
				
				try {
					
					// tests to see if the number can be converted into a double
					double doubleValue = Double.parseDouble(input.substring(1, input.length() - 1));
					value = input;
					FileExecutionTool.translatedCode += "\n" + name + " = Double.parseDouble(" + input + ");";
					return;
					
				} catch (NumberFormatException error) {
					
					FileExecutionTool.terminate("Invalid Variable/Input to Parse(keyword: int/double variable: String value): Line ", lineNumber);
					return;
					
				}
				
			} else {
				
				FileExecutionTool.terminate("Invalid Variable/Input to Parse(keyword: int/double variable: String value): Line ", lineNumber);
				return;
				
			}
								
		} else {
			
			FileExecutionTool.terminate("Invalid Variable/Input to Parse(keyword: int/double variable: String value): Line ", lineNumber);
			return;
			
		}
		
	}
	
	// method that turns a value to String, to be used by a String variable
	public void toStringValue(String input, int lineNumber) {
		
		if(datatype.equals("String")) {
			
			// checks if the input is a variable that already exist
			for(Variable var: FileExecutionTool.userDeclaredVariables) {
				
				if(input.equals(var.name)) {
					
					// tests if the variable is a String, then translate it to Java to execute
					if(var.getDatatype().equals("String")) {
						
						FileExecutionTool.translatedCode += "\n" + name + " = " + var.getName() + ";";
						return;
											
					} else {
						
						FileExecutionTool.translatedCode += "\n" + name + " = " + var.getName() + " + \"\";";
						return;
						
					}
					
				}
				
			}
			
			// if the input is not a declared variable, tests if the variable is a String, then translate it to Java to execute
			if(getDatatype(input, lineNumber).equals("String")) {
				
				FileExecutionTool.translatedCode += "\n" + name + " = " + input + ";";
				return;
									
			} else {
				
				FileExecutionTool.translatedCode += "\n" + name + " = " + input + " + \"\";";
				return;
				
			}
			
		} else {
			
			FileExecutionTool.terminate("Invalid Variable, To_String Can Only be Applied to String Variables(keyword: String variable: value): Line ", lineNumber);
			
		}
		
	}
	
	// method that processes the variable calculations
	public void calculate(String inputValue, String operator, int lineNumber) {
		
		/*
		 * integer can only calculate with integer, if the value is double, then it is casted to int
		 * double can calculate with integer and double
		 * string can only be added with a string
		 */
		if((getDatatype(inputValue, lineNumber).equals("double") && datatype.equals("int"))) {
			
			FileExecutionTool.translatedCode += "\n" + name + " = " + name + " " + operator + " (int)" + inputValue + ";";
			
		} else if((getDatatype(inputValue, lineNumber).equals("int") && datatype.equals("int")) ||
				(getDatatype(inputValue, lineNumber).equals("double") && datatype.equals("double")) ||
				(getDatatype(inputValue, lineNumber).equals("int") && datatype.equals("double")) ||
				(getDatatype(inputValue, lineNumber).equals("String") && datatype.equals("String") && operator.equals("+"))) {
			
			if(inputValue.equals("0") && operator.equals("/")) {
				
				FileExecutionTool.terminate("Cannot divide by 0: Line ", lineNumber);
				
			} else {
				
				FileExecutionTool.translatedCode += "\n" + name + " = " + name + " " + operator + " " + inputValue + ";";
			
			}
			
		} else {
			
			// other cases are not allowed for calculation
			FileExecutionTool.terminate("Invalid Syntax. Unmatching Datatype/Value: Line ", lineNumber);
			
		}
		
	}

	// method that gets the data-type of a input variable
	public static String getDatatype(String variable, int lineNumber) {
		
		// checks if the variable is an instance of boolean
		if(variable.equals(FileExecutionTool.userCommands.get("true")) || variable.equals(FileExecutionTool.userCommands.get("false"))) {
			return "boolean";
		}
		
		// tests if the variable can be can be converted to integer
		try {
			int testValue = Integer.parseInt(variable);
			return "int";
		} catch (NumberFormatException e) { }
		
		// tests if the variable can be can be converted to double
		try {
			double testValue = Double.parseDouble(variable);
			return "double";
		} catch (NumberFormatException e) { }
		
		// tests if the variable is a string
		if(variable.charAt(0) == '"' && variable.charAt(variable.length()-1) == '"') {
			return "String";
		} 
		
		// checks if the variable is initialized before
		for(Variable existingVariable: FileExecutionTool.userDeclaredVariables) {
			if(existingVariable.getName().equals(variable)) {
				
				return Variable.getDatatype(existingVariable.getValue(), lineNumber);
				
			}
		}
		
		// other cases are not permitted
		FileExecutionTool.terminate("Unrecongnizable Datatype: Line ", lineNumber);
		return "";
		
	}
	
	// method that translates the variable to Java
	public void toJava(int lineNumber) {
		
		// checks if the data-type of this variable is validated and translates each variable to Java code
		if(getDatatype(value, lineNumber) != null && datatype.equals("int")) {
			
			FileExecutionTool.translatedCode += "\nint " + name + " = " + value + ";";
		
		} else if(getDatatype(value, lineNumber) != null && datatype.equals("double")) {
			
			FileExecutionTool.translatedCode += "\ndouble " + name + " = " + value + ";";
		
		} else if(getDatatype(value, lineNumber) != null && datatype.equals("boolean")) {
			
			// because true/false keywords can be customized by the user, it is translated as "true" and "false" for Java
			if(value.equals(FileExecutionTool.userCommands.get("true")))
				FileExecutionTool.translatedCode += "\nboolean " + name + " = " + "true;";
			else
				FileExecutionTool.translatedCode += "\nboolean " + name + " = " + "false;";
			
		} else {
			
			FileExecutionTool.translatedCode += "\nString " + name + " = " + value + ";";
			
		}
		
	}
	
	public String getDatatype() {
		return datatype;
	}

	// getters and setters
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value, int lineNumber) {
		
		// checks if the input value's data-type is valid
		if(getDatatype(value, lineNumber) == null) {
			FileExecutionTool.terminate("InputMismatchException: " + "Variable: " + datatype + ", Input: " + getDatatype(value, lineNumber) + " Line: ", lineNumber);
			return;
		}
		
		if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals("int") && datatype.equals("double")) {

			// turn the value into String to be stored
			this.value = value + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals("double") && datatype.equals("int")) {
			
			// turn the value into String to be stored, cast the double to int
			this.value = value + "";
			FileExecutionTool.translatedCode += "\n" + name + " = (int)" + value + ";";
			
		} else if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals("String") && datatype.equals("String")) {
			
			// remove the quotation marks to be stored
			this.value = value.substring(0, value.length());
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals(datatype)) {
			
			this.value = value + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else {
			
			// terminate the program if value data-type is invalid
			FileExecutionTool.terminate("InputMismatchException: " + "Variable: " + datatype + ", Input: " + getDatatype(value, lineNumber) + " Line: ", lineNumber);
			
		}
		
	}

}
