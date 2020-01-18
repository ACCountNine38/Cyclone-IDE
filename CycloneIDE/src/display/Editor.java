package display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import objects.Class;
import objects.Project;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter.HighlightPainter;

//This class is used to create an editor JPanel to be displayed on the main frame of the Cyclone IDE
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
		
		Editor.ide = ide; //Initialize the IDEInterface
		
		//Set up the panel
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, State.utilityColor);
		
		//Add the tabbed pane to the panel
		addJComponents();
		
	}
	
	//This method adds the tabbed pane to the editor panel
	public void addJComponents() {
		
		//Set up the tabbed pane
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
		
		//Locate the all of the currently opened classes by looping through each project's classes
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
							
							//Write the text from the text area to the class file
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
					
					//If an opened tab has been found, check if it has been edited
					if(tabbedPane.getComponent(i).equals(currentClass.getEditorTextAreaScroll())) {
						
						//If there is an edited tab, return true
						if(currentClass.isEdited())
							return true;
						
					}
					
				}
				
			}
			
		}
		
		//Return false if no edited tab has been found
		return false;
		
	}
	
	//This method checks if the current tab has been edited
	public boolean isCurrentTabEdited() {
		
		//If there are no tabs opened, return false
		if(tabbedPane.getTabCount() == 0) {
			return false;
		}
		
		//Check all of the currently opened classes by looping through each project's classes
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				//If the current tab has been found, check if it has been edited
				if(tabbedPane.getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
					
					//If the current tab has been edited, return true
					if(currentClass.isEdited())
						return true;
					
				}
				
			}
			
		}
		
		//Return false if the current tab has not been edited
		return false;
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
				
				//If the currently displayed text area is found, highlight the specified line number
				if(tabbedPane.getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
					
					//Highlight the specified line number
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
	
	//This method detects if a button to close a tab has been pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Loop through all of the currently opened classes by looping through each project's classes
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class classButton: currentProject.getFileButtons()) {
				
				//If the tab button has been found, check if the tab has been edited
				if(e.getSource() == classButton.getTab().getCloseButton()) {
					
					//If the current tab is edited, warn the user before they close is
					if(isCurrentTabEdited()) {
						
						//Prompt the user to save the class before closing its tab
						int option = JOptionPane.showConfirmDialog(ide, "Would you like to save this class, " + classButton.getClassName() + ", before closing");
						//Yes = 0, No = 1, Cancel = 2, Closing the window = -1
						if(option == 0) { //If the user selects yes, save the tab and close it
							saveCurrentTab();
							removeDeletedClassTab(classButton);
						} else if(option == 1) { //If the user selects no, close the tab without saving
							removeDeletedClassTab(classButton);
						} 
						
					} else { //If the tab is not edited, remove the tab 
						removeDeletedClassTab(classButton);
					}
		            
				}
				
			}
			
		}
		
	}

}
