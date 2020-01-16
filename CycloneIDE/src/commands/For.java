package commands;

import utils.FileExecutionTool;

public class For {
	/*
	 * private String conditions;
	 * 
	 * private ArrayList<Variable> localVariables = new ArrayList<Variable>();
	 * 
	 * private Variable counterVariable; private String condition; private String
	 * incrementation;
	 * 
	 * public For(String conditions) {
	 * 
	 * this.conditions = conditions;
	 * 
	 * breakDownConditions(); toJava();
	 * 
	 * }
	 */
	public static void initialize(String conditions, int lineNumber) {
		breakDownConditions(conditions, lineNumber);

	}

	private static void breakDownConditions(String conditions, int lineNumber) {
		String loopProperties = conditions;
		String initializedVariables = loopProperties.substring(0, loopProperties.indexOf(":") + 1).trim();
		Variable counterVariable = new Variable(
				initializedVariables.substring(0, initializedVariables.indexOf("=")).trim(), initializedVariables
						.substring(initializedVariables.indexOf("=") + 1, initializedVariables.indexOf(":")).trim(),
				false, lineNumber);

		loopProperties = loopProperties.substring(loopProperties.indexOf(":") + 1);
		String condition = loopProperties.substring(0, loopProperties.indexOf(":")).trim();

		loopProperties = loopProperties.substring(loopProperties.indexOf(":") + 1);
		String incrementation = loopProperties.substring(0).trim();

		toJava(counterVariable, condition, incrementation, lineNumber);

		/*
		 * while(true) {
		 * 
		 * try {
		 * 
		 * String variableName = initializedVariables.substring(0,
		 * initializedVariables.indexOf("=")).trim(); String variableValue =
		 * initializedVariables.substring(initializedVariables.indexOf("=")+1,
		 * initializedVariables.indexOf(",")).trim(); localVariables.add(new
		 * Variable(variableName, variableValue, false));
		 * 
		 * initializedVariables =
		 * initializedVariables.substring(initializedVariables.indexOf(variableValue)+
		 * variableValue.length());
		 * 
		 * } catch(StringIndexOutOfBoundsException error) {
		 * 
		 * break;
		 * 
		 * }
		 * 
		 * }
		 */

	}

	private static void toJava(Variable counterVariable, String condition, String incrementation, int lineNumber) {

		FileExecutionTool.translatedCode += "\nfor(" + counterVariable.getDatatype(counterVariable.getValue(), lineNumber) + " "
				+ counterVariable.getName() + " = " + counterVariable.getValue() + "; " + condition + "; "
				+ incrementation + ") {";

	}

}
