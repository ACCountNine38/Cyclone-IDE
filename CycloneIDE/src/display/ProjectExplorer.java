package display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
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
			
		}
		
	}
	
	//This method removes all components from the project explorer
	private void removeAllComponents() {
		
		projectExplorerPanel.removeAll();
		projectDirectories.clear();
		projects.clear();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
