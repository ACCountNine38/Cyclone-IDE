package utils;

import java.math.BigInteger;

public class Variable {
	
	private String datatype;
	private String name;
	private String value;
	
	public Variable(String name, String value) {
		
		this.name = name;
		this.value = value;
		
		datatype = getDatatype(value);
		
	}
	
	public void calculate(char operation, String input) {
		
		if(datatype.equals("long") && getDatatype(input).equals("long")) {
			
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
				
			} else if(operation == '^') {
				
				BigInteger bigInt = new BigInteger(value).pow(Integer.parseInt(input));
				if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
					FileExecutionTool.executeSuccessful = false;
					FileExecutionTool.terminate("Value Limit Exceeded Exception");
					return;
					
				}
				
				double answer = Math.pow(Double.parseDouble(value), Double.parseDouble(input));
				value = answer + "";
				
			} else if(operation == '~') {
				
				double answer = Math.pow((double)Long.parseLong(value), (double)1/Long.parseLong(input));
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
		
		try {
			long testValue = Long.parseLong(variable);
			return "long";
		} catch (NumberFormatException e) { }
		
		try {
			double testValue = Double.parseDouble(variable);
			return "double";
		} catch (NumberFormatException e) { }
		
		try {
			boolean testValue = Boolean.parseBoolean(variable);
			datatype = "boolean";
		} catch (NumberFormatException e) { }
		
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

	public void setValue(String value) {
		
		if(getDatatype(value).equals("long") && datatype.equals("long")) {
			long testValue = Long.parseLong(value);
			this.value = testValue + "";
			
		} else if(getDatatype(value).equals("double") && datatype.equals("double")) {
			
			double testValue = Double.parseDouble(value);
			this.value = testValue + "";
			
		} else if(getDatatype(value).equals("boolean") && datatype.equals("boolean")) {
			
			boolean testValue = Boolean.parseBoolean(value);
			this.value = testValue + "";
			
		} else if(getDatatype(value).equals("String") && datatype.equals("String")) {
			
			this.value = value;
			
		} else {
			
			FileExecutionTool.executeSuccessful = false;
			FileExecutionTool.terminate("Input Mismatch Exception");
			
		}
		
	}

}
