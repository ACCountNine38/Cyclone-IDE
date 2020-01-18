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
			FileExecutionTool.terminate("Invalid Variable Name, Cannot Start With a Number: Line " + lineNumber, lineNumber);
			return;
			
		}
		
		// checks if the variable name is the user declared boolean values
		if(name.equals(FileExecutionTool.userCommands.get("true")) || name.equals(FileExecutionTool.userCommands.get("false"))) {
			
			// terminate the program and display error
			FileExecutionTool.terminate("Invalid Variable Name, Cannot be a Boolean Value: Line " + lineNumber, lineNumber);
			return;
			
		}
		
		// checks if the variable contains a symbol
		for(char character: invalidCharacters.toCharArray()) {
			
			if(name.contains(character + "")) {
				
				FileExecutionTool.terminate("Invalid Variable Name: Line " + lineNumber, lineNumber);
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
	
	public static void getRandom(String input) {
		
		boolean isValid = true;
		// tests if the variable can be can be converted to integer
		try {
			int testValue = Integer.parseInt(input);
			isValid = true;
		} catch (NumberFormatException e) {
			isValid = false;
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
			
		}
		else if((getDatatype(inputValue, lineNumber).equals("int") && datatype.equals("int")) ||
				(getDatatype(inputValue, lineNumber).equals("double") && datatype.equals("double")) ||
				(getDatatype(inputValue, lineNumber).equals("int") && datatype.equals("double")) ||
				(getDatatype(inputValue, lineNumber).equals("String") && datatype.equals("String") && operator.equals("+"))) {
			
			if(inputValue.equals("0") && operator.equals("/")) {
				
				FileExecutionTool.terminate("Cannot divide by 0: Line " + lineNumber, lineNumber);
				
			} else {
			
				FileExecutionTool.translatedCode += "\n" + name + " = " + name + " " + operator + " " + inputValue + ";";
			
			}
			
		} 
		// other cases are not allowed for calculation
		else {
			
			FileExecutionTool.terminate("Invalid Syntax. Unmatching Datatype/Value: Line " + lineNumber, lineNumber);
			
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
		FileExecutionTool.terminate("Unrecongnizable Datatype: Line " + lineNumber, lineNumber);
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
			FileExecutionTool.terminate("InputMismatchException: Line " + lineNumber + ". Variable: " + datatype + ", Input: " + getDatatype(value, lineNumber), lineNumber);
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
			this.value = value.substring(1, value.length());
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals(datatype)) {
			
			this.value = value + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else {
			
			// terminate the program if value data-type is invalid
			FileExecutionTool.terminate("InputMismatchException: Line " + lineNumber + ". Variable: " + datatype + ", Input: " + getDatatype(value, lineNumber), lineNumber);
			
		}
		
	}

}
