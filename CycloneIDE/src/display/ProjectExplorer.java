package display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import objects.Class;
import objects.ClassPopup;
import objects.Project;
import objects.ProjectPopup;

//This class is used to create a project explorer JPanel to be displayed on the main frame of the Cyclone IDE
public class ProjectExplorer extends Perspective {
	
	//Dimensions
	private static int orginX = 25;
	private static int orginY = 25;
	private static int width = (int) (Perspective.screenWidth/4 - 25);
	private static int height = (int) (Perspective.screenHeight - 65) - 25;
	
	private JPanel projectExplorerPanel = new JPanel();//Main panel that contains all project panels- Box Layout
	private ArrayList<String> projectDirectories = new ArrayList<String>();//Stores project directories
	private ArrayList<Project> projects = new ArrayList<Project>(); //Projects
	//Each project has its own main panel - Box Layout
	//This panel will contain:
	//	-Button that when pressed reveals project files
	//	-Project file buttons that appear and disappear when project file is clicked
	
	//Popups open when a class or project is right clicked
	private static ClassPopup classMenu = new ClassPopup();
	private JMenuItem deleteClass = new JMenuItem("Delete");
	private JMenuItem newClassC = new JMenuItem("New Class");
	private static ProjectPopup projectMenu = new ProjectPopup();
	private JMenuItem deleteProject = new JMenuItem("Delete");
	private JMenuItem newClassP = new JMenuItem("New Class");
	
	private IDEInterface ide; //IDEInterface is passed into the project explorer
	
	//Constructor method
	public ProjectExplorer(IDEInterface ide) {
		
		this.ide = ide; //Initialize the IDEInterface
		
		//Set up the panel
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, State.utilityColor);
		
		//Add the JComponents and projects to the project explorer
		addJComponents();
		loadFiles();
		createProjects();
	}
	
	//This method adds required JComponents to the panel
	public void addJComponents() {
		
		//Set up the options for right click pop up menu
		newClassC.addActionListener(this);
		classMenu.add(newClassC);
		deleteClass.addActionListener(this);
		classMenu.add(deleteClass);
		newClassP.addActionListener(this);
		projectMenu.add(newClassP);
		deleteProject.addActionListener(this);
		projectMenu.add(deleteProject);
		
		//Set up the main panel
		projectExplorerPanel.setLayout(new BoxLayout(projectExplorerPanel, BoxLayout.Y_AXIS));
		projectExplorerPanel.setBackground(State.utilityColor);
		projectExplorerPanel.setOpaque(true);
		JScrollPane consoleTextAreaScroll = new JScrollPane(projectExplorerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		consoleTextAreaScroll.setBounds(0 , 0, width, height);
		add(consoleTextAreaScroll); 
		
	}
	
	//This method loads the project files
	private void loadFiles() {
		
		//Source: https://stackoverflow.com/questions/5125242/java-list-only-subdirectories-from-a-directory-not-files/5125258
		File file = new File("projects");
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		
		//Add the directories to the project directories array list
		for(String project: directories) {
			projectDirectories.add(project);
		}
		
	}
	
	//This method adds the buttons for each project to the project manager
	private void createProjects() {
		
		//Loop through the project directory array and create a project for each directory
		for(int i = 0; i < projectDirectories.size(); i++) {
			
			//Create a project variable for each directory
			Project project = new Project(projectDirectories.get(i));
			projects.add(project);
			projectExplorerPanel.add(project.getProjectPanel());
			project.getProjectButton().addActionListener(this); //Add action listener to project button
			
			//Add action listeners to all class buttons within the project
			for(JButton classButton: project.getFileButtons()) {
				classButton.addActionListener(this);
			}
			
		}
		
	}
	
	//This method adds a single new project
	public void addNewProject(String projectName) {
		
		//Add the new project to the project directories arraylist and
		//create and add a project variable to the projects array
		projectDirectories.add(projectName);
		Project project = new Project(projectName);
		projects.add(project);
		projectExplorerPanel.add(project.getProjectPanel());  //Add the project panel to the project explorer
		project.getProjectButton().addActionListener(this); //Add action listener to project button
		updateProjects();
		
	}
	
	//This method adds a new class to a selected project
	public void addNewClass(String selectedProject, String className) {
		
		//Find the project with the specified name
		for(Project currentProject: projects) {
			
			//If the selected project is found, add a new class with the specified name to it
			if(currentProject.getProjectName().equals(selectedProject)) {
				currentProject.addNewClass(className);
				updateProjects();
				break;
			}
			
		}
		
	}
	
	//This method updates the project list - to be called when a project or class is created or deleted 
	public void updateProjects() { 
		
		//Remove all project components from the main panel
		projectExplorerPanel.removeAll();
		
		//Sort the project and project directories array lists
		Collections.sort(projectDirectories);
		Collections.sort(projects, alphaSorter);
		
		//Add the each projects components to the main panel
		for(Project currentProject: projects) {
			
			//Reformat the class file buttons
			currentProject.reformatFileButtons();
			projectExplorerPanel.add(currentProject.getProjectPanel());
			
			//Add action listeners to all class buttons within the project that 
			for(Class classButton: currentProject.getFileButtons()) {
				if(classButton.getActionListeners().length == 0 || !classButton.getActionListeners()[0].equals(this))
					classButton.addActionListener(this);
			}
			
		}
		
		//Must be called when removing components
		projectExplorerPanel.revalidate();
		projectExplorerPanel.repaint();
		
	}
	
	//Comparator for sorting projects alphabetically
	public static Comparator<Project> alphaSorter = new Comparator<Project>(){
		public int compare(Project proj1, Project proj2) {
			return proj1.getProjectName().compareTo(proj2.getProjectName());
		}
	};
	
	//Getter and setter
	public JPanel getProjectExplorerPanel() {
		return projectExplorerPanel;
	}

	public void setProjectExplorerPanel(JPanel projectExplorerPanel) {
		this.projectExplorerPanel = projectExplorerPanel;
	}

	public ArrayList<String> getProjectDirectories() {
		return projectDirectories;
	}

	public void setProjectDirectories(ArrayList<String> projectDirectories) {
		this.projectDirectories = projectDirectories;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	
	//This method detects when a project button or a popup button is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == deleteProject) { //Project right click popup button
			
			//As the user to confirm if they want to delete the selected project
			int option = JOptionPane.showConfirmDialog(ide, "Are you sure you want to delete this project: " + projectMenu.getProject().getProjectName());
			
			//Yes = 0, No = 1, Cancel = 2, Closing the window = -1
			if(option == 0) {
				
				//remove the project components from the editor if they are in any open tabs
				for(Class classButton: projectMenu.getProject().getFileButtons()) {
					ide.getEditor().removeDeletedClassTab(classButton);
				}
				
				//delete the project
				deleteProject(projectMenu.getProject());
				
			}
			
		} else if(e.getSource() == newClassP) { //Project right click popup button
			
			//Create a new class
			ide.createClass(projectMenu.getProject().getProjectName());
			
		} else if(e.getSource() == deleteClass) { //Class right click popup button
			
			int option = JOptionPane.showConfirmDialog(ide, "Are you sure you want to delete this class: " + classMenu.getSelectedClass().getClassName());
			
			if(option == 0) {
				
				//remove the class from the editor if its open in a tab
				ide.getEditor().removeDeletedClassTab(classMenu.getSelectedClass());
				
				//delete the class
				deleteClass(classMenu.getSelectedClass());
			}
			
		} else if(e.getSource() == newClassC) { //Class right click popup button
			
			//Create a new class
			ide.createClass(classMenu.getSelectedClass().getProjectName());
			
		} 
		
		//Open or collapse a project
		for(Project currentProject: projects) {
			
			//Collapse project if the project button is pressed
			if(e.getSource() == currentProject.getProjectButton()) {
				currentProject.collapse();
				projectExplorerPanel.revalidate();
				projectExplorerPanel.repaint();
				break;
			} 
			
			//Check if a class button within a project is pressed
			for(Class classButton: currentProject.getFileButtons()) {
				
				//Open the class file if its button is pressed
				if(e.getSource() == classButton) {
					
					//Display the file's contents in the text editor
					ide.getEditor().addTab(classButton);
					classButton.setupText(); //Call here to update the line numbers (also erases unsaved data from a text area)
					classButton.getLineNumberComponent().adjustWidth();
					//set the current file
					State.currentFile = new File(String.format("projects/%s/%s", 
							classButton.getProjectName(), classButton.getClassName()));
					
				} 
				
			}
			
		}
		
	}
	
	//This method shows a popup when a project is right clicked
	public static void projectPopup(Project project) {
		
		//Show the popup when a project is right clicked
		projectMenu.setProject(project);
	    projectMenu.show(project.getProjectButton(), 
	    		(int) project.getProjectButton().getMousePosition().getX(), 
	    		(int) project.getProjectButton().getMousePosition().getY());
		
	}
	
	//This method shows a popup when a class is right clicked
	public static void classPopup(Class selectedClass) {
		
		//Show the popup when a class is right clicked
		classMenu.setSelectedClass(selectedClass);
	    classMenu.show(selectedClass, 
	    		(int) selectedClass.getMousePosition().getX(), 
	    		(int) selectedClass.getMousePosition().getY());
		
	}
	
	//This method deletes a selected project
	private void deleteProject(Project project) {
		
		//Remove the project from the array lists
		projectDirectories.remove(project.getProjectName());
		projects.remove(project);
		projectExplorerPanel.remove(project.getProjectPanel());
		projectExplorerPanel.revalidate();
		projectExplorerPanel.repaint();
		
		//Delete the project
		String projectFolder = String.format("projects/%s", project.getProjectName());
		File projectFolderFilepath = new File(projectFolder);

		String[] fileList = projectFolderFilepath.list();
		
		//Delete the class files within the project
		for(String projectFile: fileList){

			File currentFile = new File(projectFolderFilepath.getPath(), projectFile);
			currentFile.delete();

		}
		
		//Delete the project folder
		projectFolderFilepath.delete();
		
	}
	
	//This method deletes a selected class
	private void deleteClass(Class selectedClass) {
		
		//Project variable to hold the project of the selected class
		Project proj = new Project();
		
		//Find the project that the class belongs to
		for(Project currentProject: projects) {
			
			//Remove the class from the project
			if(currentProject.getFileButtons().contains(selectedClass)) {
				proj = currentProject;
				proj.getFilePanel().remove(selectedClass);
				proj.getFileButtons().remove(selectedClass);
				proj.getFilePanel().revalidate();
				proj.getFilePanel().repaint();
				break;
			}
			
		}
		
		//Delete the class file
		File classFilepath = new File(String.format("projects/%s/%s", selectedClass.getProjectName(), selectedClass.getClassName()));
		classFilepath.delete();
		
		//Reformat the file buttons
		proj.reformatFileButtons();
		
	}

}
