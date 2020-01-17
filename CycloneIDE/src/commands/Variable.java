package commands;

import utils.FileExecutionTool;

/*
 * object class that stores an user declared variable
 */
public class Variable {
	
	private String datatype;
	private String name;
	private String value;
	
	private String invalidCharacters = "~!@#$%^&*()_+`-= {}|[]\\:\";\'<>?,./";
	
	public Variable(String name, String value, boolean isCounterVariable, int lineNumber) {
		
		if((int)name.charAt(0) >= 48 && (int)name.charAt(0) <= 57) {
			
			FileExecutionTool.terminate("Invalid Variable Name, cannot start with a number: Line " + lineNumber);
			return;
			
		}
		
		for(char character: invalidCharacters.toCharArray()) {
			
			if(name.contains(character + "")) {
				FileExecutionTool.terminate("Invalid Variable Name, cannot have symbols: Line " + lineNumber);
				return;
			}
			
		}
		
		this.name = name;
		this.value = value;
		
		datatype = getDatatype(value, lineNumber);
		
		if(isCounterVariable) {
			toJava(lineNumber);
		}
		
	}
	
	public void calculate(String inputValue, String operator, int lineNumber) {
		
		if((getDatatype(inputValue, lineNumber).equals("int") && datatype.equals("int")) ||
				(getDatatype(inputValue, lineNumber).equals("double") && datatype.equals("double"))||
				(getDatatype(inputValue, lineNumber).equals("int") && datatype.equals("double"))||
				(getDatatype(inputValue, lineNumber).equals("String") && datatype.equals("String") && operator.equals("+"))) {
			
			FileExecutionTool.translatedCode += "\n" + name + " = " + name + " " + operator + " " + inputValue + ";";
			
		} else {
			
			FileExecutionTool.terminate("Invalid Syntax. Unmatching Datatype/Value: Line " + lineNumber);
			
		}
		
	}

	public static String getDatatype(String variable, int lineNumber) {
		
		if(variable.equals(FileExecutionTool.userCommands.get("true")) || variable.equals(FileExecutionTool.userCommands.get("false"))) {
			return "boolean";
		}
		
		try {
			int testValue = Integer.parseInt(variable);
			return "int";
		} catch (NumberFormatException e) { }
		
		try {
			double testValue = Double.parseDouble(variable);
			return "double";
		} catch (NumberFormatException e) { }
		
		if(variable.charAt(0) == '"' && variable.charAt(variable.length()-1) == '"') {
			return "String";
		} 
		
		for(Variable existingVariable: FileExecutionTool.userDeclaredVariables) {
			if(existingVariable.getName().equals(variable)) {
				
				return Variable.getDatatype(existingVariable.getValue(), lineNumber);
				
			}
		}
		
		if(FileExecutionTool.executeSuccessful) {
			
			FileExecutionTool.terminate("Unrecongnizable Datatype/Value: Line " + lineNumber);
			
		}
		
		return "String";
		
	}

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
		
		if(getDatatype(value, lineNumber) == null) {
			FileExecutionTool.terminate("InputMismatchException: Line " + lineNumber + ". Variable: " + datatype + ", Input: " + getDatatype(value, lineNumber));
			return;
		}
		
		if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals("int") && datatype.equals("int")) {
			int testValue = Integer.parseInt(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals("double") && datatype.equals("double")) {
			
			double testValue = Double.parseDouble(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals("boolean") && datatype.equals("boolean")) {
			
			boolean testValue = Boolean.parseBoolean(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value, lineNumber) != null && getDatatype(value, lineNumber).equals("String") && datatype.equals("String")) {
			
			this.value = value.substring(1, value.length());
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else {
			
			FileExecutionTool.executeSuccessful = false;
			FileExecutionTool.terminate("InputMismatchException: Line " + lineNumber + ". Variable: " + datatype + ", Input: " + getDatatype(value, lineNumber));
			
		}
		
		//FileExecutionTool.translatedCode += "\n} catch(Exception error) {"
		//		+ "\nSystem.out.println(\"InputMismatchException: line \" + " + lineNumber + ");\n}";
		
	}
	
	public void toJava(int lineNumber) {
		
		if(getDatatype(value, lineNumber) != null && datatype.equals("int")) {
			
			FileExecutionTool.translatedCode += "\nint " + name + " = " + value + ";";
		
		} else if(getDatatype(value, lineNumber) != null && datatype.equals("double")) {
			
			FileExecutionTool.translatedCode += "\ndouble " + name + " = " + value + ";";
		
		} else if(getDatatype(value, lineNumber) != null && datatype.equals("boolean")) {
			
			if(value.equals(FileExecutionTool.userCommands.get("true")))
				FileExecutionTool.translatedCode += "\nboolean " + name + " = " + "true;";
			else
				FileExecutionTool.translatedCode += "\nboolean " + name + " = " + "false;";
			
		} else {
			
			FileExecutionTool.translatedCode += "\nString " + name + " = " + value + ";";
			
		}
		
	}

}
