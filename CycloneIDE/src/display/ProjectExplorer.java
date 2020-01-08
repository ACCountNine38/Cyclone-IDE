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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utils.Project;

public class ProjectExplorer extends Perspective{
	
	private static int orginX = 25;
	private static int orginY = 25;
	private static int width = (int) (Perspective.screenWidth/4 - 25);
	private static int height = (int) (Perspective.screenHeight - 65) - 25;
	
	private JPanel projectExplorerPanel = new JPanel();//Main panel that contains all project panels- Box Layout
	private ArrayList<String> projectDirectories = new ArrayList<String>();
	private ArrayList<Project> projects = new ArrayList<Project>();
	//Each project has its own main panel - Box Layout
	//This panel will contain:
	//	-Button that when pressed reveals project files
	//	-Project file buttons that appear and disappear when project file is clicked
	
	
	public ProjectExplorer() {
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, new Color(243, 243, 243));
		
		addJComponents();
		loadFiles();
		createProjects();
	}
	
	public void addJComponents() {
		
		projectExplorerPanel.setLayout(new BoxLayout(projectExplorerPanel, BoxLayout.Y_AXIS));
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
		
		//Print the names of each project
		System.out.println("Projects: ");
		for(String project: projectDirectories) {
			System.out.println(project);
		}
		
	}
	
	//This method adds the buttons for each project to the project manager
	private void createProjects() {
		
		//Loop through the project directory array and create a project for each directory
		for(int i = 0; i < projectDirectories.size(); i++) {
			
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
	
	//This method removes all components from the project explorer
	private void removeAllComponents() {
		
		projectExplorerPanel.removeAll();
		projectDirectories.clear();
		projects.clear();
		
	}
	
	//This method adds a single new project
	public void addNewProject(String projectName) {
		
		projectDirectories.add(projectName);
		Project project = new Project(projectName);
		projects.add(project);
		projectExplorerPanel.add(project.getProjectPanel()); //not required
		project.getProjectButton().addActionListener(this); //Add action listener to project button
		updateProjects();
		
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
			
			//Reload the class file buttons
			currentProject.setupFileButtons();
			projectExplorerPanel.add(currentProject.getProjectPanel());
			
			//Add action listeners to all class buttons within the project
			for(JButton classButton: currentProject.getFileButtons()) {
				classButton.addActionListener(this);
			}
			
		}
		
		//Must be called when removing components
		projectExplorerPanel.revalidate();
		projectExplorerPanel.repaint();
		
	}
	
	public static Comparator<Project> alphaSorter = new Comparator<Project>(){
		public int compare(Project proj1, Project proj2) {
			return proj1.getProjectName().compareTo(proj2.getProjectName());
		}
	};
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Open or collapse a project
		for(Project currentProject: projects) {
			
			//Collapse project if the project button is pressed
			if(e.getSource() == currentProject.getProjectButton()) {
				System.out.println("project button pressed");
				currentProject.collapse();
				projectExplorerPanel.revalidate();
				projectExplorerPanel.repaint();
				break;
			} 
			
			//Check if a class button within a project is pressed
			for(JButton classButton: currentProject.getFileButtons()) {
				
				//Open the class file if its button is pressed
				if(e.getSource() == currentProject.getProjectButton()) {
					
					//Display the file's contents in the text editor
					
				} 
				
			}
			
		}
		
	}

}
