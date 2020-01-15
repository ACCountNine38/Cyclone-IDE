package commands;

import java.math.BigInteger;

import utils.FileExecutionTool;

public class Variable {
	
	private String datatype;
	private String name;
	private String value;
	
	public Variable(String name, String value, boolean imbeded) {
		
		this.name = name;
		this.value = value;
		
		datatype = getDatatype(value);
		
		if(imbeded) {
			toJava();
		}
		
	}
	
	public void calculate(String calculation) {
		
		FileExecutionTool.translatedCode += "\n" + name + " = " + name + calculation + ";";
		
	}
	
	public void calculate(char operation, String input) {
		
		if(datatype.equals("int") && getDatatype(input).equals("int")) {
			
			if(operation == '+') {
				
				BigInteger bigInt = new BigInteger(value).add(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				long answer = Long.parseLong(value) + Long.parseLong(input);
				value = answer + "";
				
			} else if(operation == '-') {
				
				BigInteger bigInt = new BigInteger(value).subtract(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				
				long answer = Long.parseLong(value) - Long.parseLong(input);
				value = answer + "";
				
			} else if(operation == '*') {
				
				BigInteger bigInt = new BigInteger(value).multiply(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				long answer = Long.parseLong(value) * Long.parseLong(input);
				value = answer + "";
				
			} else if(operation == '/') {
				
				BigInteger bigInt = new BigInteger(value).divide(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
					
				long answer = Long.parseLong(value) / Long.parseLong(input);
				value = answer + "";
				
			} else if(operation == '%') {
				
				BigInteger bigInt = new BigInteger(value).mod(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				
				long answer = Long.parseLong(value) % Long.parseLong(input);
				value = answer + "";
				
			} else if(operation == '^') {
				
				BigInteger bigInt = new BigInteger(value).pow(Integer.parseInt(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				
				long answer = (long)Math.pow(Long.parseLong(value), Long.parseLong(input));
				value = answer + "";
				
			} else if(operation == '~') {
				
				long answer = (long)Math.pow((double)Long.parseLong(value), (double)1/Long.parseLong(input));
				value = answer + "";
				
			}

		} else if(datatype.equals("double") && getDatatype(input).equals("double")) {
			
			if(operation == '+') {
				
				BigInteger bigInt = new BigInteger(value).add(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf((long)Double.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				double answer = Double.parseDouble(value) + Double.parseDouble(input);
				value = answer + "";
				
			} else if(operation == '-') {
				
				BigInteger bigInt = new BigInteger(value).subtract(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				
				double answer = Double.parseDouble(value) - Double.parseDouble(input);
				value = answer + "";
				
			} else if(operation == '*') {
				
				BigInteger bigInt = new BigInteger(value).multiply(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				double answer = Double.parseDouble(value) * Double.parseDouble(input);
				value = answer + "";
				
			} else if(operation == '/') {
				
				BigInteger bigInt = new BigInteger(value).divide(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
					
				double answer = Double.parseDouble(value) / Double.parseDouble(input);
				value = answer + "";
				
			} else if(operation == '%') {
				
				BigInteger bigInt = new BigInteger(value).mod(new BigInteger(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				
				double answer = Double.parseDouble(value) % Double.parseDouble(input);
				value = answer + "";
				
			} 
			
		} else if(datatype.equals("boolean")) {
			
			FileExecutionTool.executeSuccessful = false;
			FileExecutionTool.terminate("Invalid Operation Exception");
			
		} else if(datatype.equals("str")) {
			
			if(operation == '+') {
				
				value += input;
				
			} else {
				
				FileExecutionTool.executeSuccessful = false;
				FileExecutionTool.terminate("Invalid Operation Exception");
				
			}
			
		} 
		
	}

	public String getDatatype(String variable) {
		
		if(variable.equals(FileExecutionTool.userCommands.get("true")) || variable.equals(FileExecutionTool.userCommands.get("false"))) {
			return "boolean";
		}
		
		try {
			long testValue = Long.parseLong(variable);
			return "long";
		} catch (NumberFormatException e) { }
		
		try {
			double testValue = Double.parseDouble(variable);
			return "double";
		} catch (NumberFormatException e) { }
		
		if(variable.charAt(0) == '"' && variable.charAt(variable.length()-1) == '"') {
			value = variable.substring(1, variable.length()-1);
			return "String";
		} 
		
		else if(variable.charAt(0) == '\'' && variable.charAt(variable.length()-1) == '\'' && variable.length() == 3) {
			value = variable.substring(1, variable.length()-1);
			return "char";
		}
		
		for(Variable existingVariable: FileExecutionTool.userDeclaredVariables) {
			if(existingVariable.getName().equals(variable)) {
				
				return existingVariable.getDatatype(existingVariable.getValue());
				
			}
		}
		
		return null;
		
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

	public void setValue(String value) {
		
		if(getDatatype(value).equals("long") && datatype.equals("long")) {
			long testValue = Long.parseLong(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value).equals("double") && datatype.equals("double")) {
			
			double testValue = Double.parseDouble(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value).equals("boolean") && datatype.equals("boolean")) {
			
			boolean testValue = Boolean.parseBoolean(value);
			this.value = testValue + "";
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value).equals("String") && datatype.equals("String")) {
			
			this.value = value;
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else if(getDatatype(value).equals("char") && datatype.equals("char")) {
			
			this.value = value;
			FileExecutionTool.translatedCode += "\n" + name + " = " + value + ";";
			
		} else {
			
			FileExecutionTool.executeSuccessful = false;
			FileExecutionTool.terminate("Input Mismatch Exception");
			
		}
		
	}
	
	public void toJava() {
		
		if(datatype.equals("long")) {
			
			FileExecutionTool.translatedCode += "\nlong " + name + " = " + value + ";";
		
		} else if(datatype.equals("double")) {
			
			FileExecutionTool.translatedCode += "\ndouble " + name + " = " + value + ";";
		
		} else if(datatype.equals("boolean")) {
			
			if(value.equals(FileExecutionTool.userCommands.get("true")))
				FileExecutionTool.translatedCode += "\nboolean " + name + " = " + "true;";
			else
				FileExecutionTool.translatedCode += "\nboolean " + name + " = " + "false;";
			
		} else if(datatype.equals("char")) {
			
			FileExecutionTool.translatedCode += "\nchar " + name + " = \'" + value + "\';";
		
		} else {
			
			FileExecutionTool.translatedCode += "\nString " + name + " = \"" + value + "\";";
			
		}
		
	}

}
