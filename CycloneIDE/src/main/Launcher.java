package main;

import display.IDEInterface;

/*
 * Names: Alan Sun and Dylan Williams
 * 
 * Contributions:
 * 		Alan
 * 		- File Execution Tool
 * 		- Converting code from java to Cyclone 
 * 		- Checked for all possible consequences of code input errors
 * 		- Print statement and recursive error checking
 * 		- Control structures and recursive error checking
 * 		- User input and error checking
 * 		- Loop and recursive error checking
 * 		- Inequality operators used of conditions
 * 		- "and" and "or" operators
 * 		- Variable object
 * 		- Variable declaration
 * 		- Variable data types
 * 		- Variable calculations
 * 		- Perform basic operations on variables (+,-,*,-)
 * 		- parseInt and parseDouble functions
 * 		- toString function
 * 		- Random function
 * 		- Screen scaling
 * 		- Line number component
 * 		- Dark and light mode
 * 
 * 		Dylan
 * 		- Editor
 * 		- IDE Interface
 * 		- Project Explorer
 * 		- Console
 * 		- Project and Class Objects
 * 		- Project and class creation and delete options
 * 		- Loading projects to project explorer
 * 		- Loading classes to editor
 * 		- Class saving
 * 		- Tracking edited classes
 * 		- Keyword customization options
 * 		- Utility customization options
 * 		- Highlighting line of error
 * 		- Exporting converted Cyclone code as a .java file
 * 		- Use of Java Compiler to compile Cyclone code that has been converted to Java
 * 		- Running converted code
 * 		- Set JDK file path
 * 		- Help/instructions screens
 * 
 * Date: Jan 17, 2020
 * 
 * Course Code: ICS 4U1
 * Teacher: Mr.Fernandes
 * 
 * Title: Cyclone IDE
 * 
 * Description:
 * 		Cyclone is an integrated development environment that allows users to program using our new 
 * 		programming language. This new language uses simpler keywords to substitute for Java’s 
 * 		keywords. Additionally, this new language follows the convention of using tab spaces to 
 * 		indicate that several lines are a part of a block of code.  The application includes a text 
 * 		area in which the user can type their code, an area in which files project folders and files 
 * 		are displayed, and a text area for the console. To run a program, the application converts 
 * 		the code written in the Cyclone programming language to Java code before executing it.  It 
 * 		then takes input and displays output to the console. The user also has the ability to convert 
 * 		the project made in this IDE to Java code and export it. Additionally, all the keywords and the 
 * 		IDE can all be customized by the user. 
 * 
 * Features:
 * 		- Editor
 * 		- Project Explorer
 * 		- Console
 * 		- Project and Class Objects
 * 		- Project and class creation and delete options
 * 		- Keyword customization options
 * 		- Utility customization options
 * 		- Menu bar
 * 		- Highlighting line of error
 * 		- Exporting converted Cyclone code as a .java file
 * 		- Use of Java Compiler to compile Cyclone code that has been converted to Java
 * 		- Help/instructions screens
 * 		- Converting code from java to Cyclone while catching errors
 * 		- Print Statement
 * 		- If Statement
 * 		- User Input
 * 		- Loop
 * 		- Inequality operators used of conditions
 * 		- "and" and "or" operators
 * 		- Variable Declaration
 * 		- Variable data types
 * 		- Perform basic operations on variables (+,-,*,-)
 * 		- parseInt and parseDouble functions
 * 		- toString function
 * 		- Random function
 * 
 * Major Skills:
 * 		- Object Oriented Programming
 * 		- Recursion
 * 		- Interface use
 * 		- File reading and writing
 * 		- Arrays/ArrayLists
 * 		- Java Compiler use
 * 		- Invoking methods
 * 		- Use of JComponents
 * 		- Use of file dialogue and file chooser
 * 		- Recursive error checking
 * 
 * Areas of concern
 * 		- JDK file path must be set by the user
 * 		- Infinite while loop can't be terminated, so a loop has been limited to running a maximum of 1000 times
 * 		- There must be spaces surrounding any of the following operators: >,<,>=,<=,==
 * 		- Calculations can't be executed in the print statement and conditions of loop and control structures
 */

public class Launcher {
	
	public static void main(String[] args) {
		
		new IDEInterface();
		
	}

}
