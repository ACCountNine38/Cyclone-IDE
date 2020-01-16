package commands;

import utils.FileExecutionTool;

public class Variable {
	
	private String datatype;
	private String name;
	private String value;
	
	private String invalidCharacters = "~!@#$%^&*()_+`-= {}|[]\\:\";\'<>?,./";
	
	public Variable(String name, String value, boolean isCounterVariable, int lineNumber) {
		
		for(char character: invalidCharacters.toCharArray()) {
			
			if(name.contains(character + "")) {
				FileExecutionTool.terminate("Invalid Variable Name: Line " + lineNumber);
				return;
			}
			
		}
		
		this.name = name;
		this.value = value;
		
		datatype = getDatatype(value);
		
		if(isCounterVariable) {
			toJava();
		}
		
	}
	
	public void calculate(String calculation) {
		
		FileExecutionTool.translatedCode += "\n" + name + " = " + name + calculation + ";";
		
	}

	public String getDatatype(String variable) {
		
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
			value = variable.substring(1, variable.length()-1);
			return "String";
		} 
		
		for(Variable existingVariable: FileExecutionTool.userDeclaredVariables) {
			if(existingVariable.getName().equals(variable)) {
				
				return existingVariable.getDatatype(existingVariable.getValue());
				
			}
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
		
		if(getDatatype(value) == null) {
			FileExecutionTool.terminate("InputMismatchException: Line " + lineNumber + ". Variable: " + datatype + ", Input: " + getDatatype(value));
			return;
		}
		
		//FileExecutionTool.translatedCode += "\ntry { ";
		
		if(getDatatype(value) != null && getDatatype(value).equals("int") && datatype.equals("int")) {
			int testValue = Integer.parseInt(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value) != null && getDatatype(value).equals("double") && datatype.equals("double")) {
			
			double testValue = Double.parseDouble(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value) != null && getDatatype(value).equals("boolean") && datatype.equals("boolean")) {
			
			boolean testValue = Boolean.parseBoolean(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value) != null && getDatatype(value).equals("String") && datatype.equals("String")) {
			
			this.value = value;
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else {
			
			FileExecutionTool.executeSuccessful = false;
			FileExecutionTool.terminate("InputMismatchException: Line " + lineNumber + ". Variable: " + datatype + ", Input: " + getDatatype(value));
			
		}
		
		//FileExecutionTool.translatedCode += "\n} catch(Exception error) {"
		//		+ "\nSystem.out.println(\"InputMismatchException: line \" + " + lineNumber + ");\n}";
		
	}
	
	public void toJava() {
		
		if(getDatatype(value) != null && datatype.equals("int")) {
			
			FileExecutionTool.translatedCode += "\nint " + name + " = " + value + ";";
		
		} else if(getDatatype(value) != null && datatype.equals("double")) {
			
			FileExecutionTool.translatedCode += "\ndouble " + name + " = " + value + ";";
		
		} else if(getDatatype(value) != null && datatype.equals("boolean")) {
			
			if(value.equals(FileExecutionTool.userCommands.get("true")))
				FileExecutionTool.translatedCode += "\nboolean " + name + " = " + "true;";
			else
				FileExecutionTool.translatedCode += "\nboolean " + name + " = " + "false;";
			
		} else {
			
			FileExecutionTool.translatedCode += "\nString " + name + " = \"" + value + "\";";
			
		}
		
	}

}
