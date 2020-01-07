package display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
		
		editor = new Editor();
		add(editor);
		
		projectExplorer = new ProjectExplorer();
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
		
		if(e.getSource() == getNewProjectOption()) {
			createProject();
		} else if (e.getSource() == getNewClassOption()) {
			createClass();
		}
		
	}
	
	//This method allows the user to create a project
	private void createProject() {
		
		String projectName = JOptionPane.showInputDialog("Deleting your account will delete all of your projects\nEnter your passowrd to confirm:").trim();
		System.out.println(projectName);
		
		boolean validName = true;
		
		if(projectName == null) {
			validName = false;
			//Display error message
			//JOptionPane.showMessageDialog(null, "A project name was not input","INVALID", JOptionPane.WARNING_MESSAGE);
		}
		
		for(Project currentProject: projectExplorer.getProjects()) {
			
			if(projectName.equalsIgnoreCase(currentProject.getProjectName())) {
				validName = false;
				//Display error message
				JOptionPane.showMessageDialog(null, "The project name is already taken","INVALID", JOptionPane.WARNING_MESSAGE);
			}
			
			if(!validName)
				break;
		}
		
		//A filename can't contain any of the following characters
		//\/:*?"<>|
		if(projectName.contains("\\") || projectName.contains("/") || 
				projectName.contains(":") || projectName.contains("*") || 
				projectName.contains("?") || projectName.contains("\"") || 
				projectName.contains("<") || projectName.contains(">") || 
				projectName.contains("|") || projectName.isEmpty() || projectName.matches("[ ]+")) {
			JOptionPane.showMessageDialog(null, "A filename can't contain any of the following characters:\n\\/:*?\"<>|","INVALID", JOptionPane.WARNING_MESSAGE);
			validName = false;
		}
		
		//If the project name is not taken, create the folder
		if(validName) {
			
			File file = new File(String.format("projects/%s", projectName));
			boolean directoryCreated = file.mkdir();

			if(directoryCreated) {
				System.out.println("Project folder created");
			} else {
				System.out.println("Project folder was not created");
			}
			
		}
		
	}
	
	//This method is used to create a class
    public void createClass(){
        String[] options = {"option1", "option2", "option3"};
        String selectionObject = (String) JOptionPane.showInputDialog(null, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        System.out.println("test");
    }

}

