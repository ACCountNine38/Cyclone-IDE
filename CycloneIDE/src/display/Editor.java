package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JTextArea;

import objects.Class;
import objects.Project;
import popup.KeywordCustomizationPopup;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class Editor extends Perspective {
	
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = 25;
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3*2 - 25);
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	IDEInterface ide; //IDEInterface is passed into the editor
	
	public Editor(IDEInterface ide) {
		
		this.ide = ide;
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, new Color(243, 243, 243));
		
		addJComponents();
		
		//Important methods
		//tabbedPane.getSelectedComponent();
		//tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
		
	}
	
	public void addJComponents() {
		
		tabbedPane.setBounds(0, 0, width, height);
		add(tabbedPane);
		
	}
	
	//This method creates a new tab for a class file
	public void addTab(Class classButton) {
		
		tabbedPane.addTab(classButton.getClassName(), classButton.getEditorTextAreaScroll());
		//tabbedPane.setSelectedIndex(tabbedPane.indexOfTabComponent(tabTextAreaScroll));
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
		
		//Set up the tab button
		//Remove previous action listener
		if(classButton.getTab().getCloseButton().getActionListeners().length > 0)
			classButton.getTab().getCloseButton().removeActionListener(classButton.getTab().getCloseButton().getActionListeners()[0]);
		
		classButton.getTab().getCloseButton().addActionListener(this);
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, classButton.getTab());
		
	}
	
	//This method saves the current tab of the editor
	public void saveCurrentTab(Class currentClass) {
		
		if(tabbedPane.getTabCount() == 0) {
			System.out.println("no tabs");
			return;
		}
		
		if(tabbedPane.getComponent(tabbedPane.getSelectedIndex()).equals(currentClass.getEditorTextAreaScroll())) {
			System.out.println("saving class: " + currentClass.getClassName());
			
			currentClass.setEdited(false);//Remove asterisk on the tab
			
			//Save the text to the file
			String classText = currentClass.getEditorTextArea().getText();
			
			File classFile = new File(String.format("projects/%s/%s", currentClass.getProjectName(), currentClass.getClassName()));
			try {
				
				PrintWriter pr = new PrintWriter(classFile);
				pr.print(classText);
				
				System.out.println("class saved: " + currentClass.getClassName());
				
				pr.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("class save failed: " + currentClass.getClassName());
				
			}
			
		}
		
	}
	
	//This method saves the current tab of the editor
	public void saveCurrentTab() {
		
		//Return if there are no open tabs
		if(tabbedPane.getTabCount() == 0) {
			return;
		}
		
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class currentClass: currentProject.getFileButtons()) {
				
				if(tabbedPane.getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
					System.out.println("saving class: " + currentClass.getClassName());
					
					currentClass.setEdited(false);//Remove asterisk on the tab
					
					//Save the text to the file
					String classText = currentClass.getEditorTextArea().getText();
					
					File classFile = new File(String.format("projects/%s/%s", currentClass.getProjectName(), currentClass.getClassName()));
					try {
						
						PrintWriter pr = new PrintWriter(classFile);
						pr.print(classText);
						
						System.out.println("class saved: " + currentClass.getClassName());
						
						pr.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			//System.out.println("Project: " + currentProject.getProjectName());
			
			for(Class currentClass: currentProject.getFileButtons()) {
				//System.out.println("Class: " + currentClass.getClassName());
				
				for(int i = 0; i < tabbedPane.getTabCount() + 1; i++) {
					
					if(tabbedPane.getComponent(i).equals(currentClass.getEditorTextAreaScroll())) {
						System.out.println("saving class: " + currentClass.getClassName());
						//System.out.println(" i = " + i + " num tabs =" + tabbedPane.getTabCount());
						
						currentClass.setEdited(false);//Remove asterisk on the tab
						
						//Save the text to the file
						String classText = currentClass.getEditorTextArea().getText();
						
						File classFile = new File(String.format("projects/%s/%s", currentClass.getProjectName(), currentClass.getClassName()));
						try {
							
							PrintWriter pr = new PrintWriter(classFile);
							pr.print(classText);
							
							System.out.println("class saved: " + currentClass.getClassName());
							
							pr.close();
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("class save failed: " + currentClass.getClassName());
						}
						
					}
					
				}
				
			}
			
		}
		
		System.out.println("All tabs saved");
		
	}
	
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
