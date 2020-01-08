package utils;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import display.Perspective;

public class Project {
	
	private static int width = (int) (Perspective.screenWidth/4 - 25);
	private static int buttonHeight = 50;
	
	//Each project has its own main panel - Box Layout
	//This panel will contain:
	//	-Button that when pressed reveals project files
	//	-Project file buttons that appear and disappear when project file is clicked
	private JPanel projectPanel = new JPanel();
	private JButton projectButton = new JButton();
	private JPanel filePanel = new JPanel(); //Within the main panel and stores buttons for each project file
	private ArrayList<JButton> fileButtons = new ArrayList<JButton>(); //Contains buttons for each file within the project
	
	private String projectName;
	private File filepath; 
	private boolean open;
	
	public Project(String projectName) {
		
		this.projectName = projectName;
		this.filepath = new File(String.format("projects/%s", projectName));
		open = false;
		
		setupJComponents();
		setupFileButtons();
		
	}
	
	//This method sets up the panels and project button
	private void setupJComponents() {
		
		//Setup the main panel
		projectPanel.setLayout(new BoxLayout(projectPanel, BoxLayout.Y_AXIS));
		projectPanel.setBounds(0, 0, width, (filepath.listFiles().length + 1) * buttonHeight);
		projectPanel.setMaximumSize(projectPanel.getSize());
		projectPanel.setMinimumSize(projectPanel.getSize());
		projectPanel.setPreferredSize(projectPanel.getSize());
		//projectPanel.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
		
		//Setup the folder button
		projectButton.setSize(width, buttonHeight);
		projectButton.setMaximumSize(projectButton.getSize());
		projectButton.setMinimumSize(projectButton.getSize());
		projectButton.setPreferredSize(projectButton.getSize());
		projectButton.setText(projectName);
		projectPanel.add(projectButton);
		
		//Setup the file panel
		filePanel.setLayout(null);
		filePanel.setBounds(0, 50, width, filepath.listFiles().length * buttonHeight);
		projectPanel.add(filePanel);
		
	}
	
	//This method sets up the buttons used to access individual files within the project
	public void setupFileButtons() {
		
		//Clear the panel and button array list
		filePanel.removeAll();
		fileButtons.clear();
		
		//Set the dimensions of the panels
		projectPanel.setBounds(0, 0, width, (filepath.listFiles().length + 1) * buttonHeight);
		projectPanel.setMaximumSize(projectPanel.getSize());
		projectPanel.setMinimumSize(projectPanel.getSize());
		projectPanel.setPreferredSize(projectPanel.getSize());
		
		filePanel.setBounds(0, 50, width, filepath.listFiles().length * buttonHeight);
		
		//Loop through the project directory array and create a project for each directory
		for(int i = 0; i < filepath.listFiles().length; i++) {
			
			System.out.println("file: " + filepath.listFiles()[i].getName());
			JButton fileButton = new JButton(filepath.listFiles()[i].getName());
			fileButton.setBounds(width / 10, i * buttonHeight, width * 9 / 10, buttonHeight);
			fileButton.setMaximumSize(fileButton.getSize());
			fileButton.setMinimumSize(fileButton.getSize());
			fileButton.setPreferredSize(fileButton.getSize());
			filePanel.add(fileButton);
			fileButtons.add(fileButton);
			
		}
		
		//Revalidate and repaint
		filePanel.revalidate();
		filePanel.repaint();
		
	}
	
	public JPanel getProjectPanel() {
		return projectPanel;
	}

	public void setProjectPanel(JPanel projectPanel) {
		this.projectPanel = projectPanel;
	}

	public JButton getProjectButton() {
		return projectButton;
	}

	public void setProjectButton(JButton projectButton) {
		this.projectButton = projectButton;
	}

	public JPanel getFilePanel() {
		return filePanel;
	}

	public void setFilePanel(JPanel filePanel) {
		this.filePanel = filePanel;
	}

	public ArrayList<JButton> getFileButtons() {
		return fileButtons;
	}

	public void setFileButtons(ArrayList<JButton> fileButtons) {
		this.fileButtons = fileButtons;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public File getFilepath() {
		return filepath;
	}

	public void setFilepath(File filepath) {
		this.filepath = filepath;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}
