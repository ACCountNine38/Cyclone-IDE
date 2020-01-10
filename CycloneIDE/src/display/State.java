package display;

import java.awt.Image;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import assets.Images;
import utils.FileExecutionTool;

public class State extends JFrame implements ActionListener {

	// final variables
	public static final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static File currentFile = new File("tabs/testFile");
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newProjectOption = new JMenuItem("New Project");
	private JMenuItem newClassOption = new JMenuItem("New Class");
	private JMenuItem saveCurrentTabOption = new JMenuItem("Save Current Tab");
	private JMenuItem saveAllTabsOption = new JMenuItem("Save All Tabs");
	private JMenuItem exitOption = new JMenuItem("Exit IDE");
	private JMenu editMenu = new JMenu("Edit");
	private JMenu windowMenu = new JMenu("Window");
	private JMenu runMenu = new JMenu("Run");
	private JMenuItem runOption = new JMenuItem("Run Project");
	private JMenu helpMenu = new JMenu("Help");
	
	public State() {

		new Images();
		new FileExecutionTool();
		customCursor();

	}

	// method that changes the cursor icon
	private void customCursor() {

		// using the java TookKit to change cursors
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		// load an image using ToolKit
		Image mouse = toolkit.getImage("images/cursor.png").getScaledInstance(25, 40, 0);

		// set the cursor icon giving a new image, point, and name
		setCursor(toolkit.createCustomCursor(mouse, new Point(0, 0), "Custom Cursor"));

	}

	public void addMenuBar() {
		// create a new JMenuBar item that stores different menus
		setJMenuBar(menuBar);

		// create a new menu called control and add it to the menu bar
		menuBar.add(fileMenu);
		
		//create options to create new project or class
		fileMenu.add(newProjectOption);
		newProjectOption.addActionListener(this);
		fileMenu.add(newClassOption);
		newClassOption.addActionListener(this);
		
		// creating the exit option under the control menu
		// add an action listener for button actions when clicked
		exitOption.addActionListener(new ActionListener() {

			// method handles the current button's actions
			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(1);

			}

		});

		fileMenu.add(saveCurrentTabOption);
		saveCurrentTabOption.addActionListener(this);
		fileMenu.add(saveAllTabsOption);
		saveAllTabsOption.addActionListener(this);
		
		fileMenu.add(exitOption);

		// create a new menu for settings
		menuBar.add(editMenu);

		// create a new menu to make window changes
		menuBar.add(windowMenu);

		// create a new menu to run the project
		menuBar.add(runMenu);

		// creating the exit option under the control menu
		// add an action listener for button actions when clicked
		runOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// runs the current project
				FileExecutionTool.executeFile(currentFile);

			}

		});

		runMenu.add(runOption);

		// create a new menu to show help commands
		menuBar.add(helpMenu);

	}

	public JMenu getFileMenu() {
		return fileMenu;
	}

	public void setFileMenu(JMenu fileMenu) {
		this.fileMenu = fileMenu;
	}

	public JMenuItem getNewProjectOption() {
		return newProjectOption;
	}

	public void setNewProjectOption(JMenuItem newProjectOption) {
		this.newProjectOption = newProjectOption;
	}

	public JMenuItem getNewClassOption() {
		return newClassOption;
	}

	public void setNewClassOption(JMenuItem newClassOption) {
		this.newClassOption = newClassOption;
	}

	public JMenuItem getSaveCurrentTabOption() {
		return saveCurrentTabOption;
	}

	public void setSaveCurrentTabOption(JMenuItem saveCurrentTabOption) {
		this.saveCurrentTabOption = saveCurrentTabOption;
	}

	public JMenuItem getSaveAllTabsOption() {
		return saveAllTabsOption;
	}

	public void setSaveAllTabsOption(JMenuItem saveAllTabsOption) {
		this.saveAllTabsOption = saveAllTabsOption;
	}

	public JMenuItem getExitOption() {
		return exitOption;
	}

	public void setExitOption(JMenuItem exitOption) {
		this.exitOption = exitOption;
	}

	public JMenu getEditMenu() {
		return editMenu;
	}

	public void setEditMenu(JMenu editMenu) {
		this.editMenu = editMenu;
	}

	public JMenu getWindowMenu() {
		return windowMenu;
	}

	public void setWindowMenu(JMenu windowMenu) {
		this.windowMenu = windowMenu;
	}

	public JMenu getRunMenu() {
		return runMenu;
	}

	public void setRunMenu(JMenu runMenu) {
		this.runMenu = runMenu;
	}

	public JMenuItem getRunOption() {
		return runOption;
	}

	public void setRunOption(JMenuItem runOption) {
		this.runOption = runOption;
	}

	public JMenu getHelpMenu() {
		return helpMenu;
	}

	public void setHelpMenu(JMenu helpMenu) {
		this.helpMenu = helpMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}