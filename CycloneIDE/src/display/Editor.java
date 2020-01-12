package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.EventHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JTextArea;

import objects.Class;
import objects.Project;

import javax.swing.JTabbedPane;

public class Editor extends Perspective {
	
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = 25;
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3*2 - 25);
	
	public static JTextArea editorTextArea = new JTextArea();
	
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
		
		editorTextArea.setFont(new Font("serif", Font.PLAIN, 22));
		editorTextArea.setBounds(0, 0, width, height);
		editorTextArea.setLineWrap(true);
		editorTextArea.setWrapStyleWord(true);
		editorTextArea.setTabSize(2);
		
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
					
					//saveCurrentTab(classButton); //test saving the tab before its closed
					saveAllTabs();
					
		            int i = tabbedPane.indexOfTabComponent(classButton.getTab());
		            if (i != -1) {
		            	tabbedPane.remove(i);
		            }
		            
				}
				
			}
			
		}
		
	}

}
