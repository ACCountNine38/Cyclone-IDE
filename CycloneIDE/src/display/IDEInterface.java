package display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import utils.FileInput;
import utils.Project;

public class IDEInterface extends State {
	
	// perspectives
	private Console console;
	private Editor editor;
	private ProjectExplorer projectExplorer;
	
	private JLabel background = new JLabel();
	
	public IDEInterface() {
		
		addPerspectives();
		
		addMenuBar();
		
		frameSetup(this, "Cyclone IDE", (int) Perspective.screenWidth, (int) Perspective.screenHeight);
		
	}
	
	private void addPerspectives(){
		
		console = new Console();
		add(console);
		
		editor = new Editor(this);
		add(editor);
		
		projectExplorer = new ProjectExplorer(this);
		add(projectExplorer);
		
	}

	// method that sets up a frame
	public static void frameSetup(JFrame frame, String name, int width, int height) {

		// set the name and size of the frame, and now allowing user to resize
		frame.setTitle(name);
		frame.setSize((int)State.screenWidth, (int)State.screenHeight);
		frame.setResizable(false);

		// disables auto layout, center program, exit frame when program closes
		frame.setLayout(null);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(225, 225, 225));

		// set frame to appear on screen
		frame.setVisible(true);

	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Create a new project or class
		if(e.getSource() == getNewProjectOption()) {
			createProject();
		} else if(e.getSource() == getNewClassOption()) {
			createClass();
		} else if(e.getSource() == getSaveCurrentTabOption()) {
			editor.saveCurrentTab();
		} else if (e.getSource() ==  getSaveAllTabsOption()) {
			editor.saveAllTabs();
		}
		
	}
	
	//This method allows the user to create a project
	private void createProject() {
		
		String projectName = JOptionPane.showInputDialog("Enter a project name:").trim();
		System.out.println(projectName);
		
		boolean validName = true;
		
		if(projectName.isEmpty()) {
			validName = false;
			//Display error message
			//JOptionPane.showMessageDialog(null, "A project name was not input","INVALID", JOptionPane.WARNING_MESSAGE);
		}
		
		for(Project currentProject: projectExplorer.getProjects()) {
			
			if(projectName.equalsIgnoreCase(currentProject.getProjectName())) {
				validName = false;
				//Display error message
				JOptionPane.showMessageDialog(null, "The project name is already taken","INVALID", JOptionPane.WARNING_MESSAGE);
				break;
			}
				
		}
		
		//A filename can't contain any of the following characters
		//\/:*?"<>|
		if(projectName.contains("\\") || projectName.contains("/") || 
				projectName.contains(":") || projectName.contains("*") || 
				projectName.contains("?") || projectName.contains("\"") || 
				projectName.contains("<") || projectName.contains(">") || 
				projectName.contains("|")) {
			JOptionPane.showMessageDialog(null, "A filename can't contain any of the following characters:\n\\/:*?\"<>|","INVALID", JOptionPane.WARNING_MESSAGE);
			validName = false;
		}
		
		//If the project name is not taken, create the folder
		if(validName) {
			
			File file = new File(String.format("projects/%s", projectName));
			boolean directoryCreated = file.mkdir();

			if(directoryCreated) {
				System.out.println("Project folder created");
				projectExplorer.addNewProject(projectName); //add the project to the project explorer
			} else {
				System.out.println("Project folder was not created");
			}
			
		}
		
	}
	
	//This method is used to create a class
    public void createClass(){
    	
    	//Select the project
        String selectedProject = (String) JOptionPane.showInputDialog(null, "Choose a project to create a class in:", "Menu", JOptionPane.PLAIN_MESSAGE, null, 
        		projectExplorer.getProjectDirectories().toArray(), projectExplorer.getProjectDirectories().get(0));
        System.out.println("Creating class within: " + selectedProject);
        
        //Return from the method if nothing was entered
        if(selectedProject == null)
        	return;
        
        //Enter a class name
		String className = JOptionPane.showInputDialog("Enter a class name:").trim();
		System.out.println(className);
		
		boolean validName = true;
		
		//Check if a class name was entered
		if(className.isEmpty()) {
			validName = false;
			//Display error message
			//JOptionPane.showMessageDialog(null, "A project name was not input","INVALID", JOptionPane.WARNING_MESSAGE);
		}
		
		//Check if the class name is taken
		Project project = null;
		
		for(Project currentProject: projectExplorer.getProjects()) {
			if(selectedProject.equals(currentProject.getProjectName())) {
				project = currentProject;
				break;
			}
			System.out.println(currentProject.getProjectName());
		}
		
		for(File currentClass: project.getFilepath().listFiles()) {
			
			if(className.equalsIgnoreCase(currentClass.getName())) {
				validName = false;
				//Display error message
				JOptionPane.showMessageDialog(null, "The class name is already taken","INVALID", JOptionPane.WARNING_MESSAGE);
				break;
			}
			
		}
		
		//A filename can't contain any of the following characters
		//\/:*?"<>|
		if(className.contains("\\") || className.contains("/") || 
				className.contains(":") || className.contains("*") || 
				className.contains("?") || className.contains("\"") || 
				className.contains("<") || className.contains(">") || 
				className.contains("|")) {
			JOptionPane.showMessageDialog(null, "A filename can't contain any of the following characters:\n\\/:*?\"<>|","INVALID", JOptionPane.WARNING_MESSAGE);
			validName = false;
		}
		
		//If the project name is not taken, create the folder
		if(validName) {
			
			File file = new File(String.format("projects/%s/%s", project.getProjectName(), className));

			try {
				file.createNewFile();
				System.out.println("Class file was created");
				//projectExplorer.addNewClass(className);
				projectExplorer.addNewClass(selectedProject, className);
			} catch (IOException e) {
				System.out.println("Class file was not created");
				e.printStackTrace();
			}
			
		}
        
    }
    
	//This method is used to create a class given the project name
    public void createClass(String projectName){
    	
    	int index = 0;
    	for(int i = 0; i < projectExplorer.getProjectDirectories().size(); i++) {
    		if(projectExplorer.getProjectDirectories().get(i).equals(projectName)) {
    			index = i;
    			break;
    		}
    	}
    	
    	//Select the project
        String selectedProject = (String) JOptionPane.showInputDialog(null, "Choose a project to create a class in:", "Menu", JOptionPane.PLAIN_MESSAGE, null, 
        		projectExplorer.getProjectDirectories().toArray(), projectExplorer.getProjectDirectories().get(index));
        System.out.println("Creating class within: " + selectedProject);
        
        //Return from the method if nothing was entered
        if(selectedProject == null)
        	return;
        
        //Enter a class name
		String className = JOptionPane.showInputDialog("Enter a class name:").trim();
		System.out.println(className);
		
		boolean validName = true;
		
		//Check if a class name was entered
		if(className.isEmpty()) {
			validName = false;
			//Display error message
			//JOptionPane.showMessageDialog(null, "A project name was not input","INVALID", JOptionPane.WARNING_MESSAGE);
		}
		
		//Check if the class name is taken
		Project project = null;
		
		for(Project currentProject: projectExplorer.getProjects()) {
			if(selectedProject.equals(currentProject.getProjectName())) {
				project = currentProject;
				break;
			}
			System.out.println(currentProject.getProjectName());
		}
		
		for(File currentClass: project.getFilepath().listFiles()) {
			
			if(className.equalsIgnoreCase(currentClass.getName())) {
				validName = false;
				//Display error message
				JOptionPane.showMessageDialog(null, "The class name is already taken","INVALID", JOptionPane.WARNING_MESSAGE);
				break;
			}
			
		}
		
		//A filename can't contain any of the following characters
		//\/:*?"<>|
		if(className.contains("\\") || className.contains("/") || 
				className.contains(":") || className.contains("*") || 
				className.contains("?") || className.contains("\"") || 
				className.contains("<") || className.contains(">") || 
				className.contains("|")) {
			JOptionPane.showMessageDialog(null, "A filename can't contain any of the following characters:\n\\/:*?\"<>|","INVALID", JOptionPane.WARNING_MESSAGE);
			validName = false;
		}
		
		//If the project name is not taken, create the folder
		if(validName) {
			
			File file = new File(String.format("projects/%s/%s", project.getProjectName(), className));

			try {
				file.createNewFile();
				System.out.println("Class file was created");
				//projectExplorer.addNewClass(className);
				projectExplorer.addNewClass(selectedProject, className);
			} catch (IOException e) {
				System.out.println("Class file was not created");
				e.printStackTrace();
			}
			
		}
        
    }
    
	public Console getConsole() {
		return console;
	}

	public void setConsole(Console console) {
		this.console = console;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public ProjectExplorer getProjectExplorer() {
		return projectExplorer;
	}

	public void setProjectExplorer(ProjectExplorer projectExplorer) {
		this.projectExplorer = projectExplorer;
	}
    
}

