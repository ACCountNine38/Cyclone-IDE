package display;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import objects.Class;
import objects.Project;
import popup.GettingStartedPopup;
import popup.KeywordCustomizationPopup;
import popup.UtilityCustomizationPopup;
import utils.FileExecutionTool;

public class IDEInterface extends State {
	
	// perspectives
	private Console console;
	private Editor editor;
	private ProjectExplorer projectExplorer;
	
	private static JPanel GUIPanel = new JPanel(null);
	
	public IDEInterface() {
		
		readJDKFilepath();
		loadUtilitySettings();
		
		addPerspectives();
		
		addMenuBar();
		
		frameSetup(this, "Cyclone IDE", (int) Perspective.screenWidth, (int) Perspective.screenHeight);
		
		//Warn user before closing while edited tabs are open
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				if(editor.editedTabsOpen()) {
					int option = JOptionPane.showConfirmDialog(IDEInterface.this, "Would you like to save edited tabs before closing");
					//System.out.println("Option = " + option);
					//Yes = 0, No = 1, Cancel = 2, Closing the window = -1
					if(option == 0) {
						editor.saveAllTabs();
						System.exit(0);
					} else if(option == 1) {
						System.exit(0);
					} 
				} else {
					System.exit(0);
				}
				
			}
			
		});
		
		//TEST
		//new KeywordCustomizationPopup();
		
	}
	
	private void addPerspectives(){
		
		console = new Console();
		GUIPanel.add(console);
		
		editor = new Editor(this);
		GUIPanel.add(editor);
		
		projectExplorer = new ProjectExplorer(this);
		GUIPanel.add(projectExplorer);
		
		GUIPanel.setBounds(0, 0, (int)SCREEN_WIDTH, (int)SCREEN_HEIGHT);
		GUIPanel.setBackground(State.utilityColor);
		add(GUIPanel);
		
	}

	// method that sets up a frame
	public static void frameSetup(JFrame frame, String name, int width, int height) {

		// set the name and size of the frame, and now allowing user to resize
		frame.setTitle(name);
		frame.setSize((int)State.SCREEN_WIDTH, (int)State.SCREEN_HEIGHT);
		frame.setResizable(false);

		// disables auto layout, center program, exit frame when program closes
		frame.setLayout(null);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		} else if (e.getSource() ==  getExportJavaFileOption()) {
			exportProject();
		} else if (e.getSource() ==  getSetJDKFilepathOption()) {
			setJDKFilepath();
		} else if(e.getSource() == getKeywordCustomizationOption()) {
			this.setEnabled(false);
			new KeywordCustomizationPopup(this);
		} else if(e.getSource() == getUtilityCustomizationOption()) {
			this.setEnabled(false);
			new UtilityCustomizationPopup(this);
		} else if (e.getSource() ==  getGenerateMainOption()) {
			editor.generateMainMethod();
		} else if (e.getSource() ==  getGenerateForOption()) {
			editor.generateForLoop();
		} else if (e.getSource() ==  getGettingStartedOption()) {
			this.setEnabled(false);
			new GettingStartedPopup(this);
		} 
		
	}
	
	//This method allows the user to create a project
	private void createProject() {
		
		String projectName = JOptionPane.showInputDialog("Enter a project name:").trim();
		//System.out.println(projectName);
		
		boolean validName = true;
		
		if(projectName.isEmpty()) {
			validName = false;
			//Display error message
			JOptionPane.showMessageDialog(null, "A project name was not input","INVALID", JOptionPane.WARNING_MESSAGE);
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
				//System.out.println("Project folder created");
				projectExplorer.addNewProject(projectName); //add the project to the project explorer
			} else {
				//System.out.println("Project folder was not created");
			}
			
		}
		
	}
	
	//This method is used to create a class
    public void createClass(){
    	
    	//Select the project
        String selectedProject = (String) JOptionPane.showInputDialog(null, "Choose a project to create a class in:", "Menu", JOptionPane.PLAIN_MESSAGE, null, 
        		projectExplorer.getProjectDirectories().toArray(), projectExplorer.getProjectDirectories().get(0));
        //System.out.println("Creating class within: " + selectedProject);
        
        //Return from the method if nothing was entered
        if(selectedProject == null)
        	return;
        
        //Enter a class name
		String className = JOptionPane.showInputDialog("Enter a class name:").trim();
		//System.out.println(className);
		
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
			//System.out.println(currentProject.getProjectName());
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
				className.contains("|") || className.contains(" ")) {
			JOptionPane.showMessageDialog(null, "A class name can't contain any of the following characters:\n\\/:*?\"<>| or spaces","INVALID", JOptionPane.WARNING_MESSAGE);
			validName = false;
		}
		
		//If the project name is not taken, create the folder
		if(validName) {
			
			File file = new File(String.format("projects/%s/%s", project.getProjectName(), className));

			try {
				file.createNewFile();
				//System.out.println("Class file was created");
				//projectExplorer.addNewClass(className);
				projectExplorer.addNewClass(selectedProject, className);
			} catch (IOException e) {
				//System.out.println("Class file was not created");
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
       // System.out.println("Creating class within: " + selectedProject);
        
        //Return from the method if nothing was entered
        if(selectedProject == null)
        	return;
        
        //Enter a class name
		String className = JOptionPane.showInputDialog("Enter a class name:").trim();
		//System.out.println(className);
		
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
			//System.out.println(currentProject.getProjectName());
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
				//System.out.println("Class file was created");
				//projectExplorer.addNewClass(className);
				projectExplorer.addNewClass(selectedProject, className);
			} catch (IOException e) {
				//System.out.println("Class file was not created");
				e.printStackTrace();
			}
			
		}
        
    }
    
    public void resetColor() {
    	
    	GUIPanel.setBackground(State.utilityColor);
    	
    	Console.consoleTextArea.setBackground(State.utilityColor);
    	Console.consoleTextArea.setForeground(State.textColor);
    	console.setBackground(State.utilityColor);
    	
    	projectExplorer.setBackground(State.utilityColor);
    	projectExplorer.getProjectExplorerPanel().setBackground(State.utilityColor);
    	
    	editor.getTabbedPane().setBackground(State.utilityColor);
    	editor.setBackground(State.utilityColor);
    	editor.repaint();
    	
    	for(Project currentProject: projectExplorer.getProjects()) {
    		
    		currentProject.getProjectButton().setBackground(State.utilityColor);
    		currentProject.getProjectButton().setForeground(State.textColor);
    		currentProject.getProjectButton().repaint();
    		
    		currentProject.getFilePanel().setBackground(State.utilityColor);
    		currentProject.getFilePanel().setForeground(State.textColor);
    		currentProject.getFilePanel().repaint();
    		
    		currentProject.getProjectPanel().setBackground(State.utilityColor);
    		currentProject.getProjectPanel().setForeground(State.textColor);
    		currentProject.getProjectPanel().repaint();
    		
    		for(Class currentClass: currentProject.getFileButtons()) {
    			currentClass.setBackground(State.utilityColor);
    			currentClass.setForeground(State.textColor);
    			currentClass.repaint();
    			currentClass.getEditorTextArea().setBackground(State.utilityColor);
    			currentClass.getEditorTextArea().setForeground(State.textColor);
    			currentClass.getEditorTextArea().repaint();
    		}
    		
    	}
    	
    	GUIPanel.repaint();
    	console.repaint();
    	projectExplorer.repaint();
    	editor.getTabbedPane().repaint();
    	Console.consoleTextArea.repaint();
    	projectExplorer.getProjectExplorerPanel().repaint();
    	
    }
    
    //This method loads the font settings and the dark and light mode settings
    public void loadUtilitySettings() {
    	
		try {
			
			Scanner input = new Scanner(new File("settings/fonts"));
			
			Class.editorFont = new Font(input.next(), Font.PLAIN, input.nextInt());
			Class.editorTabSize = input.nextInt();
			Console.consoleFont = new Font(input.next(), Font.PLAIN, input.nextInt());
			Console.consoleTabSize = input.nextInt();
			String theme = input.next();
			
			if(theme.equals("light")) {
				State.darkTheme = false;
				State.utilityColor = new Color(250, 250, 250);
				State.textColor = Color.black;
			} else if(theme.equals("dark")) {
				State.darkTheme = true;
				State.utilityColor = new Color(30, 30, 30);
				State.textColor = Color.white;
			}
			
			input.close();
			
		} catch(FileNotFoundException e) {
			
		}
    	
    }
    
	//This allows the user to export the project as a .java file
	private void exportProject() {
		
		//Return if there are no open tabs
		if(editor.getTabbedPane().getTabCount() == 0) {
			return;
		}
		
		//Set the current file based on the selected tab
		if(editor.getTabbedPane().getSelectedIndex() != -1) {
			
			for(Project currentProject: projectExplorer.getProjects()) {
				
				for(Class currentClass: currentProject.getFileButtons()) {
					
					if(editor.getTabbedPane().getSelectedComponent().equals(currentClass.getEditorTextAreaScroll())) {
						
						//Save the current tab
						editor.saveCurrentTab();
						
						//Set the current file
						currentFile = new File(String.format("projects/%s/%s", 
								currentClass.getProjectName(), currentClass.getClassName()));
						
					}
					
				}
			
			}
			
		}
		
		//Export the current class
		FileExecutionTool.exportFile(currentFile);

	}
    
	//This allows the user to export the project as a .java file
	private void setJDKFilepath() {
		
		//Find the current file path for the JDK
		String currentFilepath = "";
		
		try {
			//Write data to a file
			Scanner input = new Scanner(new File("settings/jdkFilepath"));
			if(input.hasNextLine())
				currentFilepath = input.nextLine();
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Save Failed");
		}
		
		//Open a file chooser and use it to set the JDK folder file path
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle(String.format("Select the JDK folder, jdk1.8.0_181 (Current filepath: %s)", currentFilepath));
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
        	System.out.println("getCurrentDirectory(): " +  fileChooser.getCurrentDirectory());
        	System.out.println("getSelectedFile() : " +  fileChooser.getSelectedFile().getAbsolutePath());
        	JOptionPane.showMessageDialog(this, String.format("JDK Filepath set to: %s", fileChooser.getSelectedFile().getAbsolutePath()));
        } else {
        	System.out.println("No Selection ");
        }
        
        String file = fileChooser.getSelectedFile().getAbsolutePath();
	    File filepath = new File(file);
	    
	    //Make sure that the file path is a directory
		if(filepath.isDirectory()) {
			
			try {
				
				//Write data to a file
				PrintWriter pr = new PrintWriter(new File("settings/jdkFilepath"));
				pr.println(file);
				pr.close();
				
				//Save JDK file path
		        JDKFilepath = file;

			} catch (FileNotFoundException e) {
				System.out.println("Save Failed");
			}

		} else {
			System.out.println("Invalid Filepath");
		}
	    
	}
	
	//This method reads the JDK filepath when the program is run
	private void readJDKFilepath() {
		
		try {
			//Write data to a file
			Scanner input = new Scanner(new File("settings/jdkFilepath"));
			if(input.hasNextLine())
				JDKFilepath = input.nextLine();
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Save Failed");
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

	public static JPanel getGUIPanel() {
		return GUIPanel;
	}
    
}

