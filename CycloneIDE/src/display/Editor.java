package display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import objects.Class;
import objects.Project;
import popup.KeywordOption;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class Editor extends Perspective {
	
	//Panel dimensions and position
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = 25;
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3*2 - 25);
	
	private static JTabbedPane tabbedPane = new JTabbedPane(); //Tabbed pane
	
	private static IDEInterface ide; //IDEInterface is passed into the editor
	
	//Constructor method
	public Editor(IDEInterface ide) {
		
		Editor.ide = ide;
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, State.utilityColor);
		
		addJComponents();
		
	}
	
	//This method adds the tabbed pane to the editor panel
	public void addJComponents() {
		
		tabbedPane.setBounds(0, 0, width, height);
		add(tabbedPane);
		
	}
	
	//This method creates a new tab for a class file
	public void addTab(Class classButton) {
		
		//Add the text area to the tabbed pane and select the new tab
		tabbedPane.addTab(classButton.getClassName(), classButton.getEditorTextAreaScroll());
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
		
		//Set up the tab button
		//Remove previous action listener
		if(classButton.getTab().getCloseButton().getActionListeners().length > 0)
			classButton.getTab().getCloseButton().removeActionListener(classButton.getTab().getCloseButton().getActionListeners()[0]);
		
		//Add the action listener and set up the tab component that allows the user to close the tab
		classButton.getTab().getCloseButton().addActionListener(this);
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, classButton.getTab());
		
	}
	
	//This method saves the current tab of the editor
	public void saveCurrentTab() {
		
		//Return if there are no open tabs
		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		
		//Locate the currently selected class
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				if(tabbedPane.getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
					
					currentClass.setEdited(false); //Remove asterisk on the tab
					
					//Save the text to the file
					String classText = currentClass.getEditorTextArea().getText();
					File classFile = new File(String.format("projects/%s/%s", currentClass.getProjectName(), currentClass.getClassName()));
					try {
						
						PrintWriter pr = new PrintWriter(classFile);
						pr.print(classText);
						pr.close();
						
					} catch (FileNotFoundException e) {
						System.out.println("class save failed: " + currentClass.getClassName());
					}
					
					return;
					
				}
				
			}
			
		}
		
	}
	
	//This method saved the classes of all opened tabs
	public void saveAllTabs() { //NOTE* needs fixing
		
		//Return if there are no open tabs
		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		
		//Locate the all of the currently opened classes
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				for(int i = 0; i < tabbedPane.getTabCount() + 1; i++) {
					
					if(tabbedPane.getComponent(i).equals(currentClass.getEditorTextAreaScroll())) {
						System.out.println("saving class: " + currentClass.getClassName());
						
						currentClass.setEdited(false);//Remove asterisk on the tab
						
						//Save the text to the file
						String classText = currentClass.getEditorTextArea().getText();
						File classFile = new File(String.format("projects/%s/%s", currentClass.getProjectName(), currentClass.getClassName()));
						try {
							
							PrintWriter pr = new PrintWriter(classFile);
							pr.print(classText);
							pr.close();
							
						} catch (FileNotFoundException e) {
							System.out.println("class save failed: " + currentClass.getClassName());
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	//The method removes the text areas of the deleted class that is passed in
	public void removeDeletedClassTab(Class currentClass) {
        int i = tabbedPane.indexOfTabComponent(currentClass.getTab());
        if (i != -1) {
        	tabbedPane.remove(i);
        }
	}
	
	//This method returns true if an edited tab(s) is opened but returns false otherwise
	public boolean editedTabsOpen() {
		
		//If there are no tabs opened, return false
		if(tabbedPane.getTabCount() == 0) {
			return false;
		}
		
		//Locate the currently opened classes and test if each opened class has been edited
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				for(int i = 0; i < tabbedPane.getTabCount() + 1; i++) {
					
					if(tabbedPane.getComponent(i).equals(currentClass.getEditorTextAreaScroll())) {
						
						if(currentClass.isEdited())
							return true;
						
					}
					
				}
				
			}
			
		}
		
		return false;
		
	}
	
	public boolean isCurrentTabEdited() {
		
		//If there are no tabs opened, return false
		if(tabbedPane.getTabCount() == 0) {
			return false;
		}
		
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				if(tabbedPane.getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
					
					if(currentClass.isEdited())
						return true;
					
				}
				
			}
			
		}
		
		return false;
	}
	
	public void generateMainMethod() {
		
		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				if(tabbedPane.getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
					
					currentClass.getEditorTextArea().setText(currentClass.getEditorTextArea().getText() + String.format("\n%s:", getKeyword("main")));
					
				}
				
			}
			
		}
		
	}
	
	//This method generates a for loop given the user's specifications
	public void generateForLoop() {
		
		//Return if no tabs are opened in the editor
		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				if(tabbedPane.getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
					
					//Give the user the option for the initial variable value
					Integer i = null;
					while(i == null) {
						try {
							i = Integer.parseInt(JOptionPane.showInputDialog("Starting value:"));
						} catch (NumberFormatException e) {	}
					}
					
					//Enter the highest value that the variable can reach
					Integer highest = null;
					while(highest == null) {
						try {
							highest = Integer.parseInt(JOptionPane.showInputDialog("Greatest counter value:")) + 1;
						} catch (NumberFormatException e) {	}
					}
					
					//Output the for loop to the editor
					currentClass.getEditorTextArea().setText(currentClass.getEditorTextArea().getText() + String.format("\n%s: i = %d : i < %d : i++", getKeyword("for"), i, highest));
					
				}
				
			}
			
		}
		
	}
	
	//This method returns the keyword for a for a function
	private String getKeyword(String function) {
		
		try {
			
			//Read from the user commands file
			Scanner input = new Scanner(new File("commands/userCommands"));
			
			//Read the commands and current keywords and display them
			while(input.hasNextLine()) {
				
				if(input.next().equals(function)) {
					return input.next();
				}
				
			}
			
			input.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	//This method highlights a specific line of the currently displayed text area
	public static void highlightLine(int lineNumber) {
		
		lineNumber -= 1; //The line number index starts at 0
		
		//Return if no tabs are opened in the editor
		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		
		//Find the currently displayed text area
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				if(tabbedPane.getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
					
					//SOURCE: https://stackoverflow.com/questions/10191723/highlight-one-specific-row-line-in-jtextarea
			        try {
			        	
			            int startIndex = currentClass.getEditorTextArea().getLineStartOffset(lineNumber);
			            int endIndex = currentClass.getEditorTextArea().getLineEndOffset(lineNumber);
			            HighlightPainter painter;
			            System.out.println("RED Colour");
			            painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
			            currentClass.setHighlightTag(currentClass.getEditorTextArea().getHighlighter().addHighlight(startIndex, endIndex, painter));

			        } catch(BadLocationException e) {
			            e.printStackTrace();
			        }
					
				}
				
			}
			
		}
		

	}
	
	//Getter and setter
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class classButton: currentProject.getFileButtons()) {
				
				if(e.getSource() == classButton.getTab().getCloseButton()) {
					
					//If the
					if(isCurrentTabEdited()) {
						
						int option = JOptionPane.showConfirmDialog(ide, "Would you like to save this class, " + classButton.getClassName() + ", before closing");
						System.out.println("Option = " + option);
						//Yes = 0, No = 1, Cancel = 2, Closing the window = -1
						if(option == 0) {
							saveCurrentTab();
							removeDeletedClassTab(classButton);
						} else if(option == 1) {
							removeDeletedClassTab(classButton);
						} 
						
					} else {
						removeDeletedClassTab(classButton);
					}
		            
				}
				
			}
			
		}
		
	}

}
