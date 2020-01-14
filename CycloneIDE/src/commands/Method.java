package commands;

import java.util.ArrayList;

import objects.Variable;
import utils.FileExecutionTool;

public class Method {
	
	private ArrayList<String> code = new ArrayList<String>();
	
	private String name;
	private String datatype;
	private ArrayList<Variable> parameters = new ArrayList<Variable>();
	
	// 
	public Method(String name, String datatype, String parameters) {
		
		this.name = name;
		this.datatype = datatype;
		breakDownParameters(parameters);
		
	}
	
	// method that breaks a input String into the method's parameters
	private void breakDownParameters(String input) {
		
		
		
	}
	
	public static void declareMain() {
		
		FileExecutionTool.translatedCode += "\n"
				+ "public static void main(String[] args) {";
		return;
		
	}
	
	private void toJava() {
		
		
	}
	
}
